package pl.polsl.Covid19TrackerServer.services;

import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.polsl.Covid19TrackerServer.models.enumerations.FileType;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CovidCasesService {

    private final CsvFileReader csvFileReader;
    List<CSVRecord> confirmedList = new ArrayList<>();
    List<CSVRecord> deathsList = new ArrayList<>();
    List<CSVRecord> recoveredList = new ArrayList<>();

    public CovidCasesService(CsvFileReader csvFileReader) throws IOException, InterruptedException {
        this.csvFileReader = csvFileReader;
    }

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    private void initLists() throws IOException, InterruptedException {
        this.confirmedList = csvFileReader.fetchData(FileType.CONFIRMED);
        this.deathsList = csvFileReader.fetchData(FileType.DEATHS);
        this.recoveredList = csvFileReader.fetchData(FileType.RECOVERED);
    }




}