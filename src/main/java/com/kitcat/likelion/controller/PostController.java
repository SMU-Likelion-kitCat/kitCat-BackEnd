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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    private final JwtUtil jwtUtil;

    @PostMapping("/create")
    public String createPost(@RequestPart(value = "dto")PostCreateRequestDTO requestDTO,
                                @RequestPart(value = "files", required = false) List<MultipartFile> files,
                                @AuthenticationPrincipal CustomUserDetails userDetails) {

        postService.createPost(userDetails.getUserId(), requestDTO, files);
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
    public PostDetailDTO show(@RequestParam Long postId,
                                 @AuthenticationPrincipal CustomUserDetails userDetails){
        return postService.findDetailPost(userDetails.getUserId(), postId);
    }

    @GetMapping("/heart/insert")
    public String insertHeart(@RequestParam Long postId,
                                @AuthenticationPrincipal CustomUserDetails userDetails) {

        postService.insertHeart(userDetails.getUserId(), postId);
        return "good";
    }

    @GetMapping("/heart/delecte")
    public String deleteHeart(@RequestParam Long postId,
                                @AuthenticationPrincipal CustomUserDetails userDetails) {

        postService.deleteHeart(userDetails.getUserId(), postId);
        return "good";
    }




}
