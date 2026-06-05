package fr.diginamic.echolink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main entry point of the EchoLink application.
 * <p>
 * Bootstraps the Spring Boot context and enables scheduled tasks.
 */
@SpringBootApplication
@EnableScheduling
public class EcholinkApplication {

    /**
     * Starts the EchoLink application.
     *
     * @param args application startup arguments
     */
    static void main(String[] args) {
        SpringApplication.run(EcholinkApplication.class, args);
    }

}
