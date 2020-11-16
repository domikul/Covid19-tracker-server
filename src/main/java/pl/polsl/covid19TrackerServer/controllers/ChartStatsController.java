package pl.polsl.covid19TrackerServer.controllers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.covid19TrackerServer.models.ChartStats;
import pl.polsl.covid19TrackerServer.models.enumerations.FileType;
import pl.polsl.covid19TrackerServer.services.ChartDataService;

import java.time.LocalDate;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/covid19Tracker/chart/")
public class ChartStatsController {

    private final ChartDataService chartDataService;

    public ChartStatsController(ChartDataService chartDataService) {
        this.chartDataService = chartDataService;
    }

    @GetMapping(value = "/{country}/{status}")
    public ResponseEntity<ChartStats> getCountryDataForChart(
            @PathVariable final String country,
            @RequestParam(name = "from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate startDate,
            @RequestParam(name = "to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate endDate,
            @PathVariable final String status) {
        FileType enumStats = Enum.valueOf(FileType.class, status.toUpperCase());
        final ChartStats latestCasesList = chartDataService.reportChartDataForCountryInTimeRange(country, enumStats, startDate, endDate);
        return ResponseEntity.ok(latestCasesList);
    }

    @GetMapping(value = "/global/all/{status}")
    public ResponseEntity<ChartStats> getGlobalDataForChart(@PathVariable final String status) {
        FileType enumStats = Enum.valueOf(FileType.class, status.toUpperCase());
        final ChartStats latestCasesList = chartDataService.reportGlobalChartDataInAllTheTime(enumStats);
        return ResponseEntity.ok(latestCasesList);
    }

}
