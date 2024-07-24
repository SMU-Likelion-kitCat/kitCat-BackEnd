package com.kitcat.likelion.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PetInfoDTO {

    private Long petId;

    private String name;

    private String image;
}
