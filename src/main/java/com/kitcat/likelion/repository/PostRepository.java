package com.kitcat.likelion.repository;


import com.kitcat.likelion.domain.Post;
import com.kitcat.likelion.responseDTO.PostListDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {


    @Modifying(clearAutomatically = true)
    @Query("update Post p set p.like_count = p.like_count + 1 where p.id = :id")
    int increaseHeart(@Param("id") Long id);


    @Modifying(clearAutomatically = true)
    @Query("update Post p set p.like_count = p.like_count - 1 where p.id = :id")
    int decreaseHeart(@Param("id") Long id);


    @Modifying(clearAutomatically = true)
    @Query("SELECT new com.kitcat.likelion.responseDTO.PostListDTO(" +
            "p.id, p.content, u.nickname, p.commentCount, p.like_count, p.createTime, " +
            "CASE WHEN h.id IS NOT NULL THEN TRUE ELSE FALSE END) " +
            "FROM Post p " +
            "LEFT JOIN p.user u " +
            "LEFT JOIN p.hearts h " +
            "GROUP BY p.id, u.nickname, p.commentCount, p.like_count, p.createTime")
    List<PostListDTO> findAllPostListDTO();



}
