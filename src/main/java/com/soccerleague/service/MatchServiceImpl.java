package com.soccerleague.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soccerleague.model.League;
import com.soccerleague.model.Match;
import com.soccerleague.model.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MatchServiceImpl implements MatchService{
    private static final Logger log = LoggerFactory.getLogger(MatchScheduleBuilder.class);
    @Value("${league.teamsFile}")
    Resource resourceFile;
    @Autowired
    private ObjectMapper objectMapper;
    @Value("#{new java.text.SimpleDateFormat('yyyy-MM-dd').parse('${league.startDate}')}")
    private Date date;
    @Value("${league.breakInDays}")
    private int leagueBreakInDays;
    @Value("${league.matchAfterDays}")
    private int leagueMatchAfterDays;
    @Value("${league.teamInMatch}")
    private int leagueTeamsInAMatch;

    @Cacheable("teams")
    @Override
    public List<Team> getTeams() {
        List<Team> teams = new ArrayList<>();
        try{
            League league = objectMapper.readValue(resourceFile.getFile(), League.class);
            teams.addAll(league.getTeams());
        } catch (IOException ioException){
            log.error("Exception: %s", ioException.getMessage());
        }

        return teams;
    }

    @Override
    public void createScheduleStartingFrom(Date fromDate){
        getMatchScheduleBuilder().createScheduleFromDate(date);

    }

    private MatchScheduleBuilder getMatchScheduleBuilder(){
        return new MatchScheduleBuilder(getTeams(), date, leagueMatchAfterDays, leagueTeamsInAMatch);
    }

    @Override
    public List<Match> getMatchSchedule(){
        return getMatchScheduleBuilder().createScheduleFromDate(date).getScheduledMatches();
    }

    @Override
    public List<Match> getFullScheduleAfterBreak() {
        return getMatchScheduleBuilder().createScheduleFromDate(date).addBreakInDays(leagueBreakInDays).getScheduledMatches();
    }

    @Override
    public void printFullScheduleAfterBreak(){
        getMatchScheduleBuilder().createScheduleFromDate(date).addBreakInDays(leagueBreakInDays).createFullScheduleAfterBreak().printLeagueSchedule();
    }

}
