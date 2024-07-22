package com.kitcat.likelion.requestDTO;

import com.kitcat.likelion.domain.enumration.GrowthStatus;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Getter
public class PetsDTO {
    private String name;

    private double weight;

    private String growthStatus;
}
