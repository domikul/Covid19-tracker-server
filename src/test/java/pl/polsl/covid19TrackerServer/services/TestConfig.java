package pl.polsl.covid19TrackerServer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

    @Autowired
    private CsvFileReader csvFileReader;

    @Autowired
    private CovidCasesService covidCasesService;

    @Bean
    public CovidCasesService covidCasesService() {
        return new CovidCasesService(csvFileReader);
    }

    @Bean
    public ChartDataService chartDataService() {
        return new ChartDataService(csvFileReader, covidCasesService);
    }

    @Bean
    public CsvFileReader csvFileReader() {
        return new CsvFileReader();
    }
}
