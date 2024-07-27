package com.kitcat.likelion.init;

import com.kitcat.likelion.domain.Pet;
import com.kitcat.likelion.domain.Post;
import com.kitcat.likelion.domain.User;
import com.kitcat.likelion.domain.enumration.GrowthStatus;
import com.kitcat.likelion.domain.enumration.RoleType;
import com.kitcat.likelion.repository.PostRepository;
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
    private final PostRepository postRepository;

    @PostConstruct
    public void init() {
        User user1 = new User("test@naver.com","user1", encoder.encode("1234"), RoleType.USER, 178.7, 76.2);
        User user = new User("test0@naver.com","user0", encoder.encode("1234"), RoleType.USER, 178.7, 76.2);

        Pet pet1 = new Pet("초코", "/dog1", 4.2, GrowthStatus.GROWING_UP_LESS_FOUR_MONTH);
        Pet pet2 = new Pet("우유", "/dog2", 5.6, GrowthStatus.NEUTERED_ADULT);

        pet1.setUser(user1);
        pet2.setUser(user1);

        userRepository.save(user1);
        userRepository.save(user);
    }
}
