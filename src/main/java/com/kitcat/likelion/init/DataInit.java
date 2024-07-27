package com.kitcat.likelion.init;

import com.kitcat.likelion.domain.Pet;
import com.kitcat.likelion.domain.Post;
import com.kitcat.likelion.domain.Routine;
import com.kitcat.likelion.domain.User;
import com.kitcat.likelion.domain.enumration.*;
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
        Pet pet2 = new Pet("기름이", "/dog2", 5.6, GrowthStatus.NEUTERED_ADULT);

        pet1.setUser(user1);
        pet2.setUser(user1);

        Routine routine1 = Routine.builder()
                .name("달리기")
                .step(5)
                .count(10)
                .target(65)
                .colorCode("#FCF1DB")
                .routineBase(RoutineBase.WEEK)
                .routineType(RoutineType.TIME)
                .routineTerm(RoutineTerm.FOUR_WEEKS)
                .build();

        Routine routine2 = Routine.builder()
                .name("기름이와 산책")
                .step(500)
                .count(1)
                .target(3000)
                .colorCode("#FCF1DB")
                .routineBase(RoutineBase.WEEK)
                .routineType(RoutineType.DISTANCE)
                .routineTerm(RoutineTerm.TWO_WEEKS)
                .build();

        routine1.setUser(user1);
        routine2.setUser(user1);

        Post post1 = new Post("test1", "test_content1", 0, 1, user1);
        Post post2 = new Post("test2", "test_content2", 0, 1, user1);

        userRepository.save(user1);
        userRepository.save(user);
        postRepository.save(post1);
        postRepository.save(post2);
    }
}
