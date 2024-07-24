package com.kitcat.likelion.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.kitcat.likelion.domain.Pet;
import com.kitcat.likelion.domain.User;
import com.kitcat.likelion.domain.enumration.GrowthStatus;
import com.kitcat.likelion.repository.PetRepository;
import com.kitcat.likelion.repository.UserRepository;
import com.kitcat.likelion.requestDTO.PetListDTO;
import com.kitcat.likelion.requestDTO.PetsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

            MultipartFile file = files.get(index);
            ObjectMetadata metadata = new ObjectMetadata();

            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            amazonS3.putObject(bucket, image, file.getInputStream(), metadata);
            petRepository.save(pet);
        }
    }
}
