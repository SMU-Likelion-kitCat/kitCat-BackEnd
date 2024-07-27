package com.kitcat.likelion.domain.enumration;

public enum RoutineTerm {
    ONE_WEEKS("1주"),
    TWO_WEEKS("2주"),
    THREE_WEEKS("3주"),
    FOUR_WEEKS("4주");

    private String routineTerm;

    RoutineTerm(String routineTerm) {
        this.routineTerm = routineTerm;
    }
}
