package model;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("label")

public enum Label {
    GOOD_WEATHER, BAD_WEATHER, FOOD
}
