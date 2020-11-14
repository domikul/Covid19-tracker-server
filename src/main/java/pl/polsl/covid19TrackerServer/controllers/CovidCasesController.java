package pl.polsl.covid19TrackerServer.controllers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.covid19TrackerServer.models.CountryStats;
import pl.polsl.covid19TrackerServer.services.CovidCasesService;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/covid19Tracker")
public class CovidCasesController {

    private final CovidCasesService covidCasesService;

    public CovidCasesController(CovidCasesService covidCasesService) {
        this.covidCasesService = covidCasesService;
    }

    @GetMapping(value = "/total/world")
    public ResponseEntity<List<CountryStats>> getWorldLatestData() throws ParseException {
        final List<CountryStats> latestCasesList = covidCasesService.showWorldCasesInTimeRange(null, null);
        return ResponseEntity.ok(latestCasesList);
    }

    @GetMapping(value = "/total/global")
    public ResponseEntity<CountryStats> getGlobalLatestData() throws ParseException {
        final CountryStats latestGlobalCases = covidCasesService.showGlobalCasesInTimeRange(null, null);
        return ResponseEntity.ok(latestGlobalCases);
    }

    @GetMapping(value = "/total/{country}")
    public ResponseEntity<CountryStats> getLatestDataByCountry(@PathVariable final String country) throws ParseException {
        final CountryStats latestCases = covidCasesService.showCasesByCountryInTimeRange(country, null, null);
        return ResponseEntity.ok(latestCases);
    }

    @GetMapping(value = "/partial/world")
    public ResponseEntity<List<CountryStats>> getAllCountriesDataInTimeRange(@RequestParam(name = "from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate startDate,
                                                                             @RequestParam(name = "to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate endDate) throws ParseException {
        final List<CountryStats> latestCasesList = covidCasesService.showWorldCasesInTimeRange(startDate, endDate);
        return ResponseEntity.ok(latestCasesList);
    }

}
