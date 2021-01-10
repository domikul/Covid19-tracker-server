package pl.polsl.covid19TrackerServer.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.polsl.covid19TrackerServer.services.ChartDataService;
import pl.polsl.covid19TrackerServer.services.CovidCasesService;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class ChartStatsControllerTest {

    @Mock
    private ChartDataService chartDataService;

    @InjectMocks
    private ChartStatsController chartStatsController;

    MockMvc mockMvc;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(chartStatsController).build();
    }

    @Test
    void getCountryDataForChart() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/covid19Tracker/chart/Poland/confirmed?from=2020-05-29&to=2020-05-30")
        )
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void getGlobalDataForChart() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/covid19Tracker/chart/total/global/confirmed")
        )
                .andDo(print())
                .andExpect(status().isOk());
    }
}
