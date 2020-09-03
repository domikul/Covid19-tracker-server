package pl.polsl.Covid19TrackerServer.services;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import pl.polsl.Covid19TrackerServer.models.LocationStats;

import java.io.IOException;
import java.io.StringReader;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConfirmedCasesService {

    private final CsvFileReader csvFileReader;
    private List<LocationStats> allStats = new ArrayList<>();

    public ConfirmedCasesService(CsvFileReader csvFileReader) {
        this.csvFileReader = csvFileReader;
    }

    public List<LocationStats> getLatestConfirmedCases() throws IOException, InterruptedException {

        HttpResponse<String> httpResponse = csvFileReader.fetchConfirmedData();

        List<LocationStats> newStats = new ArrayList<>();
        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        for (CSVRecord record : records) {
            LocationStats locationStats = new LocationStats();
            locationStats.setState(record.get("Province/State"));
            locationStats.setCountry(record.get("Country/Region"));
            locationStats.setLatestTotalCases(Integer.parseInt(record.get(record.size()-1)));
            newStats.add(locationStats);
        }

        this.allStats = newStats;
        return allStats;
    }


    public LocationStats getLatestConfirmedCasesByCountry(String country) throws IOException, InterruptedException {

        LocationStats casesByState = new LocationStats();
        List<LocationStats> confirmedList = getLatestConfirmedCases();
        for (LocationStats record : confirmedList) {
            if(record.getCountry().equals(country)) {
               casesByState = record;
            }
        }
        return casesByState;
    }


}
