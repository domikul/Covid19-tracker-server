package pl.polsl.covid19TrackerServer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import pl.polsl.covid19TrackerServer.services.TestConfig;

@SpringBootTest
@ContextConfiguration(classes = {TestConfig.class})
class Covid19TrackerServerApplicationTest {

    @Test
    void contextLoads() {
    }

}
