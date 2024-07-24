package com.kitcat.likelion.requestDTO;

import com.kitcat.likelion.domain.enumration.GrowthStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@NoArgsConstructor
public class PetsDTO {

    @Schema(description = "반려견 이름", example = "초코")
    private String name;

    @Schema(description = "반려견 체중", example = "5.2")
    private double weight;

    @Schema(description = "반려견 성장 상태 " +
            "GROWING_UP_LESS_FOUR_MONTH(\"성장기(4개월 미만)\"),\n" +
            "    GROWING_UP_LESS_TWELVE_MONTH(\"성장기(4~12개월)\"),\n" +
            "    NOT_NEUTERED_ADULT(\"미중성 성견\"),\n" +
            "    NEUTERED_ADULT(\"중성화 완료 성견\"),\n" +
            "    NEED_LOSE_WEIGHT_ADULT(\"체중 감량 필요 성견\"),\n" +
            "    NEED_GAIN_WEIGHT_ADULT(\"체중 증량 필요 성견\")", example = "GROWING_UP_LESS_TWELVE_MONTH")
    private String growthStatus;
}
