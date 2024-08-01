package com.kitcat.likelion.controller;

import com.kitcat.likelion.requestDTO.LoginDTO;
import com.kitcat.likelion.requestDTO.RegisterDTO;
import com.kitcat.likelion.requestDTO.RoutineCreateDTO;
import com.kitcat.likelion.responseDTO.*;
import com.kitcat.likelion.security.custom.CustomUserDetails;
import com.kitcat.likelion.service.RoutineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/routine")
@Tag(name = "Routine API", description = "routine 도메인 관련 API")
public class RoutineController {

    private final RoutineService routineService;

    @PostMapping("/save")
    @Operation(summary = "루틴 생성 기능", description = "루틴 생성에 사용되는 API")
    @ApiResponse(responseCode = "200", description = "루틴 생성 성공", content = @Content(mediaType = "application/json"))
    public String routineSave(@RequestBody RoutineCreateDTO dto,
                              @AuthenticationPrincipal CustomUserDetails userDetails) {
        routineService.save(userDetails.getUserId(), dto);
        return "success";
    }

    @GetMapping("/list")
    @Operation(summary = "루틴 전체 조회 기능", description = "사용자가 생성한 모든 루틴을 조회하는 API")
    @ApiResponse(responseCode = "200", description = "루틴 조회 성공", content = @Content(mediaType = "application/json"))
    public List<RoutineDTO> routineList(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return routineService.getRoutineList(userDetails.getUserId());
    }

    @GetMapping("/record/{routineId}")
    @Operation(summary = "루틴 주처별 기록", description = "사용자가 선택한 루틴의 주차별 기록을 조회하는 API")
    @ApiResponse(responseCode = "200", description = "루틴 조회 성공", content = @Content(mediaType = "application/json"))
    public RoutineDetailDTO routineRecord(@PathVariable("routineId") Long routineId) {
        return routineService.getRoutineRecord(routineId);
    }

    @GetMapping("/month/{year}/{month}")
    @Operation(summary = "선택한 년 월 산책 루틴 반환", description = "선택한 년 월의 전체 루틴 기록을 반환하는 API")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json"))
    @Parameters({
            @Parameter(name = "year", description = "기록을 가져올 연도", example = "2024"),
            @Parameter(name = "month", description = "기록을 가져올 달", example = "8")
    })
    public List<DayRoutineDTO> dayRecord(@PathVariable("year") int year,
                                         @PathVariable("month") int month,
                                         @AuthenticationPrincipal CustomUserDetails userDetails) {
        return routineService.getMonthRoutine(userDetails.getUserId(), year, month);
    }
}
