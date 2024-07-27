package com.kitcat.likelion.controller;

import com.kitcat.likelion.requestDTO.RecordCreateDTO;
import com.kitcat.likelion.security.custom.CustomUserDetails;
import com.kitcat.likelion.service.RecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/record")
@Tag(name = "Record API", description = "산책 기록 관련 API")

public class RecordController {

    private final RecordService recordService;

    @PostMapping("/save")
    @Operation(summary = "산책 기록 저장", description = "산책 완료후 기록을 저장하는 API")
    @ApiResponse(responseCode = "200", description = "기록 성공", content = @Content(mediaType = "application/json"))
    public String save(@RequestBody RecordCreateDTO dto,
                     @AuthenticationPrincipal CustomUserDetails userDetails) {
        recordService.save(userDetails.getUserId(), dto);
        return "success";
    }
}
