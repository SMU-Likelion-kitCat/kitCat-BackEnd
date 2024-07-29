package com.kitcat.likelion.requestDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class RegisterDTO {

    @Schema(description = "이메일(아이디)", example = "abc@naver.com")
    private String email;

    @Schema(description = "비밀번호", example = "1234")
    private String password;

    @Schema(description = "닉네임", example = "햄버거")
    private String nickname;

    @Schema(description = "키", example = "172.3")
    private double height;

    @Schema(description = "체중", example = "68.7")
    private double weight;

    @Schema(description = "BMI", example = "18.2")
    private double bmi;
}
