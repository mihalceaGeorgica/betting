package ro.fortech.betting.startup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "ro.fortech")
public class BettingApplication {

    public static void main(final String[] args) {	
        SpringApplication.run(BettingApplication.class, args);
    }
}
