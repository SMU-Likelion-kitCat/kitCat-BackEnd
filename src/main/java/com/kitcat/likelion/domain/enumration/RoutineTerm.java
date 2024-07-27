package com.kitcat.likelion.domain.enumration;

import lombok.Getter;

@Getter
public enum RoutineTerm {
    ONE_WEEKS("1주"),
    TWO_WEEKS("2주"),
    THREE_WEEKS("3주"),
    FOUR_WEEKS("4주");

    private String routineTerm;

    RoutineTerm(String routineTerm) {
        this.routineTerm = routineTerm;
    }

    public static RoutineTerm fromString(String text) {
        for (RoutineTerm rt : RoutineTerm.values()) {
            if (rt.routineTerm.equals(text)) {
                return rt;
            }
        }
        throw new IllegalArgumentException("No enum constant with text " + text);
    }
}
