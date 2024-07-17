package com.kitcat.likelion.service;

import com.kitcat.likelion.domain.User;
import com.kitcat.likelion.domain.enumrate.RoleType;
import com.kitcat.likelion.repository.UserRepository;
import com.kitcat.likelion.requestDTO.LoginDTO;
import com.kitcat.likelion.requestDTO.RegisterDTO;
import com.kitcat.likelion.security.custom.CustomUserInfoDto;
import com.kitcat.likelion.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Transactional
    public void register(RegisterDTO dto) {
        User user = new User(dto.getEmail(), dto.getNickname(), encoder.encode(dto.getPassword()), RoleType.USER, dto.getHeight(), dto.getWeight());
        userRepository.save(user);
    }

    public String login(LoginDTO dto) {
        User user = userRepository.findUserByEmail(dto.getEmail());

        if(user == null) {
            return "user not found";
        }

        if(!encoder.matches(dto.getPassword(), user.getPassword())) {
            return "password error";
        }

        CustomUserInfoDto customUserInfoDto = new CustomUserInfoDto(user.getId(), user.getEmail(), user.getNickname(), user.getPassword(), user.getRole());
        return jwtUtil.createAccessToken(customUserInfoDto);
    }

    public String validateDuplicateNickname(String nickname) {
        User user = userRepository.findUserByNickname(nickname);

        if(user == null) {
            return "available";
        }

        return "duplicate";
    }

    public String validateDuplicateEmail(String email) {
        User user = userRepository.findUserByEmail(email);

        if(user == null) {
            return "available";
        }

        return "duplicate";
    }
}
