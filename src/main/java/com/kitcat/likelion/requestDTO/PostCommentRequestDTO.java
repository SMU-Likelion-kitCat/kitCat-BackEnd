package com.kitcat.likelion.requestDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCommentRequestDTO {

    @Schema(description = "댓글이 달리는 게시글의 ID")
    private Long postId;

    @Schema(description = "댓글의 내용")
    private String content;

}
