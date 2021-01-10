package pl.polsl.covid19TrackerServer.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.polsl.covid19TrackerServer.models.CountryStats;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
class CovidCasesServiceTest {

    @Autowired
    private CsvFileReader csvFileReader;

    @Autowired
    private CovidCasesService covidCasesService;

    MockMvc mockMvc;

    @BeforeEach
    public void init()
    {
        mockMvc = standaloneSetup(covidCasesService).build();
        mockMvc = standaloneSetup(csvFileReader).build();
    }

    @Test
    void checkHeadersList() {
        List<String> headersList = csvFileReader.getHeadersList();
        assertEquals("Province/State", headersList.get(0));
        assertEquals("Country/Region", headersList.get(1));
        assertEquals("Lat", headersList.get(2));
        assertEquals("Long", headersList.get(3));
        assertEquals("1/22/20", headersList.get(4));
    }

    @Test
    void showWorldCasesInTimeRange() throws ParseException {
        String country = "Poland";
        String startDate = "2020-08-15";
        String endDate = "2020-08-25";
        CountryStats latestCases = covidCasesService.showCasesByCountryInTimeRange(country, LocalDate.parse(startDate), LocalDate.parse(endDate));

        assertEquals(6983, latestCases.getConfirmedCases());
        assertEquals(108, latestCases.getDeathsCases());
        assertEquals(3931, latestCases.getRecoveredCases());
        assertEquals(country, latestCases.getCountry());
    }

}
