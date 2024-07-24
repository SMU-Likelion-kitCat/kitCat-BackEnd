package com.kitcat.likelion.repository;

import com.kitcat.likelion.domain.Comment;
import com.kitcat.likelion.domain.Post;
import com.kitcat.likelion.responseDTO.PostCommentResponesDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    default List<PostCommentResponesDTO> findByPostId(Long postId) {
        return null;
    }

}
