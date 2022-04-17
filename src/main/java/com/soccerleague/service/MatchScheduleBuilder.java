package com.soccerleague.service;

import com.soccerleague.model.Match;
import com.soccerleague.model.Team;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

public class MatchScheduleBuilder {
    private List<Team> teamList;
    private List<Match> matchList;
    private Date roundStartDate;
    private int matchAfterDays;
    private int leagueTeamsInAMatch;

    public MatchScheduleBuilder(List<Team> teams, Date startDate, int matchAfterDays, int leagueTeamsInAMatch){
        this.teamList = teams;
        matchList = new ArrayList<>();
        this.roundStartDate = startDate;
        this.matchAfterDays = matchAfterDays;
        this.leagueTeamsInAMatch = leagueTeamsInAMatch;
    }

    public MatchScheduleBuilder createScheduleFromDate(Date fromDate){
        List<List<Team>> matches = getMatches(teamList);
        int matchNumber =0;
        for(List<Team> teamList: matches){
            Match match = new Match(teamList.get(0), teamList.get(1),DateUtil.addDays(matchAfterDays*matchNumber, fromDate));
            matchList.add(match);
            matchNumber ++;
        }
        return this;
    }

    public List<Match> getScheduledMatches(){
        return matchList;
    }

    public void printLeagueSchedule(){
        System.out.format("%10s%32s%32s%n","Date","Team1","Team2");
        Consumer<Match> consumer = (Match match) -> System.out.format("%10s%32s%32s%n", formatDate(match.getDate()), match.getHostTeam().getName(), match.getGuestTeam().getName());;
        forEach(matchList, consumer);
    }
    private static <T> void forEach(List<T> list, Consumer<T> consumer) {
        for (T t : list) {
            consumer.accept(t);
        }
    }
    private String formatDate(Date date){
        String pattern = "dd.MM.yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

     public MatchScheduleBuilder addBreakInDays(int numberOfDays){
        Date endDate = DateUtil.addDays(matchAfterDays*matchList.size(), roundStartDate);
        roundStartDate = DateUtil.addDays(numberOfDays, endDate);
        return this;
     }

     public MatchScheduleBuilder createFullScheduleAfterBreak(){
        List<Match> roundMatches = new ArrayList<>();
        int nextRoundMatchNumber = 1;
        for(Match match: matchList){
            Match matchAfterSwitch = scheduleNewMatch(match, nextRoundMatchNumber);
            roundMatches.add(matchAfterSwitch);
            nextRoundMatchNumber++;
        }
        matchList.addAll(roundMatches);
        return this;
     }

     private Match scheduleNewMatch(Match match, int nextRoundMatchNumber){
        int daysToAdd = matchAfterDays*(nextRoundMatchNumber-1);
        return new Match( match.getGuestTeam(), match.getHostTeam(), DateUtil.addDays(daysToAdd, roundStartDate) );
     }

    List<List<Team>> getMatches(List<Team> teams){
        return ListPartition.ofSize(
                teams, leagueTeamsInAMatch
        );
    }


}
