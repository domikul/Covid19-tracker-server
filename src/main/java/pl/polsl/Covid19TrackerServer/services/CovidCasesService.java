package pl.polsl.Covid19TrackerServer.services;

import org.apache.commons.csv.CSVRecord;
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
    List<CSVRecord> confirmedList = new ArrayList<>();
    List<CSVRecord> deathsList = new ArrayList<>();
    List<CSVRecord> recoveredList = new ArrayList<>();

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


    public List<CountryStats> allCountriesLatestCases() {

        List<CountryStats> resultList = new ArrayList<>();

        confirmedList.forEach(it -> {
            CountryStats countryStats = singleCountrySingleDate(it.get("Country/Region"), LocalDate.now().minusDays(1));
            resultList.add(countryStats);
        });

        return resultList.stream().distinct().collect(Collectors.toList());

    }

    public List<CountryStats> allCountriesTimeRangeCases(LocalDate startDate, LocalDate endDate) {

        List<CountryStats> resultList = new ArrayList<>();

        confirmedList.forEach(it -> {
            CountryStats countryStats = singleCountryCasesInTimeRange(it.get("Country/Region"), startDate, endDate);
            resultList.add(countryStats);
        });

        return resultList.stream().distinct().collect(Collectors.toList());

    }


    public CountryStats singleCountrySingleDate(String country, LocalDate date) {

        CountryStats partialConfirmedData = sendPartialResultsInChoosenDate(confirmedList, country, date, FileType.CONFIRMED);
        int partialConfirmed = partialConfirmedData.getConfirmedCases();
        int partialDeaths = sendPartialResultsInChoosenDate(deathsList, country, date, FileType.DEATHS).getDeathsCases();
        int partialRecovered = sendPartialResultsInChoosenDate(recoveredList, country, date , FileType.RECOVERED).getRecoveredCases();
        double latitude = partialConfirmedData.getLatitude();
        double longitude = partialConfirmedData.getLongitude();

        return fillCountryStatsEntity(country, partialConfirmed, partialDeaths, partialRecovered, latitude, longitude);
    }

    private CountryStats singleCountryCasesInTimeRange(String country, LocalDate startDate, LocalDate endDate) {

        CountryStats startResponse = singleCountrySingleDate(country, startDate);
        CountryStats endResponse = singleCountrySingleDate(country, endDate);

        int partialConfirmed = endResponse.getConfirmedCases() - startResponse.getConfirmedCases();
        int partialDeaths = endResponse.getDeathsCases() - startResponse.getDeathsCases();
        int partialRecovered = endResponse.getRecoveredCases() - startResponse.getRecoveredCases();
        double latitude = startResponse.getLatitude();
        double longitude = startResponse.getLongitude();

        return fillCountryStatsEntity(country, partialConfirmed, partialDeaths, partialRecovered, latitude, longitude);
    }

    private CountryStats sendPartialResultsInChoosenDate(List<CSVRecord> casesList, String country, LocalDate date, FileType status) {

        int totalSumCases = 0;

        List<CSVRecord> filteredRecords = casesList.stream()
                .filter(el -> el.get("Country/Region").equals(country)).collect(Collectors.toList());

        totalSumCases += filteredRecords.stream()
                .map(el -> el.get(date.getMonthValue() + "/" + date.getDayOfMonth() + "/" + date.getYear() % 1000))
                .mapToInt(Integer::valueOf)
                .sum();

        CSVRecord latest = filteredRecords.get(filteredRecords.size()-1);

        return fillPartialStatsForCountryEntity(latest, totalSumCases, status);
    }

    private CountryStats fillPartialStatsForCountryEntity(CSVRecord record, int totalSumCases, FileType status){

        CountryStats countryStats = new CountryStats();

        countryStats.setLatitude(Double.parseDouble(record.get("Lat")));
        countryStats.setLongitude(Double.parseDouble(record.get("Long")));
        countryStats.setCountry(record.get("Country/Region"));

        if(status.equals(FileType.CONFIRMED))
            countryStats.setConfirmedCases(totalSumCases);
        else if(status.equals(FileType.DEATHS))
            countryStats.setDeathsCases(totalSumCases);
        else if(status.equals(FileType.RECOVERED))
            countryStats.setRecoveredCases(totalSumCases);

        return countryStats;
    }

    private CountryStats fillCountryStatsEntity(String country, int partialConfirmed, int partialDeaths, int partialRecovered, double latitude, double longitude){

        CountryStats countryStats = new CountryStats();
        countryStats.setCountry(country);
        countryStats.setConfirmedCases(partialConfirmed);
        countryStats.setDeathsCases(partialDeaths);
        countryStats.setRecoveredCases(partialRecovered);
        countryStats.setLatitude(latitude);
        countryStats.setLongitude(longitude);

        return countryStats;
    }


}