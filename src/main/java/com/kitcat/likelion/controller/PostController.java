package com.kitcat.likelion.controller;

import com.kitcat.likelion.domain.Post;
import com.kitcat.likelion.requestDTO.PostCreateRequestDTO;
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

}
