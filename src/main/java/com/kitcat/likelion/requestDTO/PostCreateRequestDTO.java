package com.kitcat.likelion.requestDTO;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCreateRequestDTO {

    @Schema(description = "게시글 이름")
    private String title;

    @Schema(description = "게시글 내용")
    private String content;

}
