package com.kitcat.likelion.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.kitcat.likelion.domain.Pet;
import com.kitcat.likelion.domain.User;
import com.kitcat.likelion.domain.enumration.GrowthStatus;
import com.kitcat.likelion.repository.PetRepository;
import com.kitcat.likelion.repository.UserRepository;
import com.kitcat.likelion.requestDTO.ModifyPetDTO;
import com.kitcat.likelion.requestDTO.PetListDTO;
import com.kitcat.likelion.requestDTO.PetsDTO;
import com.kitcat.likelion.responseDTO.PetInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PetService {
    private final PetRepository petRepository;

    private final UserRepository userRepository;

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    public void savePets(List<PetsDTO> dto, List<MultipartFile> files, Long userId) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Could not found id : " + userId));

        int index = 0;

        for (PetsDTO petsDTO : dto) {
            String image = "kitcat/" + user.getNickname() + "-" + petsDTO.getName();

            Pet pet = new Pet(petsDTO.getName(), image, petsDTO.getWeight(), GrowthStatus.valueOf(petsDTO.getGrowthStatus()));
            pet.setUser(user);

            MultipartFile file = files.get(index++);
            ObjectMetadata metadata = new ObjectMetadata();

            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            amazonS3.putObject(bucket, image, file.getInputStream(), metadata);
            petRepository.save(pet);
        }
    }

    public List<PetInfoDTO> getPets(Long userId) {
        List<Pet> pets = petRepository.findPetsByUserId(userId);

        return pets.stream().map(pet -> new PetInfoDTO(pet.getId(), pet.getName(), pet.getImage(), pet.getWeight())).toList();
    }

    @Transactional
    public String modifyPetInfo(Long userId, List<ModifyPetDTO> modifyPetDTOs, List<MultipartFile> files) {
        int index = 0;

        for (ModifyPetDTO dto : modifyPetDTOs) {
            if (dto.getPetid() == null) {
                throw new IllegalArgumentException("petId는 null이 될 수 없습니다.");
            }

            Optional<Pet> optionalPet = petRepository.findById(dto.getPetid());
            Pet pet = null;
            if (optionalPet.isPresent()) {
                 pet = optionalPet.get();
                if (!pet.getUser().getId().equals(userId)) {
                    throw new RuntimeException("Unauthorized access to pet: " + dto.getPetid());
                }
                pet.modifyName(dto.getName());
                pet.modifyWeight(dto.getWeight());
                petRepository.save(pet);
            } else if (dto.isImageStatus()) {
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new NotFoundException("Could not find user with id: " + userId));

                pet = optionalPet.get();
                String oldImage = "kitcat/" + pet.getImage();

                try {
                    amazonS3.deleteObject(bucket, oldImage);
                } catch (Exception e) {
                    throw new RuntimeException("Failed to delete old image for pet: " + dto.getPetid(), e);
                }

                if (files != null && index < files.size()) {
                    pet = optionalPet.get();
                    String newImage = "kitcat/" + pet.getImage();
                    MultipartFile file = files.get(index++);
                    ObjectMetadata metadata = new ObjectMetadata();
                    metadata.setContentLength(file.getSize());
                    metadata.setContentType(file.getContentType());

                    try {
                        amazonS3.putObject(bucket, newImage, file.getInputStream(), metadata);
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to upload new image for pet: " + dto.getPetid(), e);
                    }

                } else {
                    throw new IllegalArgumentException("Image file is missing for pet with id: " + dto.getPetid());
                }
            } else {
                throw new RuntimeException("Pet not found with id: " + dto.getPetid());
            }
        }

        return "good";
    }



}
