package com.example.polyville2.model;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("label")

public enum Label {
    STORM,
    RAIN,
    SNOW,
    MIST,
    SUNNY,
    CLOUDS,
    UNDEFINED,
    HOT,
    WARM,
    MILD,
    CHILLY,
    COLD,
    FREEZE,
    FOOD
}

