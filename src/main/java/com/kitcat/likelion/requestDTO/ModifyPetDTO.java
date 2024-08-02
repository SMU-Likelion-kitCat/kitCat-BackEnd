package com.kitcat.likelion.requestDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ModifyPetDTO {

    private Long petid;

    private String name;

    private double weight;

}
