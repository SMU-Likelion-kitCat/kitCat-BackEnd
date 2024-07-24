package com.kitcat.likelion.repository;


import com.kitcat.likelion.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {


    @Modifying(clearAutomatically = true)
    @Query("update Post p set p.like_count = p.like_count + 1 where p.id = :id")
    int increaseHeart(@Param("id") Long id);


    @Modifying(clearAutomatically = true)
    @Query("update Post p set p.like_count = p.like_count - 1 where p.id = :id")
    int decreaseHeart(@Param("id") Long id);


}
