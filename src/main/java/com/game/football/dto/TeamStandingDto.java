package com.game.football.dto;

import com.game.football.model.TeamLeagueStanding;
import lombok.Data;

import java.util.Objects;

@Data
public class TeamStandingDto {
  private String country;
  private String league;
  private String team;
  private int overallPosition;

  public static TeamStandingDto from(TeamLeagueStanding teamLeagueStanding) {
    TeamStandingDto dto = new TeamStandingDto();
    if (Objects.nonNull(teamLeagueStanding)) {
      dto.setCountry(new StringBuilder().append("(").append(teamLeagueStanding.getCountryId()).append(") - ").append(teamLeagueStanding.getCountryName()).toString());
      dto.setLeague(new StringBuilder().append("(").append(teamLeagueStanding.getLeagueId()).append(") - ").append(teamLeagueStanding.getLeagueName()).toString());
      dto.setTeam(new StringBuilder().append("(").append(teamLeagueStanding.getTeamId()).append(") - ").append(teamLeagueStanding.getTeamName()).toString());
      dto.setOverallPosition(teamLeagueStanding.getOverallPosition());
    }
    return dto;

  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getLeague() {
    return league;
  }

  public void setLeague(String league) {
    this.league = league;
  }

  public String getTeam() {
    return team;
  }

  public void setTeam(String team) {
    this.team = team;
  }

  public int getOverallPosition() {
    return overallPosition;
  }

  public void setOverallPosition(int overallPosition) {
    this.overallPosition = overallPosition;
  }
}
