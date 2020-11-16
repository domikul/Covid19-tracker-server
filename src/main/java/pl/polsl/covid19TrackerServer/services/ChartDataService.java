package pl.polsl.covid19TrackerServer.services;

import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import pl.polsl.covid19TrackerServer.exceptions.IncorrectDateException;
import pl.polsl.covid19TrackerServer.models.ChartStats;
import pl.polsl.covid19TrackerServer.models.enumerations.FileType;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChartDataService {

    private final CsvFileReader csvFileReader;
    private final CovidCasesService covidCasesService;

    public ChartDataService(CsvFileReader csvFileReader, CovidCasesService covidCasesService) {
        this.csvFileReader = csvFileReader;
        this.covidCasesService = covidCasesService;
    }

    public ChartStats reportChartDataForCountryInTimeRange(String country, FileType status, LocalDate startDate, LocalDate endDate) {

        if (startDate != null && (startDate.isAfter(endDate) || endDate.isBefore(startDate) || endDate.isAfter(LocalDate.now()) || startDate.equals(endDate)))
            throw new IncorrectDateException();

        List<CSVRecord> casesList = csvFileReader.dictionaryOfListsByStatus.get(status);

        Map<LocalDate, Integer> casesMap = new LinkedHashMap<>();

        while (!startDate.equals(endDate.plusDays(1))) {
            int cases = covidCasesService.sendPartialResultsInChosenDate(casesList, country, startDate);
            casesMap.put(startDate, cases);
            startDate = startDate.plusDays(1);
        }
        return new ChartStats(country, status, casesMap);
    }

    public ChartStats reportGlobalChartDataInAllTheTime(FileType status) {

        List<CSVRecord> casesList = csvFileReader.dictionaryOfListsByStatus.get(status);

        Map<LocalDate, Integer> casesMap = new LinkedHashMap<>();
        LocalDate startDate = LocalDate.parse("2020-01-22");

        while (startDate.isBefore(LocalDate.now().minusDays(1))) {
            int cases = covidCasesService.sendPartialGlobalResultsInChosenDate(casesList, startDate);
            casesMap.put(startDate, cases);
            startDate = startDate.plusDays(15);
        }
        return new ChartStats("Global", status, casesMap);
    }

}
