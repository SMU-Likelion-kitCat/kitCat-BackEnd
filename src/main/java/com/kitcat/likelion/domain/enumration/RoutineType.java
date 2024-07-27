package com.kitcat.likelion.domain.enumration;

import lombok.Getter;

@Getter
public enum RoutineType {
    TIME("시간 목표"),
    CALORIE("칼로리 목표"),
    DISTANCE("산책 거리 목표");

    private String routineType;

    RoutineType(String routineType) {
        this.routineType = routineType;
    }

    public static RoutineType fromString(String text) {
        for (RoutineType rt : RoutineType.values()) {
            if (rt.routineType.equals(text)) {
                return rt;
            }
        }
        throw new IllegalArgumentException("No enum constant with text " + text);
    }
}
