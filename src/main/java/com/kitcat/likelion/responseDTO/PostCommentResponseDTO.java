package com.kitcat.likelion.responseDTO;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class PostCommentResponseDTO {

    private Long commentId;

    private String writer;

    private String content;

    private boolean isDeleted;

    private LocalDateTime commentTime;

    public PostCommentResponseDTO(Long commentId, String writer, String content, boolean isDeleted, LocalDateTime commentTime) {
        this.commentId = commentId;
        this.writer = writer;
        this.content = content;
        this.isDeleted = isDeleted;
        this.commentTime = commentTime;
    }

}
