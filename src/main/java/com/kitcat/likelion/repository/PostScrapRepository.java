package com.kitcat.likelion.repository;

import com.kitcat.likelion.domain.Post;
import com.kitcat.likelion.domain.PostScrap;
import com.kitcat.likelion.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface PostScrapRepository extends JpaRepository<PostScrap, Long> {
    Optional<PostScrap> findByUserAndPost(@Param("member") User user,
                                            @Param("post") Post post);

    Optional<PostScrap> findByUserIdAndPostId(@Param("memberId") Long memberId,
                                                @Param("postId") Long postId);
}
