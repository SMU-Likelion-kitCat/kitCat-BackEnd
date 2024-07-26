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
    @Query("select new com.kitcat.likelion.responseDTO.PostListDTO(p.content, u.nickname, p.commentCount, p.like_count, p.createTime) " +
            "from Post p left join p.user u")
    List<PostListDTO> findAllPostListDTO();


}
