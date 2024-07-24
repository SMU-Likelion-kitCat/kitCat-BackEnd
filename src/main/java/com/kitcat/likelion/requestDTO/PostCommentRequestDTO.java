package com.kitcat.likelion.requestDTO;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCommentRequestDTO {

    private Long postId;

    private Long parentId;

    private String content;

}
