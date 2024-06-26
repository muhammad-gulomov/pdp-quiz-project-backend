package uz.muhammadtrying.pdpquizprojectbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class PdpQuizProjectBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(PdpQuizProjectBackendApplication.class, args);
    }
}
