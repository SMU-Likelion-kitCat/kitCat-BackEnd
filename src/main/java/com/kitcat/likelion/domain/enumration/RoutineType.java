package com.kitcat.likelion.domain.enumration;

public enum RoutineType {
    TIME("시간 목표를 달성하는 방식"),
    CALORIE("칼로리 목표를 달성하는 방식"),
    DISTANCE("산책 거리 목표를 달성하는 방식");

    private String routineType;

    RoutineType(String routineType) {
        this.routineType = routineType;
    }
}
