package pl.polsl.covid19TrackerServer.services;

import org.apache.commons.csv.CSVRecord;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import pl.polsl.covid19TrackerServer.exceptions.IncorrectDateException;
import pl.polsl.covid19TrackerServer.exceptions.NoAvailableDataException;
import pl.polsl.covid19TrackerServer.models.CountryStats;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class CovidCasesService {

    private final CsvFileReader csvFileReader;

    public CovidCasesService(CsvFileReader csvFileReader) {
        this.csvFileReader = csvFileReader;
    }

    public List<CountryStats> showWorldCasesInTimeRange(@Nullable LocalDate startDate, LocalDate endDate) {

        List<CountryStats> resultList = new ArrayList<>();

        csvFileReader.confirmedList.forEach(it -> {
            resultList.add(showCasesByCountryInTimeRange(it.get("Country/Region"), startDate, endDate));
        });

        return resultList.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    public CountryStats showGlobalCasesInTimeRange(@Nullable LocalDate startDate, LocalDate endDate) {

        List<CountryStats> worldList = showWorldCasesInTimeRange(startDate, endDate);
        CountryStats globalResult = new CountryStats();
        globalResult.setCountry("Global");

        worldList.forEach(it -> {
            globalResult.setConfirmedCases(globalResult.getConfirmedCases()+it.getConfirmedCases());
            globalResult.setDeathsCases(globalResult.getDeathsCases()+it.getDeathsCases());
            globalResult.setRecoveredCases(globalResult.getRecoveredCases()+it.getRecoveredCases());
        });

        return globalResult;
    }

    public CountryStats showCasesByCountryInTimeRange(String country, @Nullable LocalDate startDate, LocalDate endDate) {
        int confirmed = calculateSubtractionOfDailyCases(csvFileReader.confirmedList, country, startDate, endDate);
        int deaths = calculateSubtractionOfDailyCases(csvFileReader.deathsList, country, startDate, endDate);
        int recovered = calculateSubtractionOfDailyCases(csvFileReader.recoveredList, country, startDate, endDate);

        return new CountryStats(country, confirmed, recovered, deaths);
    }

    private int calculateSubtractionOfDailyCases(final List<CSVRecord> records, final String country, @Nullable LocalDate startDate, LocalDate endDate) {

        if (startDate != null && (startDate.isAfter(endDate) || endDate.isBefore(startDate) || endDate.isAfter(LocalDate.now()) || startDate.equals(endDate)))
            throw new IncorrectDateException();

        int endDateResult = sendPartialResultsInChosenDate(records, country, endDate);
        int startDateResult = startDate != null ? sendPartialResultsInChosenDate(records, country, startDate) : 0;

        return endDateResult - startDateResult;
    }


    public int sendPartialResultsInChosenDate(List<CSVRecord> casesList, String country, LocalDate date) {

        if (date.isBefore(LocalDate.parse("2020-01-22")) || date.equals(LocalDate.now()))
            throw new NoAvailableDataException(date);

        LocalDate checkedDate = check(casesList, date);

        return casesList.stream()
                .filter(el -> el.get("Country/Region").equals(country))
                .map(el -> el.get(checkedDate.getMonthValue() + "/" + checkedDate.getDayOfMonth() + "/" + checkedDate.getYear() % 1000))
                .mapToInt(Integer::valueOf)
                .sum();
    }

    public int sendPartialGlobalResultsInChosenDate(List<CSVRecord> casesList, LocalDate date) {

        List<String> listWithCountryNames = new ArrayList<>();
        casesList.forEach(it ->
                listWithCountryNames.add(it.get("Country/Region")));

       return listWithCountryNames.stream()
               .distinct()
               .mapToInt(it -> sendPartialResultsInChosenDate(casesList, it, date))
               .sum();

    }

    private LocalDate check(List<CSVRecord> casesList, LocalDate date) {

        if(date.equals(LocalDate.now().minusDays(1))){
            List<List<String>> headers = casesList.stream().map(it -> it.getParser().getHeaderNames()).collect(Collectors.toList());
            boolean c = false;
            for(int i=0; i<headers.get(0).size() - 1 ; i++ ){
                if (headers.get(0).get(i).equals(date.getMonthValue() + "/" + date.getDayOfMonth() + "/" + date.getYear() % 1000)) {
                    c = true;
                    break;
                }
            }
            if(!c)
                return date.minusDays(1);
        }
        return date;
    }

}
