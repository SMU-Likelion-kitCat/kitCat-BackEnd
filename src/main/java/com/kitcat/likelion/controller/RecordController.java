package com.kitcat.likelion.controller;

import com.kitcat.likelion.requestDTO.RecordCreateDTO;
import com.kitcat.likelion.responseDTO.DayRecordDTO;
import com.kitcat.likelion.security.custom.CustomUserDetails;
import com.kitcat.likelion.service.RecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/month/{year}/{month}")
    @Operation(summary = "선택한 년 월 산책 기록 반환", description = "선택한 년 월의 전체 산책 기록을 반환하는 API")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json"))
    public List<DayRecordDTO> dayRecord(@PathVariable("year") int year,
                                        @PathVariable("month") int month,
                                        @AuthenticationPrincipal CustomUserDetails userDetails) {
        return recordService.getMonthRecord(userDetails.getUserId(), year, month);
    }
}
