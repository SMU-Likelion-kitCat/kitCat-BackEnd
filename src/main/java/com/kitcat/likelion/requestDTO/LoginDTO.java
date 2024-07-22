package com.kitcat.likelion.requestDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginDTO {
    private String email;

    private String password;

    public LoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
