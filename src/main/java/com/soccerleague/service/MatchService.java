package com.soccerleague.service;

import com.soccerleague.model.Match;
import com.soccerleague.model.Team;

import java.util.Date;
import java.util.List;

public interface MatchService {
    List<Team> getTeams();
    void createScheduleStartingFrom(Date date) ;
    List<Match> getMatchSchedule();
    List<Match> getFullScheduleAfterBreak();
    void printFullScheduleAfterBreak();
}
