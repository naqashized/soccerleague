package com.soccerleague;

import com.soccerleague.model.Match;
import com.soccerleague.model.Team;
import com.soccerleague.service.DateUtil;
import com.soccerleague.service.MatchService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MatchServiceTests {
    @Autowired
    private MatchService matchService;
    @Value("#{new java.text.SimpleDateFormat('yyyy-MM-dd').parse('${league.startDate}')}")
    private Date leagueStartDate;
    @Value("${league.matchAfterDays}")
    private int leagueMatchAfterDays;

    @Test
    public void shouldReturnTeams(){
        List<Team> teams = matchService.getTeams();
        Assertions.assertTrue(teams.size()>0);
    }

    @Test
    public void shouldReturnScheduledMatchesBeforeBreak(){
        List<Match> matches = matchService.getMatchSchedule();
        Assertions.assertTrue(matches.size()>0);
    }
    @Test
    public void shouldValidateScheduledMatchDate(){
        List<Match> matches = matchService.getMatchSchedule();
        Date firstMatchDate = matches.get(0).getDate();
        Assertions.assertEquals(leagueStartDate, firstMatchDate);
        Date secondMatchDate = matches.get(1).getDate();
        Assertions.assertEquals(secondMatchDate, DateUtil.addDays(leagueMatchAfterDays, leagueStartDate));
    }

    @Test
    public void shouldReturnScheduledMatchesAfterBreak(){
        List<Match> matches = matchService.getMatchSchedule();
        Assertions.assertTrue(matches.size()>0);
    }
    @Test
    public void shouldPrintFullSchedule() {
        matchService.printFullScheduleAfterBreak();
    }


}
