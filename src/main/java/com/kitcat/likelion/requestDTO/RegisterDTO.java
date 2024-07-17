package com.kitcat.likelion.requestDTO;

import lombok.Getter;

@Getter
public class RegisterDTO {
    private String email;

    private String password;

    private String nickname;

    private double height;

    private double weight;
}
