package com.game.football.service;

import com.game.football.dto.TeamStandingDto;
import com.game.football.model.TeamLeagueStandingRequest;

public interface FootBallService {
    TeamStandingDto getTeamStanding(TeamLeagueStandingRequest teamLeagueStandingRequest);
}