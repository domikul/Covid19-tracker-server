package pl.polsl.Covid19TrackerServer.services;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.polsl.Covid19TrackerServer.models.LocationStats;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class Covid19DataService {

    private static String CONFIRMED_CASES_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    private List<LocationStats> allStats = new ArrayList<>();

    @PostConstruct
 //   @Scheduled(cron = "* * 1 * * *")
    public HttpResponse<String> fetchVirusData() throws IOException, InterruptedException {

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(CONFIRMED_CASES_URL))
                .build();

        return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

    }

    public List<LocationStats> getLatestConfirmedCases() throws IOException, InterruptedException {

        HttpResponse<String> httpResponse = fetchVirusData();

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
