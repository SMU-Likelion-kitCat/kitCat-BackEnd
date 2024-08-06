package com.kitcat.likelion.repository;

import com.kitcat.likelion.domain.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findPetsByUserId(@Param("userId") Long userId);

    Pet findPetById(@Param("id") Long id);


}
