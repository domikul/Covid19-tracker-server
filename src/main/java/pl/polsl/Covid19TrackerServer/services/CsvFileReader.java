package pl.polsl.Covid19TrackerServer.services;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import pl.polsl.Covid19TrackerServer.models.enumerations.FileType;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvFileReader {

    public List<CSVRecord> fetchData(FileType typeOfFile) throws IOException, InterruptedException {

        List<CSVRecord> recordsList = new ArrayList<>();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(typeOfFile.returnURL(typeOfFile)))
                .build();

        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> CsvRecords = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);

        for (CSVRecord record : CsvRecords) {
            recordsList.add(record);
        }
        return recordsList;

    }
}
