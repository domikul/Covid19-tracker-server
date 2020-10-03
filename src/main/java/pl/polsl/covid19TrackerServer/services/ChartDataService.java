package pl.polsl.covid19TrackerServer.services;

import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import pl.polsl.covid19TrackerServer.models.ChartStats;
import pl.polsl.covid19TrackerServer.models.enumerations.FileType;

import java.time.LocalDate;
import java.util.HashMap;
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

    public ChartStats reportChartDataForCountryInTimeRange(String country, FileType status, LocalDate startDate, LocalDate endDate){

        List<CSVRecord> casesList = csvFileReader.dictionaryOfListsByStatus.get(status);

        Map<LocalDate, Integer> casesMap = new HashMap<>();

        while(!startDate.equals(endDate.plusDays(1))){
            int cases = covidCasesService.sendPartialResultsInChosenDate(casesList, country, startDate);
            casesMap.put(startDate, cases);
            startDate = startDate.plusDays(1);
        }
        return new ChartStats(country,status,casesMap);
    }

}
