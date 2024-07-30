package com.kitcat.likelion.repository;

import com.kitcat.likelion.domain.UserRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRecordRepository extends JpaRepository<UserRecord, Long> {

    @Query("select ur from UserRecord ur where ur.routine.id = :routineId " +
            "order by ur.createDate asc ")
    List<UserRecord> findRoutineRecords(@Param("routineId") Long routineId);
}
