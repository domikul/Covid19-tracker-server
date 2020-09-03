package pl.polsl.Covid19TrackerServer.services;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpResponse;

@Service
public interface CsvFileReader {

    HttpResponse<String> fetchConfirmedData() throws IOException, InterruptedException;
}
