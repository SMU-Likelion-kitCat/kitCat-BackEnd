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
import com.kitcat.likelion.responseDTO.RecordDTO;
import com.kitcat.likelion.responseDTO.RoutineDTO;
import com.kitcat.likelion.responseDTO.WeekRecordDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
                    .build();

            dtos.add(dto);
        }

        return dtos;
    }

    public List<WeekRecordDTO> getRoutineRecord(Long routineId) {
        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new NotFoundException("Could not found id : " + routineId));

        List<UserRecord> routineRecords = userRecordRepository.findRoutineRecords(routineId);
        List<WeekRecordDTO> result = new ArrayList<>();
        WeekRecordDTO weekRecord = new WeekRecordDTO();

        int week = 1;

        for (UserRecord userRecord : routineRecords) {
            LocalDateTime recordDate = userRecord.getCreateDate();

            if(recordDate.isBefore(routine.getCreateDate().plusWeeks(week))) {
                week++;
                result.add(weekRecord);
                weekRecord.getWeekRecordDTOList().clear();
            }

            weekRecord.addWeekRecord(new RecordDTO(userRecord.getCreateDate(), userRecord.getWalkTime()));
        }

        return result;
    }
}
