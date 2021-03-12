package com.game.football.model;

import lombok.Data;
import lombok.NonNull;


@Data
public class TeamLeagueStandingRequest {

  @NonNull
  private String teamName;
  @NonNull
  private String countryName;
  @NonNull
  private String leagueName;

  public String getTeamName() {
    return teamName;
  }

  public void setTeamName(String teamName) {
    this.teamName = teamName;
  }

  public String getCountryName() {
    return countryName;
  }

  public void setCountryName(String countryName) {
    this.countryName = countryName;
  }

  public String getLeagueName() {
    return leagueName;
  }

  public void setLeagueName(String leagueName) {
    this.leagueName = leagueName;
  }
}
