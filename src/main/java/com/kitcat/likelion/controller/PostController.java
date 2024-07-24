package com.kitcat.likelion.controller;

import com.kitcat.likelion.domain.Post;
import com.kitcat.likelion.requestDTO.PostCommentRequestDTO;
import com.kitcat.likelion.requestDTO.PostCreateRequestDTO;
import com.kitcat.likelion.responseDTO.PostDetailDTO;
import com.kitcat.likelion.security.custom.CustomUserDetails;
import com.kitcat.likelion.security.jwt.JwtUtil;
import com.kitcat.likelion.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    private final JwtUtil jwtUtil;

    @PostMapping("/create")
    public String createPost(@RequestPart(value = "dto")PostCreateRequestDTO requestDTO,
                                @AuthenticationPrincipal CustomUserDetails userDetails) {

        postService.createPost(userDetails.getUserId(), requestDTO);
        return "good";
    }

    @PostMapping("/comment")
    public String comment(@RequestBody PostCommentRequestDTO requestDTO,
                                @AuthenticationPrincipal CustomUserDetails userDetails){

        postService.createComment(userDetails.getUserId(), requestDTO);
//        postService.createComment(1L, requestDTO);
        return "good";
    }

    @GetMapping("/show")
    public PostDetailDTO show(@RequestParam("id") Long postId,
                                 @AuthenticationPrincipal CustomUserDetails userDetails){
        Long userId = userDetails.getUserId();
        return postService.findDetailPost(userId, postId);
    }

    @GetMapping("/heart/insert")
    public String insertHeart(@RequestParam Long postId,
                                @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long userId = userDetails.getUserId();
        postService.insertHeart(userId, postId);
        return "good";
    }

    @GetMapping("/heart/delecte")
    public String deleteHeart(@RequestParam Long postId,
                                @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long userId = userDetails.getUserId();
        postService.deleteHeart(userId, postId);
        return "good";
    }




}
