package pl.polsl.Covid19TrackerServer.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.Covid19TrackerServer.models.LocationStats;
import pl.polsl.Covid19TrackerServer.services.ConfirmedCasesService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/covidTracker")
public class ConfirmedCasesController {

    private final ConfirmedCasesService confirmedCasesService;

    public ConfirmedCasesController(ConfirmedCasesService confirmedCasesService) {
        this.confirmedCasesService = confirmedCasesService;
    }

    @GetMapping(value = "/latestStats")
    public ResponseEntity<List<LocationStats>> getLatestData() throws IOException, InterruptedException {
        final List<LocationStats> latestCasesList = confirmedCasesService.getLatestConfirmedCases();
        return ResponseEntity.ok(latestCasesList);
    }

    @GetMapping(value = "/latestStats/{country}")
    public ResponseEntity<LocationStats> getLatestDataByCountry(@PathVariable final String country) throws IOException, InterruptedException {
        final LocationStats latestCases = confirmedCasesService.getLatestConfirmedCasesByCountry(country);
        return ResponseEntity.ok(latestCases);
    }

}
