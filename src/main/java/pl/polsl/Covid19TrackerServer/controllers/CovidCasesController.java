package pl.polsl.Covid19TrackerServer.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.Covid19TrackerServer.services.CovidCasesService;

@RestController
@RequestMapping("/covidTracker")
public class CovidCasesController {

    private final CovidCasesService covidCasesService;

    public CovidCasesController(CovidCasesService covidCasesService) {
        this.covidCasesService = covidCasesService;
    }
//
//    @GetMapping(value = "/latestStats")
//    public ResponseEntity<List<LocationStats>> getLatestData() throws IOException, InterruptedException {
//        final List<LocationStats> latestCasesList = confirmedCasesService.getLatestConfirmedCases();
//        return ResponseEntity.ok(latestCasesList);
//    }
//
//    @GetMapping(value = "/latestStats/{country}")
//    public ResponseEntity<LocationStats> getLatestDataByCountry(@PathVariable final String country) throws IOException, InterruptedException {
//        final LocationStats latestCases = confirmedCasesService.getLatestConfirmedCasesByCountry(country);
//        return ResponseEntity.ok(latestCases);
//    }

}
