package com.kitcat.likelion.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class PostListDTO {

    private Long postId;

    private String content;

    private String writer;

    private int commentCount;

    private int likeCount;

    private LocalDateTime createTime;

}
