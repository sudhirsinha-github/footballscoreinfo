package com.game.football.controller;

import com.game.football.dto.TeamStandingDto;
import com.game.football.model.TeamLeagueStandingRequest;
import com.game.football.service.FootBallServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/api/service/v1/score")
public class FootBallScoreController {

  private final FootBallServiceImpl footBallService;
  private static final Logger log = LoggerFactory.getLogger(FootBallScoreController.class);

  @Autowired
  public FootBallScoreController(FootBallServiceImpl footBallService) {
    this.footBallService = footBallService;
  }

  @GetMapping
  public ResponseEntity<TeamStandingDto> getStandings(@Validated TeamLeagueStandingRequest teamStandingRequest) {
    log.info("Request was started {} {}", teamStandingRequest.toString(), Instant.now());
    return ResponseEntity.ok(footBallService.getTeamStanding(teamStandingRequest));
  }
}
