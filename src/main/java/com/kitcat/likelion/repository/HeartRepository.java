package com.kitcat.likelion.repository;

import com.kitcat.likelion.domain.Heart;
import com.kitcat.likelion.domain.Post;
import com.kitcat.likelion.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {

    Optional<Heart> findByUserAndPost(@Param("member")User user, @Param("post") Post post);

    Optional<Heart> findByUserIdAndPostId(@Param("memberId") Long memberId,
                                            @Param("postId") Long postId);

    @Modifying
    @Query("delete from Heart h " +
            "where h.user.id = :id ")
    void deleteByUserId(@Param("id") Long id);

    @Query("SELECT COUNT(h) > 0 FROM Heart h WHERE h.post.id = :postId AND h.user.id = :userId")
    boolean existsByPostIdAndUserId(@Param("postId") Long postId, @Param("userId") Long userId);

}
