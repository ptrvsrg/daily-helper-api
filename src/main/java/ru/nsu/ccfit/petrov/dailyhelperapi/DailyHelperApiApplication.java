package ru.nsu.ccfit.petrov.dailyhelperapi;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@RequiredArgsConstructor
public class DailyHelperApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DailyHelperApiApplication.class, args);
    }
}
