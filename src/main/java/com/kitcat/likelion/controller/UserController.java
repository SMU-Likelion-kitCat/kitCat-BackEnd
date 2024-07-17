package com.kitcat.likelion.controller;

import com.kitcat.likelion.requestDTO.LoginDTO;
import com.kitcat.likelion.requestDTO.RegisterDTO;
import com.kitcat.likelion.security.custom.CustomUserDetails;
import com.kitcat.likelion.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@Tag(name = "User API", description = "user 도메인 관련 API")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "회원 가입 기능", description = "회원가입에 사용되는 API")
    @ApiResponse(responseCode = "200", description = "회원 가입 성공", content = @Content(mediaType = "application/json"))
    @Parameters({
            @Parameter(name = "email", description = "아이디(이메일)", example = "user2@naver.com"),
            @Parameter(name = "password", description = "비밀번호", example = "1234"),
            @Parameter(name = "nickname", description = "닉네임", example = "user2")
    })
    public String register(@RequestBody RegisterDTO dto) {
        userService.register(dto);
        return "success";
    }

    @PostMapping("/login")
    @Operation(summary = "회원 로그인 기능", description = "로그인에 사용되는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "로그인 실패", content = @Content(mediaType = "application/json"))
    })
    @Parameters({
            @Parameter(name = "email", description = "아이디(이메일)", example = "test@naver.com"),
            @Parameter(name = "password", description = "비밀번호", example = "1234"),
    })
    public ResponseEntity<String> login(@RequestBody LoginDTO dto) {
        String token = userService.login(dto);

        if(token.equals("user not found") || token.equals("password error")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(token);
        }

        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    @GetMapping("/check/nickname/{nickname}")
    @Operation(summary = "닉네임 중복 검사", description = "회원 가입 시 닉네임 중복 검사 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용 가능한 닉네임", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "409", description = "중복 닉네임", content = @Content(mediaType = "application/json"))
    })
    @Parameter(name = "nickname", description = "중복 검사할 닉네임", example = "user1")
    public ResponseEntity<String> checkNickname(@PathVariable("nickname") String nickname) {
        String nicknameStatus = userService.validateDuplicateNickname(nickname);

        if(nicknameStatus.equals("duplicate")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(nicknameStatus);
        }

        return ResponseEntity.status(HttpStatus.OK).body(nicknameStatus);
    }

    @GetMapping("/check/email/{email}")
    @Operation(summary = "아이디(이메일) 중복 검사", description = "회원 가입 시 아이디(이메일) 중복 검사 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용 가능한 아이디", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "409", description = "중복 아이디", content = @Content(mediaType = "application/json"))
    })
    @Parameter(name = "email", description = "중복 검사할 아이디(이메일)", example = "test@naver.com")
    public ResponseEntity<String> checkEmail(@PathVariable("email") String email) {
        String emailStatus = userService.validateDuplicateEmail(email);

        if(emailStatus.equals("duplicate")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(emailStatus);
        }

        return ResponseEntity.status(HttpStatus.OK).body(emailStatus);
    }

    @GetMapping("/test")
    @PreAuthorize("isAuthenticated()")
    public Long test(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return userDetails.getUserId();
    }
}
