package pl.polsl.covid19TrackerServer;

import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.polsl.covid19TrackerServer.controllers.CovidCasesController;
import pl.polsl.covid19TrackerServer.models.CountryStats;
import pl.polsl.covid19TrackerServer.services.CovidCasesService;
import pl.polsl.covid19TrackerServer.services.CsvFileReader;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class Covid19TrackerServerApplicationTests {

	@Autowired
	private CsvFileReader csvFileReader;

	@Mock
	private CovidCasesService covidCasesService;

	@Mock
	private CountryStats countryStats;

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
	void checkPartialResultInChosenDate() {
		when(covidCasesService.sendPartialResultsInChosenDate(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(1);
		assertEquals(1, covidCasesService.sendPartialResultsInChosenDate(csvFileReader.confirmedList, "Poland", LocalDate.now()));
	}

	@Test
	void checkPartialGlobalResultsInChosenDate() {
		when(covidCasesService.sendPartialGlobalResultsInChosenDate(Mockito.any(), Mockito.any())).thenReturn(1);
		assertEquals(1, covidCasesService.sendPartialGlobalResultsInChosenDate(csvFileReader.confirmedList, LocalDate.now()));
	}

	@Test
	void checkCasesByCountryInTimeRange() throws ParseException {
		when(covidCasesService.showCasesByCountryInTimeRange(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(countryStats);
		assertEquals(countryStats, covidCasesService.showCasesByCountryInTimeRange("Poland", LocalDate.now().minusDays(1), LocalDate.now()));
	}

	@Test
	void checkGlobalCasesInTimeRange() throws ParseException {
		when(covidCasesService.showGlobalCasesInTimeRange(Mockito.any(), Mockito.any())).thenReturn(countryStats);
		assertEquals(countryStats, covidCasesService.showGlobalCasesInTimeRange(LocalDate.now().minusDays(1), LocalDate.now()));
	}

}
