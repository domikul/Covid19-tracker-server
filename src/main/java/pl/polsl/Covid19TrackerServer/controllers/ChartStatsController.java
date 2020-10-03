package pl.polsl.Covid19TrackerServer.controllers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.Covid19TrackerServer.models.ChartStats;
import pl.polsl.Covid19TrackerServer.models.enumerations.FileType;
import pl.polsl.Covid19TrackerServer.services.CovidCasesService;

import java.time.LocalDate;


@RestController
@RequestMapping("/covidTracker/chart/")
public class ChartStatsController {

    private final CovidCasesService covidCasesService;

    public ChartStatsController(CovidCasesService covidCasesService) {
        this.covidCasesService = covidCasesService;
    }

    //http://localhost:8080/covidTracker/chart/Poland/confirmed/dateRange?startDate=2020-09-05&endDate=2020-09-08
    @GetMapping(value = "/{country}/{status}/dateRange")
    public ResponseEntity<ChartStats> getChartDataForCountry(
            @PathVariable final String country,
            @RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate startDate,
            @RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate endDate,
            @PathVariable final String status) {
        FileType enumStats = Enum.valueOf(FileType.class, status.toUpperCase());
        final ChartStats latestCasesList = covidCasesService.reportChartData(country, enumStats, startDate, endDate) ;
        return ResponseEntity.ok(latestCasesList);
    }

}