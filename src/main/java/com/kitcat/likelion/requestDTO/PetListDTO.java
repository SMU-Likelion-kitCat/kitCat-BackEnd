package com.kitcat.likelion.requestDTO;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PetListDTO {
    private List<PetsDTO> petsDTOS = new ArrayList<>();
}
