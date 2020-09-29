package pl.polsl.Covid19TrackerServer.controllers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.Covid19TrackerServer.models.CountryStats;
import pl.polsl.Covid19TrackerServer.services.CovidCasesService;

import java.time.LocalDate;
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
        final CountryStats latestCases = covidCasesService.singleCountrySingleDate(country, LocalDate.now().minusDays(1));
        return ResponseEntity.ok(latestCases);
    }

    //http://localhost:8080/covidTracker/latestStats/France/date?requestDate=2020-09-16
    @GetMapping(value = "/latestStats/{country}/date")
    public ResponseEntity<CountryStats> getLatestDataByCountryAndDate(@PathVariable final String country,
                                                                      @RequestParam(name = "requestDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate date) {
        final CountryStats latestCases = covidCasesService.singleCountrySingleDate(country, date);
        return ResponseEntity.ok(latestCases);
    }

}
