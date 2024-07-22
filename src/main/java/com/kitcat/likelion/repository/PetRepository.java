package com.kitcat.likelion.repository;

import com.kitcat.likelion.domain.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
}
