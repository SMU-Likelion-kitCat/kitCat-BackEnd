package com.kitcat.likelion.requestDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class LoginDTO {

    @Schema(description = "아이디(이메일)", example = "test@naver.com")
    private String email;

    @Schema(description = "비밀번호", example = "1234")
    private String password;

    public LoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
