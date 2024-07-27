package com.kitcat.likelion.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.kitcat.likelion.domain.Routine;
import com.kitcat.likelion.domain.User;
import com.kitcat.likelion.domain.enumration.RoutineBase;
import com.kitcat.likelion.domain.enumration.RoutineTerm;
import com.kitcat.likelion.domain.enumration.RoutineType;
import com.kitcat.likelion.repository.RoutineRepository;
import com.kitcat.likelion.repository.UserRepository;
import com.kitcat.likelion.requestDTO.RoutineCreateDTO;
import com.kitcat.likelion.responseDTO.RoutineDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoutineService {

    private final UserRepository userRepository;
    private final RoutineRepository routineRepository;

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
                    .routineBase(routine.getRoutineBase())
                    .routineTerm(routine.getRoutineTerm())
                    .routineType(routine.getRoutineType())
                    .build();

            dtos.add(dto);
        }

        return dtos;
    }
}
