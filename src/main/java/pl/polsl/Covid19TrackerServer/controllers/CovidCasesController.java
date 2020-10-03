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
        final List<CountryStats> latestCasesList = covidCasesService.allCountriesTimeRangeCases(null, LocalDate.now().minusDays(1));
        return ResponseEntity.ok(latestCasesList);
    }

    //http://localhost:8080/covidTracker/latestStats/Poland
    @GetMapping(value = "/latestStats/{country}")
    public ResponseEntity<CountryStats> getLatestDataByCountry(@PathVariable final String country) {
        final CountryStats latestCases = covidCasesService.singleCountryCasesInTimeRange(country, null, LocalDate.now().minusDays(1));
        return ResponseEntity.ok(latestCases);
    }

    //http://localhost:8080/covidTracker/latestStats/dateRange?startDate=2020-09-05&endDate=2020-09-08
    @GetMapping(value = "/latestStats/dateRange")
    public ResponseEntity<List<CountryStats>> getAllCountriesDataInTimeRange(@RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate startDate,
                                                                             @RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate endDate) {
        final List<CountryStats> latestCasesList = covidCasesService.allCountriesTimeRangeCases(startDate, endDate);
        return ResponseEntity.ok(latestCasesList);
    }



}
