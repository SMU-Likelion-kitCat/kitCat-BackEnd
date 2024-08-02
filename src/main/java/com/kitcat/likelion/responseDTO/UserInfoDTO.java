package com.kitcat.likelion.responseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserInfoDTO {

    @Schema(description = "닉네임", example = "햄버거")
    private String nickname;

    @Schema(description = "키", example = "172.3")
    private double height;

    @Schema(description = "체중", example = "68.7")
    private double weight;

    @Schema(description = "BMI", example = "18.2")
    private double bmi;

}
