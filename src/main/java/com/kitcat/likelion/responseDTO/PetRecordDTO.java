package com.kitcat.likelion.responseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PetRecordDTO {
    @Schema(description = "반려견 이미지 이름")
    private String image;

    @Schema(description = "반려견 소모 칼로리")
    private int calorie;
}
