package pl.polsl.Covid19TrackerServer.services;

import org.apache.commons.csv.CSVRecord;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.polsl.Covid19TrackerServer.models.CountryStats;
import pl.polsl.Covid19TrackerServer.models.enumerations.FileType;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CovidCasesService {

    private final CsvFileReader csvFileReader;
    private List<CSVRecord> confirmedList;
    private List<CSVRecord> deathsList;
    private List<CSVRecord> recoveredList;

    public CovidCasesService(CsvFileReader csvFileReader) {
        this.csvFileReader = csvFileReader;
    }

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    private void initLists() throws IOException, InterruptedException {
        this.confirmedList = csvFileReader.fetchData(FileType.CONFIRMED);
        this.deathsList = csvFileReader.fetchData(FileType.DEATHS);
        this.recoveredList = csvFileReader.fetchData(FileType.RECOVERED);
    }


    public List<CountryStats> allCountriesTimeRangeCases(@Nullable LocalDate startDate, LocalDate endDate) {

        List<CountryStats> resultList = new ArrayList<>();

        confirmedList.forEach(it -> {
            resultList.add(singleCountryCasesInTimeRange(it.get("Country/Region"), startDate, endDate));
        });

        return resultList.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    public CountryStats singleCountryCasesInTimeRange(String country, @Nullable LocalDate startDate, LocalDate endDate) {
        int partialConfirmed = calculateSubtraction(confirmedList, country, startDate, endDate);
        int partialDeaths = calculateSubtraction(deathsList, country, startDate, endDate);
        int partialRecovered = calculateSubtraction(recoveredList, country, startDate, endDate);

        return new CountryStats(country, partialConfirmed, partialRecovered, partialDeaths);
    }

    private int calculateSubtraction(final List<CSVRecord> records, final String country, @Nullable LocalDate startDate, LocalDate endDate) {
        int partialEnd = sendPartialResultsInChosenDate(records, country, endDate);
        int partialStart = startDate != null ? sendPartialResultsInChosenDate(records, country, startDate) : 0;

        return partialEnd - partialStart;
    }

    private int sendPartialResultsInChosenDate(List<CSVRecord> casesList, String country, LocalDate date) {
        return casesList.stream()
                .filter(el -> el.get("Country/Region").equals(country))
                .map(el -> el.get(date.getMonthValue() + "/" + date.getDayOfMonth() + "/" + date.getYear() % 1000))
                .mapToInt(Integer::valueOf)
                .sum();
    }
}