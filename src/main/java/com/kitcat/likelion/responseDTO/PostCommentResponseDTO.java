package com.kitcat.likelion.responseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class PostCommentResponseDTO {

    @Schema(description = "댓글 고유 번호")
    private Long commentId;

    @Schema(description = "댓글 작성자")
    private String writer;

    @Schema(description = "댓글 내용")
    private String content;

    @Schema(description = "댓글 삭제된 상태")
    private boolean isDeleted;

    @Schema(description = "댓글 작성 시간")
    private LocalDateTime commentTime;

    public PostCommentResponseDTO(Long commentId, String writer, String content, boolean isDeleted, LocalDateTime commentTime) {
        this.commentId = commentId;
        this.writer = writer;
        this.content = content;
        this.isDeleted = isDeleted;
        this.commentTime = commentTime;
    }

}
