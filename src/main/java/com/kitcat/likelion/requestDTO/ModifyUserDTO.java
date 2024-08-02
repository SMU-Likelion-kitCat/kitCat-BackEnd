package com.kitcat.likelion.requestDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ModifyUserDTO {

    private String nickname;

    private double height;

    private double weight;

    private double bmi;

}
