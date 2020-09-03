package pl.polsl.Covid19TrackerServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Covid19TrackerServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(Covid19TrackerServerApplication.class, args);
	}

}
