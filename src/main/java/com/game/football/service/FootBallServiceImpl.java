package com.game.football.service;


import com.game.football.dto.TeamStandingDto;
import com.game.football.exceptions.BadRequestException;
import com.game.football.httpclient.ApiClient;
import com.game.football.model.Country;
import com.game.football.model.League;
import com.game.football.model.TeamLeagueStanding;
import com.game.football.model.TeamLeagueStandingRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class FootBallServiceImpl implements FootBallService {

  private final ApiClient apiClient;

  private static final Logger log = LoggerFactory.getLogger(FootBallServiceImpl.class);

  @Autowired
  public FootBallServiceImpl(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  @Override
  public TeamStandingDto getTeamStanding(TeamLeagueStandingRequest teamStandingRequest) {
    TeamLeagueStanding teamStandingDefault = getDefaultTeamStanding(teamStandingRequest);
    List<Country> countries = getCountries();
    Country country = getCountryByName(teamStandingRequest, countries);
    if (!isValidateCountryResponse(teamStandingRequest, teamStandingDefault, country)) {
      return TeamStandingDto.from(teamStandingDefault);
    }

    teamStandingDefault.setCountryId(country.getId());

    List<League> leaguesList = getLeagues(country.getId());
    League leagues = getLeaguesByName(teamStandingRequest, leaguesList);
    if (!isValidLeagueResponse(teamStandingRequest, leagues)) {
      return (TeamStandingDto.from(teamStandingDefault));
    }
    teamStandingDefault.setLeagueId(leagues.getLeagueId());
    List<TeamLeagueStanding> teamStandings = getTeamStanding(leagues.getLeagueId());
    log.info("team standing found {}", teamStandings.toString());

    TeamLeagueStanding teamStandingsFiltered = getFilteredTeamStanding(teamStandingRequest,
            teamStandings);
    teamStandingsFiltered.setCountryId(country.getId());
    log.info("team standing filtered found {}", teamStandingsFiltered.toString());
    if (teamStandingsFiltered.getTeamId() == 0) {
      return TeamStandingDto.from(teamStandingDefault);
    }

    return TeamStandingDto.from(teamStandingsFiltered);
  }

  private Country getCountryByName(TeamLeagueStandingRequest teamStandingRequest,
                                   List<Country> countries) {
    return countries.stream()
            .filter(c -> teamStandingRequest.getCountryName().equalsIgnoreCase(c.getName())).findFirst()
            .orElse(new Country());
  }

  private League getLeaguesByName(TeamLeagueStandingRequest teamStandingRequest,
                                  List<League> leaguesList) {
    return leaguesList.stream()
            .filter(l -> teamStandingRequest.getLeagueName().equalsIgnoreCase(l.getLeagueName()))
            .findFirst().orElse(new League());
  }

  private TeamLeagueStanding getFilteredTeamStanding(TeamLeagueStandingRequest teamStandingRequest,
                                                     List<TeamLeagueStanding> teamStandings) {
    return teamStandings.stream()
            .filter(t -> teamStandingRequest.getTeamName().equalsIgnoreCase(t.getTeamName()))
            .findFirst().orElse(new TeamLeagueStanding());
  }

  private boolean isValidLeagueResponse(TeamLeagueStandingRequest teamStandingRequest, League leagues) {
    if (Objects.isNull(leagues)) {
      throw new BadRequestException("leagues Not Found by name " + teamStandingRequest.getLeagueName());
    }
    log.info("league found {}", leagues.toString());
    if (leagues.getLeagueId() == 0) {
      return false;
    }
    return true;
  }

  private boolean isValidateCountryResponse(TeamLeagueStandingRequest teamStandingRequest,
                                            TeamLeagueStanding teamStandingDefault, Country country) {
    if (Objects.isNull(country)) {
      throw new BadRequestException("Country Not Found by name " + teamStandingRequest.getCountryName());
    }
    log.info("Country found {}", country.toString());

    if (country.getId() == 0) {
      teamStandingDefault.setCountryId(0);
      return false;
    }
    return true;
  }

  private TeamLeagueStanding getDefaultTeamStanding(TeamLeagueStandingRequest teamStandingRequest) {
    TeamLeagueStanding teamStanding = new TeamLeagueStanding();
    teamStanding.setTeamName(teamStandingRequest.getTeamName());
    teamStanding.setCountryName(teamStandingRequest.getCountryName());
    teamStanding.setLeagueName(teamStandingRequest.getLeagueName());
    return teamStanding;
  }

  private List<Country> getCountries() {
    return new ArrayList<>(Arrays.asList(apiClient.getCountries()));
  }

  private List<League> getLeagues(int countryId) {
    return new ArrayList<>(Arrays.asList(apiClient.getLeagues(countryId)));
  }


  private List<TeamLeagueStanding> getTeamStanding(int leagueId) {
    return new ArrayList<>(Arrays.asList(apiClient.getTeamStanding(leagueId)));
  }
}
