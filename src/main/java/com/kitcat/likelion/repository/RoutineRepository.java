package com.kitcat.likelion.repository;

import com.kitcat.likelion.domain.Routine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoutineRepository extends JpaRepository<Routine, Long> {
    @Query("select r from Routine r " +
            "where r.user.id = :userId " +
            "order by r.id asc ")
    List<Routine> findRoutineList(@Param("userId") Long userId);
}
