package pl.polsl.covid19TrackerServer.services;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.polsl.covid19TrackerServer.models.enumerations.FileType;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

@Service
public class CsvFileReader {

    public List<CSVRecord> confirmedList;
    public List<CSVRecord> deathsList;
    public List<CSVRecord> recoveredList;

    public final Hashtable<FileType, List<CSVRecord>> dictionaryOfListsByStatus = new Hashtable<>();

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    private void updateListsOfCases() throws IOException, InterruptedException {

        this.confirmedList = fetchData(FileType.CONFIRMED);
        this.deathsList = fetchData(FileType.DEATHS);
        this.recoveredList = fetchData(FileType.RECOVERED);
        fillDictionary();
    }

    private void fillDictionary() {
        dictionaryOfListsByStatus.clear();
        dictionaryOfListsByStatus.put(FileType.CONFIRMED, confirmedList);
        dictionaryOfListsByStatus.put(FileType.DEATHS, deathsList);
        dictionaryOfListsByStatus.put(FileType.RECOVERED, recoveredList);
    }

    public List<CSVRecord> fetchData(FileType typeOfFile) throws IOException, InterruptedException {

        List<CSVRecord> recordsList = new ArrayList<>();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(typeOfFile.returnURL()))
                .build();

        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> CsvRecords = CSVFormat.DEFAULT.withFirstRecordAsHeader()
                .parse(csvBodyReader);

        CsvRecords.forEach(recordsList::add);
        return recordsList;

    }
}
