package com.kitcat.likelion.domain.enumration;

import lombok.Getter;

@Getter
public enum RoutineBase {
    DAY("하루"),
    WEEK("일주일");

    private String routineBase;

    RoutineBase(String routineBase) {
        this.routineBase = routineBase;
    }

    public static RoutineBase fromString(String text) {
        for (RoutineBase rb : RoutineBase.values()) {
            if (rb.routineBase.equals(text)) {
                return rb;
            }
        }
        throw new IllegalArgumentException("No enum constant with text " + text);
    }
}
