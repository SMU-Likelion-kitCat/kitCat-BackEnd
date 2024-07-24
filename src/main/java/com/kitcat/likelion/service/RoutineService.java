package com.kitcat.likelion.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.kitcat.likelion.domain.Routine;
import com.kitcat.likelion.domain.User;
import com.kitcat.likelion.domain.enumration.RoutineTerm;
import com.kitcat.likelion.domain.enumration.RoutineType;
import com.kitcat.likelion.repository.RoutineRepository;
import com.kitcat.likelion.repository.UserRepository;
import com.kitcat.likelion.requestDTO.RoutineCreateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .target(dto.getTarget())
                .colorCode(dto.getColorCode())
                .routineType(RoutineType.valueOf(dto.getRoutineType()))
                .routineTerm(RoutineTerm.valueOf(dto.getRoutineTerm()))
                .build();

        routine.setUser(user);
        routineRepository.save(routine);
    }
}
