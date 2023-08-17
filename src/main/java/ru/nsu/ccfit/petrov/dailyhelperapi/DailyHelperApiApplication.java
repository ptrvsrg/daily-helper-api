package ru.nsu.ccfit.petrov.dailyhelperapi;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class DailyHelperApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DailyHelperApiApplication.class, args);
    }
}
