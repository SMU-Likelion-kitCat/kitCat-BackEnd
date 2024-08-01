package com.kitcat.likelion.repository;

import com.kitcat.likelion.domain.UserRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface UserRecordRepository extends JpaRepository<UserRecord, Long> {

    @Query("select ur from UserRecord ur where ur.routine.id = :routineId " +
            "order by ur.createDate asc ")
    List<UserRecord> findRoutineRecords(@Param("routineId") Long routineId);

    @Query("select ur from UserRecord ur " +
            "where (ur.createDate between :startOfMonth and :endOfMonth) " +
            "and ur.user.id = :userId " +
            "order by ur.createDate asc")
    List<UserRecord> findBetweenDate(@Param("startOfMonth") LocalDateTime startOfMonth,
                                     @Param("endOfMonth") LocalDateTime endOfMonth,
                                     @Param("userId") Long userId);

    @Query("select ur from UserRecord ur " +
            "left join fetch ur.routine " +
            "where (ur.createDate between :startOfMonth and :endOfMonth) " +
            "and ur.user.id = :userId " +
            "order by ur.createDate asc")
    List<UserRecord> findBetweenDateFetchRoutine(@Param("startOfMonth") LocalDateTime startOfMonth,
                                     @Param("endOfMonth") LocalDateTime endOfMonth,
                                     @Param("userId") Long userId);
}
