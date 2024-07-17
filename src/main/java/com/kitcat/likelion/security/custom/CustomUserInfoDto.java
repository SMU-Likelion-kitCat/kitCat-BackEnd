package com.kitcat.likelion.security.custom;

import com.kitcat.likelion.domain.enumration.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomUserInfoDto {
    private Long userId;

    private String email;

    private String nickname;

    private String password;

    private RoleType role;
}
