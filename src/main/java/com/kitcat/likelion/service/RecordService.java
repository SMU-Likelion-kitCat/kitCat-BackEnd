package com.kitcat.likelion.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.kitcat.likelion.domain.Pet;
import com.kitcat.likelion.domain.PetRecord;
import com.kitcat.likelion.domain.User;
import com.kitcat.likelion.domain.UserRecord;
import com.kitcat.likelion.repository.PetRepository;
import com.kitcat.likelion.repository.UserRecordRepository;
import com.kitcat.likelion.repository.UserRepository;
import com.kitcat.likelion.requestDTO.PetCalorieDTO;
import com.kitcat.likelion.requestDTO.RecordCreateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecordService {

    private final PetRepository petRepository;

    private final UserRepository userRepository;

    private final UserRecordRepository userRecordRepository;

    @Transactional
    public void save(Long userId, RecordCreateDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Could not found id : " + userId));

        List<PetCalorieDTO> petCalorieDTOS = dto.getPetRecords();

        UserRecord userRecord = new UserRecord(dto.getCalorie(), dto.getDistance(), dto.getWalkTime());
        userRecord.setUser(user);

        for (PetCalorieDTO petCalorie : petCalorieDTOS) {
            Pet pet = petRepository.findById(petCalorie.getPetId())
                    .orElseThrow(() -> new NotFoundException("Could not found id : " + petCalorie.getPetId()));

            PetRecord petRecord = new PetRecord(petCalorie.getCalorie(), pet);
            petRecord.setUserRecord(userRecord);
            petRecord.setPet(pet);
        }

        userRecordRepository.save(userRecord);
    }
}
