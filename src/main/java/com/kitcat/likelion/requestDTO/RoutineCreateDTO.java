package com.kitcat.likelion.requestDTO;

import com.kitcat.likelion.domain.enumration.RoutineTerm;
import com.kitcat.likelion.domain.enumration.RoutineType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class RoutineCreateDTO {

    @Schema(description = "루틴 이름", example = "달리기")
    private String name;

    @Schema(description = "루틴 목표", example = "1.5")
    private String target;

    @Schema(description = "하이라이트", example = "#FFFFFF")
    private String colorCode;

    @Schema(description = "루틴 유형 \n" +
            "    TIME(\"시간 목표를 달성하는 방식\"),\n" +
            "    CALORIE(\"칼로리 목표를 달성하는 방식\"),\n" +
            "    DISTANCE(\"산책 거리 목표를 달성하는 방식\")", example = "TIME")
    private RoutineType routineType;

    @Schema(description = "루틴 기간 \n" +
            "    ONE_WEEKS(\"1주\"),\n" +
            "    TWO_WEEKS(\"2주\"),\n" +
            "    THREE_WEEKS(\"3주\"),\n" +
            "    TEN_DAYS(\"10일\"),\n" +
            "    ONE_MONTHS(\"한 달\"),\n" +
            "    TWO_MONTHS(\"두 달\")", example = "TWO_WEEKS")
    private RoutineTerm routineTerm;
}
