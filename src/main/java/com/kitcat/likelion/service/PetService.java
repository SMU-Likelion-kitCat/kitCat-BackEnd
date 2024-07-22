package com.kitcat.likelion.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.kitcat.likelion.domain.Pet;
import com.kitcat.likelion.domain.User;
import com.kitcat.likelion.domain.enumration.GrowthStatus;
import com.kitcat.likelion.repository.PetRepository;
import com.kitcat.likelion.repository.UserRepository;
import com.kitcat.likelion.requestDTO.PetListDTO;
import com.kitcat.likelion.requestDTO.PetsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PetService {
    private final PetRepository petRepository;

    private final UserRepository userRepository;

    @Transactional
    public void savePets(PetListDTO dto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Could not found id : " + userId));

        for (PetsDTO petsDTO : dto.getPetsDTOS()) {
            Pet pet = new Pet(petsDTO.getName(), petsDTO.getWeight(), GrowthStatus.valueOf(petsDTO.getGrowthStatus()));
            pet.setUser(user);

            petRepository.save(pet);
        }
    }
}
