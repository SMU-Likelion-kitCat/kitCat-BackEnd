package com.kitcat.likelion.init;

import com.kitcat.likelion.domain.User;
import com.kitcat.likelion.domain.enumration.RoleType;
import com.kitcat.likelion.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final PasswordEncoder encoder;
    private final UserRepository userRepository;

    @PostConstruct
    public void init() {
        User user1 = new User("test@naver.com","user1", encoder.encode("1234"), RoleType.USER, 178.7, 76.2);
        User user = new User("test0@naver.com","user0", encoder.encode("1234"), RoleType.USER, 178.7, 76.2);

        userRepository.save(user1);
        userRepository.save(user);
    }
}
