package com.kitcat.likelion.requestDTO;

import com.kitcat.likelion.domain.enumration.RoutineTerm;
import com.kitcat.likelion.domain.enumration.RoutineType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class RoutineCreateDTO {

    @Schema(description = "루틴 이름", example = "달리기")
    private String name;

    @Schema(description = "루틴 최종 목표(시간은 분 단위, 거리는 m 단위로 치환해서 보내 주세요)", example = "55")
    private int target;

    @Schema(description = "단계별 목표 단위(시간은 분 단위, 거리는 m 단위로 치환해서 보내 주세요)", example = "5")
    private int step;

    @Schema(description = "하이라이트", example = "#FFFFFF")
    private String colorCode;

    @Schema(description = "루틴 유형 \n" +
            "    TIME(\"시간 목표\"),\n" +
            "    CALORIE(\"칼로리 목표\"),\n" +
            "    DISTANCE(\"산책 거리 목표\")", example = "시간 목표")
    private String routineType;

    @Schema(description = "루틴 기간 \n" +
            "    ONE_WEEKS(\"1주\"),\n" +
            "    TWO_WEEKS(\"2주\"),\n" +
            "    THREE_WEEKS(\"3주\"),\n" +
            "    FOUR_WEEKS(\"4주\"),\n" , example = "2주")
    private String routineTerm;

    @Schema(description = "루틴 빈도 - 기준\n" +
            "    DAY(\"하루\"),\n" +
            "    WEEK(\"일주일\");", example = "일주일")
    private String routineBase;

    @Schema(description = "루틴 빈도 - 회수", example = "3")
    private int count;
}
