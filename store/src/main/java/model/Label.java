package model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
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