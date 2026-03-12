package com.feelog.constant;

public enum Emotion {
    HAPPY("행복"),
    JOY("기쁨"),
    SAD("슬픔"),
    ANGER("분노"),
    DEPRESSED("우울"),
    FRUSTRATED("좌절"),
    EXCITED("설렘"),
    COMFORT("위로"),
    FEAR("공포"),
    LONELY("외로움"),
    MISSING("그리움"),
    GRATEFUL("감사");

    private final String label;

    Emotion(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
