package com.kitcat.likelion.responseDTO;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class PostCommentResponesDTO {

    private Long commentId;

    private String writer;

    private String content;

    private boolean isDeleted;

    private boolean commentWriterState;

    private LocalDateTime commentTime;

    private List<PostCommentResponesDTO> children = new ArrayList<>();

    public PostCommentResponesDTO(Long commentId, String writer, String content, boolean isDeleted, boolean commentWriterState, LocalDateTime commentTime) {
        this.commentId = commentId;
        this.writer = writer;
        this.content = content;
        this.isDeleted = isDeleted;
        this.commentWriterState = commentWriterState;
        this.commentTime = commentTime;
    }

}
