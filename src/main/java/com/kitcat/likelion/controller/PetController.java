package com.kitcat.likelion.controller;

import com.kitcat.likelion.requestDTO.PetListDTO;
import com.kitcat.likelion.requestDTO.PetsDTO;
import com.kitcat.likelion.security.custom.CustomUserDetails;
import com.kitcat.likelion.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pet")
@Tag(name = "Pet API", description = "pet 도메인 관련 API")
public class PetController {

    private final PetService petService;

    @PostMapping("/save")
    @Operation(summary = "반려견 정보", description = "반려견 정보 저장하는 API")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json"))
    @PreAuthorize("isAuthenticated()")
    public String save(@RequestPart("dto") List<PetsDTO> dto,
                       @RequestPart("files") List<MultipartFile> files,
                       @AuthenticationPrincipal CustomUserDetails userDetails) throws IOException {
        petService.savePets(dto, files, userDetails.getUserId());
        return "success";
    }
}
