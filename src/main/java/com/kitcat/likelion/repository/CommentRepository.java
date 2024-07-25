package com.kitcat.likelion.repository;

import com.kitcat.likelion.domain.Comment;
import com.kitcat.likelion.domain.Post;
import com.kitcat.likelion.responseDTO.PostCommentResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>{

    @Query(value = "select c from Comment c " +
            "left join fetch c.post p " +
            "where c.user.id = :userId " +
            "order by c.createTime desc ")
    List<Comment> findCommentHistory(@Param("userId") Long userId);

    default List<PostCommentResponseDTO> findByPostId(Long postId, Long userId) {
        return null;
    }

}
