package com.kitcat.likelion.responseDTO;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostDetailDTO {

    private String title;

    private String content;

    private String writer;

    private int commentCount;

    private int likeCount;

    private boolean heartState;

    private LocalDateTime createTime;

    private List<PostCommentResponseDTO> comments;

    @Builder
    public PostDetailDTO(String title, String content, String writer, int commentCount, boolean heartState, LocalDateTime createTime, int likeCount, List<PostCommentResponseDTO> comments) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.commentCount = commentCount;
        this.heartState = heartState;
        this.createTime = createTime;
        this.likeCount = likeCount;
        this.comments = comments;
    }


}
