package com.kitcat.likelion.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.kitcat.likelion.domain.Routine;
import com.kitcat.likelion.domain.User;
import com.kitcat.likelion.domain.UserRecord;
import com.kitcat.likelion.domain.enumration.RoutineBase;
import com.kitcat.likelion.domain.enumration.RoutineTerm;
import com.kitcat.likelion.domain.enumration.RoutineType;
import com.kitcat.likelion.repository.RoutineRepository;
import com.kitcat.likelion.repository.UserRecordRepository;
import com.kitcat.likelion.repository.UserRepository;
import com.kitcat.likelion.requestDTO.RoutineCreateDTO;
import com.kitcat.likelion.responseDTO.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoutineService {

    private final UserRepository userRepository;
    private final RoutineRepository routineRepository;

    private final UserRecordRepository userRecordRepository;

    @Transactional
    public void save(Long userId, RoutineCreateDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Could not found id : " + userId));

        Routine routine = Routine.builder()
                .name(dto.getName())
                .step(dto.getStep())
                .count(dto.getCount())
                .target(dto.getTarget())
                .colorCode(dto.getColorCode())
                .routineBase(RoutineBase.fromString(dto.getRoutineBase()))
                .routineType(RoutineType.fromString(dto.getRoutineType()))
                .routineTerm(RoutineTerm.fromString(dto.getRoutineTerm()))
                .build();

        routine.setUser(user);
        routineRepository.save(routine);
    }

    public List<RoutineDTO> getRoutineList(Long userId) {
        List<Routine> routineList = routineRepository.findRoutineList(userId);
        List<RoutineDTO> dtos = new ArrayList<>();

        for (Routine routine : routineList) {
            double progress = ((double) routine.getUserRecords().size() / (double) ((routine.getRoutineTerm().getRoutineTerm().charAt(0) - '0') * routine.getCount())) * 100;

            RoutineDTO dto = RoutineDTO.builder()
                    .routineId(routine.getId())
                    .name(routine.getName())
                    .step(routine.getStep())
                    .count(routine.getCount())
                    .target(routine.getTarget())
                    .colorCode(routine.getColorCode())
                    .routineBase(routine.getRoutineBase().getRoutineBase())
                    .routineTerm(routine.getRoutineTerm().getRoutineTerm().charAt(0) - '0')
                    .routineType(routine.getRoutineType().getRoutineType())
                    .progress((int) progress)
                    .build();
            System.out.println(routine.getUserRecords().size());
            System.out.println((routine.getRoutineTerm().getRoutineTerm().charAt(0) - '0') * routine.getCount());
            dtos.add(dto);
        }

        return dtos;
    }

    public RoutineDetailDTO getRoutineRecord(Long routineId) {
        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new NotFoundException("Could not found id : " + routineId));
        LocalDateTime routineDate = routine.getCreateDate();

        List<UserRecord> routineRecords = userRecordRepository.findRoutineRecords(routineId);
        List<WeekRecordDTO> result = new ArrayList<>();
        List<String> achievement = new ArrayList<>();

        WeekRecordDTO weekRecord = new WeekRecordDTO();

        int week = 1, base = routine.getRoutineTerm().getRoutineTerm().charAt(0) - '0';
        RoutineType type = routine.getRoutineType();

        System.out.println("routineDate = " + routineDate);

        for (UserRecord userRecord : routineRecords) {
            LocalDateTime recordDate = userRecord.getCreateDate();
            System.out.println("recordDate = " + recordDate);

            // 산책 기록 날짜가 루틴 생성 날짜(+week)전이 아니라면
            if(!recordDate.isBefore(routineDate.plusWeeks(week))) {
                week++;
                result.add(weekRecord);
                weekRecord = new WeekRecordDTO();
                weekRecord.getRecords().clear();
            }

            int record = type.equals(RoutineType.TIME) ? userRecord.getWalkTime() :
                    (type.equals(RoutineType.DISTANCE) ? userRecord.getDistance() : userRecord.getCalorie());

            weekRecord.addRecord(new RecordDTO(userRecord.getCreateDate(), record, false));
        }
        result.add(weekRecord);

        for(int i = 0; i < 4; i++) {

            if(result.size() > i) {
                int goal = routine.getTarget() - (routine.getStep() * (base - i + 1));
                int count = 0;

                for(RecordDTO recordDTO : result.get(i).getRecords()) {
                    if(goal < recordDTO.getRecord()) {
                        count++;
                        recordDTO.setSuccess(true);
                    }
                }

                achievement.add(count >= routine.getCount() ? "SUCCESS" : "FAIL");
            }

            else {
                achievement.add("NONE");
            }
        }
        System.out.println("achievement = " + achievement);

        return new RoutineDetailDTO(achievement, result);
    }

    public List<DayRoutineDTO> getMonthRoutine(Long userId, int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);

        LocalDateTime startOfMonth = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = yearMonth.atEndOfMonth().atTime(23, 59, 59);

        int dayOfMonth = endOfMonth.getDayOfMonth();

        DayRoutineDTO[] array = new DayRoutineDTO[dayOfMonth];
        for(int i = 0; i < dayOfMonth; i++) {
            array[i] = new DayRoutineDTO();
        }

        List<DayRoutineDTO> dayRoutines = Arrays.asList(array);
        List<UserRecord> records = userRecordRepository.findBetweenDateFetchRoutine(startOfMonth, endOfMonth, userId);

        for (UserRecord record : records) {
            Routine routine = record.getRoutine();

            if(routine == null) {
                continue;
            }

            double progress = ((double) routine.getUserRecords().size() / (double) ((routine.getRoutineTerm().getRoutineTerm().charAt(0) - '0') * routine.getCount())) * 100;

            RoutineDTO dto = RoutineDTO.builder()
                    .routineId(routine.getId())
                    .name(routine.getName())
                    .step(routine.getStep())
                    .count(routine.getCount())
                    .target(routine.getTarget())
                    .colorCode(routine.getColorCode())
                    .routineBase(routine.getRoutineBase().getRoutineBase())
                    .routineTerm(routine.getRoutineTerm().getRoutineTerm().charAt(0) - '0')
                    .routineType(routine.getRoutineType().getRoutineType())
                    .progress((int) progress)
                    .build();

            int day = record.getCreateDate().getDayOfMonth();
            dayRoutines.get(day - 1).addRoutine(dto);
        }

        return dayRoutines;
    }
}
