# football-score-info

### Problem statement:
    Develop, Test and Deploy a microservice to find standings of a team playing league football match using country name, league name and team name. The
    service should be accessible via web browser on internet and end user should be able to view results by changing previously listed parameters. Output of
    this service should be presented in web browser using any one of Javascript framework, HTML or JSON. And the service should be ready to be released
    to production or live environment. In output, display following:
    Country ID & Name: (<ID>) - <name>
    League ID & Name: (<ID>) - <name>
    Team ID & Name: (<ID>) - <name>
    Overall League Position: <position>
    
            APIs to be used, to fetch data, are available ->
            https://apifootball.com/documentation/

### Technologies Used
    Java 8
    SpringBoot
    Junit

    
### API 
http://localhost:8080/api/service/v1/score?teamName=Leicester&countryName=France&leagueName=Premier%20League
   
  {
  "country": "(46) - France",
  "league": "(0) - Premier League",
  "team": "(0) - Leicester",
  "overallPosition": 0
  }

## Swagger

## Hystrix

### Unit Test case - Junit
// todo
### BDD test case - Integration Testing using cucumber
//todo
