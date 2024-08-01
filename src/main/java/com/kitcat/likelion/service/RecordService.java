package com.kitcat.likelion.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.kitcat.likelion.domain.*;
import com.kitcat.likelion.repository.PetRepository;
import com.kitcat.likelion.repository.RoutineRepository;
import com.kitcat.likelion.repository.UserRecordRepository;
import com.kitcat.likelion.repository.UserRepository;
import com.kitcat.likelion.requestDTO.LocationDTO;
import com.kitcat.likelion.requestDTO.PetCalorieDTO;
import com.kitcat.likelion.requestDTO.RecordCreateDTO;
import com.kitcat.likelion.responseDTO.DayRecordDTO;
import com.kitcat.likelion.responseDTO.PetRecordDTO;
import com.kitcat.likelion.responseDTO.RecordDetailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecordService {

    private final PetRepository petRepository;

    private final UserRepository userRepository;

    private final RoutineRepository routineRepository;

    private final UserRecordRepository userRecordRepository;

    @Transactional
    public void save(Long userId, RecordCreateDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Could not found id : " + userId));
        Long routineId = dto.getRoutineId();

        UserRecord userRecord = new UserRecord(dto.getCalorie(), dto.getDistance(), dto.getWalkTime());
        userRecord.setUser(user);

        Routine routine = null;

        if(dto.getRoutineId() != null) {
            routine = routineRepository.findById(routineId)
                    .orElseThrow(() -> new NotFoundException("Could not found id : " + routineId));
            userRecord.setRoutine(routine);
        }

        List<PetCalorieDTO> petCalorieDTOS = dto.getPetRecords();

        for (PetCalorieDTO petCalorie : petCalorieDTOS) {
            Pet pet = petRepository.findById(petCalorie.getPetId())
                    .orElseThrow(() -> new NotFoundException("Could not found id : " + petCalorie.getPetId()));

            PetRecord petRecord = new PetRecord(petCalorie.getCalorie(), pet);
            petRecord.setUserRecord(userRecord);
            petRecord.setPet(pet);
        }

        List<LocationDTO> paths = dto.getPath();

        for (LocationDTO location : paths) {
            Path path = new Path(location.getLatitude(), location.getLongitude());
            path.setUserRecord(userRecord);
        }

        userRecordRepository.save(userRecord);
    }

    public List<DayRecordDTO> getMonthRecord(Long userId, int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);

        LocalDateTime startOfMonth = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = yearMonth.atEndOfMonth().atTime(23, 59, 59);

        int dayOfMonth = endOfMonth.getDayOfMonth();

        DayRecordDTO[] array = new DayRecordDTO[dayOfMonth];
        for (int i = 0; i < dayOfMonth; i++) {
            array[i] = new DayRecordDTO();
        }

        List<DayRecordDTO> dayRecords = Arrays.asList(array);

        List<UserRecord> records = userRecordRepository.findBetweenDate(startOfMonth, endOfMonth, userId);

        for (UserRecord record : records) {
            List<LocationDTO> locations = record.getPaths().stream().map(location ->
                    new LocationDTO(location.getLatitude(), location.getLongitude())).toList();

            List<PetRecordDTO> petRecords = record.getPetRecords().stream().map(petRecord ->
                    new PetRecordDTO(petRecord.getPet().getImage(), petRecord.getCalorie())).toList();

            RecordDetailDTO dto = RecordDetailDTO.builder()
                    .walkTime(record.getWalkTime())
                    .distance(record.getDistance())
                    .calorie(record.getCalorie())
                    .locations(locations)
                    .petRecords(petRecords)
                    .build();

            int day = record.getCreateDate().getDayOfMonth();
            dayRecords.get(day - 1).addRecordDetail(dto);
        }

        return dayRecords;
    }
}
