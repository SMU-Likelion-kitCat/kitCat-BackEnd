package com.kitcat.likelion.responseDTO;

import com.kitcat.likelion.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class PostListDTO {

    private Long postId;

    private String content;

    private String writer;

    private int commentCount;

    private int likeCount;

    private LocalDateTime createTime;

    private boolean heartStatus;

    public PostListDTO(Long postId, String content, String writer, int commentCount, int likeCount, LocalDateTime createTime, Boolean heartStatus) {
        this.postId = postId;
        this.content = content;
        this.writer = writer;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.createTime = createTime;
        this.heartStatus = heartStatus;

    }

}
