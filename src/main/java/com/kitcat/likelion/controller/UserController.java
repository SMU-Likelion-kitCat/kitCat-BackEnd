package com.kitcat.likelion.controller;

import com.kitcat.likelion.requestDTO.LoginDTO;
import com.kitcat.likelion.requestDTO.ModifyUserDTO;
import com.kitcat.likelion.requestDTO.PetsDTO;
import com.kitcat.likelion.requestDTO.RegisterDTO;
import com.kitcat.likelion.responseDTO.UserInfoDTO;
import com.kitcat.likelion.security.custom.CustomUserDetails;
import com.kitcat.likelion.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@Tag(name = "User API", description = "user 도메인 관련 API")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "회원 가입 기능", description = "회원가입에 사용되는 API")
    @ApiResponse(responseCode = "200", description = "회원 가입 성공", content = @Content(mediaType = "application/json"))
    public String register(@RequestBody RegisterDTO dto) {
        userService.register(dto);

        String token = userService.login(new LoginDTO(dto.getEmail(), dto.getPassword()));
        insertToken(token);

        return token;
    }

    @PostMapping("/login")
    @Operation(summary = "회원 로그인 기능", description = "로그인에 사용되는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "로그인 실패", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<String> login(@RequestBody LoginDTO dto) {
        System.out.println("dto = " + dto);
        String status = userService.login(dto);

        if(status.equals("user not found") || status.equals("password error")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(status);
        }

        insertToken(status);
        return ResponseEntity.status(HttpStatus.OK).body(status);
    }

    public void insertToken(String token) {
        Cookie cookie = new Cookie("accessToken", token);

        cookie.setPath("/");
        cookie.setSecure(false);
        cookie.setMaxAge(60 * 60 * 24 * 30); // 30 일
        cookie.setHttpOnly(true);

        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.addCookie(cookie);
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

    @GetMapping("/info")
    @Operation(summary = "사용자 정보 조회", description = "사용자의 정보를 조회하는 API")
    @ApiResponse(responseCode = "200", description = "조회 완료", content = @Content(mediaType = "application/json"))
    public UserInfoDTO info(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return userService.getInfo(userDetails.getUserId());
    }

    @GetMapping("/test")
    public String test() {
        return "success";
    }


    @PostMapping("/modify/user")
    public String modifyUserInfo(@AuthenticationPrincipal CustomUserDetails userDetails,
                             @RequestPart(value = "dto") ModifyUserDTO modifyUserDTO) {
        Long userId = userDetails.getUserId();
        userService.modifyUserInfo(userId, modifyUserDTO);
        return "good";
    }

}
