package com.game.football.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class League {

  @JsonProperty("country_id")
  private int countryId;

  @JsonProperty("country_name")
  private String countryName;

  @JsonProperty("league_name")
  private String leagueName;

  @JsonProperty("league_id")
  private int leagueId;

  public int getCountryId() {
    return countryId;
  }

  public void setCountryId(int countryId) {
    this.countryId = countryId;
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

  public int getLeagueId() {
    return leagueId;
  }

  public void setLeagueId(int leagueId) {
    this.leagueId = leagueId;
  }
}
