package pl.polsl.covid19TrackerServer.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.polsl.covid19TrackerServer.services.CovidCasesService;
import pl.polsl.covid19TrackerServer.services.TestConfig;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
class CovidCasesControllerTest {

    @Mock
    private CovidCasesService covidCasesService;

    @InjectMocks
    private CovidCasesController covidCasesController;

    MockMvc mockMvc;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(covidCasesController).build();
    }

    @Test
    void getWorldLatestData() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/covid19Tracker/total/all")
        )
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void getGlobalLatestData() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/covid19Tracker/total/global")
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getLatestDataByCountry() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/covid19Tracker/total/Poland")
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getAllCountriesDataInTimeRange_correctDate() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/covid19Tracker/partial/all?from=2020-05-29&to=2020-05-30")
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getAllCountriesDataInTimeRange_wrongDate() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/covid19Tracker/partial/all?from=2020-0-29&to=2020-05-30")
        )
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    void getGlobalLatestData_wrongURL() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/covid19Tracker/tot/glob")
        )
                .andDo(print())
                .andExpect(status().is(404));
    }
}
