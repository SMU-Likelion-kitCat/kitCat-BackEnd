package com.kitcat.likelion.domain.enumration;

public enum RoutineTerm {
    ONE_WEEKS("1주"),
    TWO_WEEKS("2주"),
    THREE_WEEKS("3주"),
    TEN_DAYS("10일"),
    ONE_MONTHS("한 달"),
    TWO_MONTHS("두 달");

    private String routineTerm;

    RoutineTerm(String routineTerm) {
        this.routineTerm = routineTerm;
    }
}
