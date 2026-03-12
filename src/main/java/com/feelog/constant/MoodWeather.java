package com.feelog.constant;

public enum MoodWeather {
    SUNNY("맑음"),
    CLOUDY("흐림"),
    RAINY("비"),
    SNOWY("눈"),
    WINDY("바람");

    private final String label;

    MoodWeather(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
