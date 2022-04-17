package com.soccerleague.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class Match {
    private Team hostTeam;
    private Team guestTeam;
    private Date date;
}
