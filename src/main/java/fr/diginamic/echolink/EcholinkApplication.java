package fr.diginamic.echolink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EcholinkApplication {

    static void main(String[] args) {
        SpringApplication.run(EcholinkApplication.class, args);
    }

}
