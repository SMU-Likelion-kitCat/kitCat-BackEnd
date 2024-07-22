package com.kitcat.likelion.requestDTO;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PetListDTO {
    private List<PetsDTO> petsDTOS = new ArrayList<>();
}
