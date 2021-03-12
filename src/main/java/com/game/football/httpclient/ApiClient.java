package com.game.football.httpclient;

import com.game.football.model.Country;
import com.game.football.model.League;
import com.game.football.model.TeamLeagueStanding;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
public class ApiClient {

  private static final String APIKEY = "APIkey";
  private final RestTemplate restTemplate;

  @Value("${football.game.base.url}")
  private String baseUrl;

  @Value("${football.game.action.standings}")
  private String standingsAction;

  @Value("${football.game.action.countries}")
  private String countriesAction;

  @Value("${football.game.action.leagues}")
  private String leaguesAction;

  @Value("${football.game.api}")
  private String api;

  @Autowired
  public ApiClient(RestTemplate restTemplate) {
    this.restTemplate = new RestTemplate();
  }

  @HystrixCommand(fallbackMethod = "getCountries_Fallback",  commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000") })
  public Country[] getCountries() {
    Map<String, String> queryParams = new HashMap<>();
    queryParams.put("action", countriesAction);
    UriComponentsBuilder builder = getUriComponentsBuilder(baseUrl, queryParams);
    return this.restTemplate
        .exchange(builder.toUriString(), HttpMethod.GET, new HttpEntity<>(getHeaders()),
            Country[].class).getBody();
  }

  private Country[] getCountries_Fallback() {
    return new Country[]{
            new Country() };
  }

  @HystrixCommand(fallbackMethod = "getLeagues_Fallback",  commandProperties = {
      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000") })
  public League[] getLeagues(int countryId) {
    Map<String, String> queryParams = new HashMap<>();
    queryParams.put("action", leaguesAction);
    queryParams.put("country_id", String.valueOf(countryId));
    UriComponentsBuilder builder = getUriComponentsBuilder(baseUrl, queryParams);
    return this.restTemplate
        .exchange(builder.toUriString(), HttpMethod.GET, new HttpEntity<>(getHeaders()),
            League[].class).getBody();
  }

  private League[] getLeagues_Fallback(int countryId) {
    League leagues = new League();
    leagues.setCountryId(countryId);
    return new League[]{leagues};
  }

  @HystrixCommand(fallbackMethod = "getTeamStanding_Fallback",  commandProperties = {
      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000") })
  public TeamLeagueStanding[] getTeamStanding(int leagueId) {
    Map<String, String> queryParams = new HashMap<>();
    queryParams.put("action", standingsAction);
    queryParams.put("league_id", String.valueOf(leagueId));
    UriComponentsBuilder builder = getUriComponentsBuilder(baseUrl, queryParams);
    return this.restTemplate
        .exchange(builder.toUriString(), HttpMethod.GET, new HttpEntity<>(getHeaders()),
            TeamLeagueStanding[].class).getBody();
  }

  private TeamLeagueStanding[] getTeamStanding_Fallback(int leagueId) {
    TeamLeagueStanding teamStanding = new TeamLeagueStanding();
    teamStanding.setLeagueId(leagueId);
    return new TeamLeagueStanding[]{teamStanding};
  }

  private UriComponentsBuilder getUriComponentsBuilder(String url,
      Map<String, String> queryParams) {
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
        .queryParam(APIKEY, api);
    queryParams.forEach(builder::queryParam);
    return builder;
  }

  private HttpHeaders getHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
    return headers;
  }
}
