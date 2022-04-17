package com.soccerleague.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Team {
    private String name;
    @JsonProperty("founding_date")
    private String foundingDate;
}
