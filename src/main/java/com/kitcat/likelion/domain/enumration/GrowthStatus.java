package com.kitcat.likelion.domain.enumration;

import lombok.Getter;

@Getter
public enum GrowthStatus {
    GROWING_UP_LESS_FOUR_MONTH("성장기(4개월 미만)"),
    GROWING_UP_LESS_TWELVE_MONTH("성장기(4~12개월)"),
    NOT_NEUTERED_ADULT("미중성 성견"),
    NEUTERED_ADULT("중성화 완료 성견"),
    NEED_LOSE_WEIGHT_ADULT("체중 감량 필요 성견"),
    NEED_GAIN_WEIGHT_ADULT("체중 증량 필요 성견");

    private String growthStatus;

    private GrowthStatus(String growthStatus) {
        this.growthStatus = growthStatus;
    }

}
