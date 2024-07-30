package com.kitcat.likelion.init;

import com.kitcat.likelion.domain.*;
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
        User user1 = new User("test@naver.com","user1", encoder.encode("1234"), RoleType.USER, 178.7, 76.2, 18.2);
        User user = new User("test0@naver.com","user0", encoder.encode("1234"), RoleType.USER, 178.7, 76.2, 21.8);

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

        UserRecord userRecord1 = new UserRecord(120, 3200, 61);
        UserRecord userRecord2 = new UserRecord(130, 3300, 62);
        UserRecord userRecord3 = new UserRecord(140, 3400, 63);

        userRecord1.setUser(user1);
        userRecord2.setUser(user1);
        userRecord3.setUser(user1);

        userRecord1.setRoutine(routine1);
        userRecord2.setRoutine(routine1);
        userRecord3.setRoutine(routine1);

        userRecord1.addPetRecord(new PetRecord(110, pet1));
        userRecord2.addPetRecord(new PetRecord(110, pet1));
        userRecord3.addPetRecord(new PetRecord(110, pet1));

        Post post1 = new Post("test1", "test_content1", 0, 1, user1);
        Post post2 = new Post("test2", "test_content2", 0, 1, user1);

        userRepository.save(user1);
        userRepository.save(user);
        postRepository.save(post1);
        postRepository.save(post2);
    }
}
