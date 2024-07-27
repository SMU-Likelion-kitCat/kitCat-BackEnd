package com.kitcat.likelion.requestDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class PetCalorieDTO {

    @Schema(description = "반려견 Id", example = "1")
    private Long petId;

    @Schema(description = "반려견 소모 칼로리", example = "143")
    private int calorie;
}
