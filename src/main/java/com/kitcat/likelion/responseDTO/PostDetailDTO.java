package com.kitcat.likelion.responseDTO;

import com.kitcat.likelion.requestDTO.PostCommentRequestDTO;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class PostDetailDTO {

    private String title;

    private String content;

    private String writer;

    private int commentCount;

    private int likeCount;

    private boolean heartState;

    private boolean scrapState;

    private LocalDateTime createTime;

    private List<PostCommentResponesDTO> comments;

    @Builder
    public PostDetailDTO(String title, String content, String writer, int commentCount, boolean heartState, boolean scrapState, LocalDateTime createTime, int likeCount, List<PostCommentResponesDTO> comments) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.commentCount = commentCount;
        this.heartState = heartState;
        this.scrapState = scrapState;
        this.createTime = createTime;
        this.likeCount = likeCount;
        this.comments = comments;
    }


}
