package com.example.polyville2.model;

import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;

@JsonTypeName("label")

public enum Label implements Serializable {
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

