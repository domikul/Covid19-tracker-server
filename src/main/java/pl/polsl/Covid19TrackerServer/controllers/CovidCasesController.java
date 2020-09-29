package pl.polsl.Covid19TrackerServer.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.Covid19TrackerServer.models.CountryStats;
import pl.polsl.Covid19TrackerServer.services.CovidCasesService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/covidTracker")
public class CovidCasesController {

    private final CovidCasesService covidCasesService;

    public CovidCasesController(CovidCasesService covidCasesService) {
        this.covidCasesService = covidCasesService;
    }

    //http://localhost:8080/covidTracker/latestStats
    @GetMapping(value = "/latestStats")
    public ResponseEntity<List<CountryStats>> getLatestData() {
        final List<CountryStats> latestCasesList = covidCasesService.allCountriesLatestCases();
        return ResponseEntity.ok(latestCasesList);
    }

    //http://localhost:8080/covidTracker/latestStats/Poland
    @GetMapping(value = "/latestStats/{country}")
    public ResponseEntity<CountryStats> getLatestDataByCountry(@PathVariable final String country) {
        final CountryStats latestCases = covidCasesService.singleCountryLatestCases(country);
        return ResponseEntity.ok(latestCases);
    }

}
