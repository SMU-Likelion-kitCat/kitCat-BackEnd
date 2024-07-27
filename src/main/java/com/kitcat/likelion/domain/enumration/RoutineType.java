package com.kitcat.likelion.domain.enumration;

public enum RoutineType {
    TIME("시간 목표"),
    CALORIE("칼로리 목표"),
    DISTANCE("산책 거리 목표");

    private String routineType;

    RoutineType(String routineType) {
        this.routineType = routineType;
    }
}
