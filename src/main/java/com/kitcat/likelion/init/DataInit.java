package com.kitcat.likelion.init;

import com.kitcat.likelion.domain.*;
import com.kitcat.likelion.domain.enumration.*;
import com.kitcat.likelion.repository.PostRepository;
import com.kitcat.likelion.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final PasswordEncoder encoder;

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    @PostConstruct
    public void init() {
        User user1 = new User("test@naver.com","user1", encoder.encode("1234"), RoleType.USER, 178.7, 76.2, 18.2);
        User user = new User("test0@naver.com","user0", encoder.encode("1234"), RoleType.USER, 178.7, 76.2, 21.8);

        Pet pet1 = new Pet("초코", "/dog1", 4.2, GrowthStatus.GROWING_UP_LESS_FOUR_MONTH);
        Pet pet2 = new Pet("기름이", "/dog2", 5.6, GrowthStatus.NEUTERED_ADULT);

        pet1.setUser(user1);
        pet2.setUser(user1);

        Routine routine1 = Routine.builder()
                .name("달리기")
                .step(5)
                .count(2)
                .target(40)
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

        UserRecord userRecord1 = new UserRecord(120, 3200, 61);
        UserRecord userRecord2 = new UserRecord(130, 3300, 62);
        UserRecord userRecord3 = new UserRecord(140, 3400, 63);

        Path path1 = new Path(new BigDecimal("37.5665"), new BigDecimal("126.9780"));
        Path path2 = new Path(new BigDecimal("35.1796"), new BigDecimal("129.0756"));
        Path path3 = new Path(new BigDecimal("35.1550"), new BigDecimal("126.8495"));

        path1.setUserRecord(userRecord1);
        path2.setUserRecord(userRecord1);
        path3.setUserRecord(userRecord1);

        userRecord1.setUser(user1);
        userRecord2.setUser(user1);
        userRecord3.setUser(user1);

        userRecord1.setRoutine(routine1);
        userRecord2.setRoutine(routine1);
        userRecord3.setRoutine(routine1);

        PetRecord petRecord1 = new PetRecord(110, pet1);
        PetRecord petRecord2 = new PetRecord(120, pet1);
        PetRecord petRecord3 = new PetRecord(130, pet1);

        petRecord1.setUserRecord(userRecord1);
        petRecord2.setUserRecord(userRecord2);
        petRecord3.setUserRecord(userRecord3);

        Post post1 = new Post("test1", "test_content1", 0, 1, user1);
        Post post2 = new Post("test2", "test_content2", 0, 1, user1);

        Comment comment1 = new Comment("아니면 티코 1", false);
        Comment comment2 = new Comment("아니면 티코 2", false);

        Comment children1 = new Comment("티코 좋다 1", false);
        Comment children2 = new Comment("티코 좋다 2", false);

        comment1.setPost(post1);
        comment2.setPost(post2);

        comment1.setUser(user1);
        comment2.setUser(user1);

        children1.setParent(comment1);
        children2.setParent(comment2);

        userRepository.save(user1);
        userRepository.save(user);
        postRepository.save(post1);
        postRepository.save(post2);
    }
}
