package ru.nsu.ccfit.petrov.dailyhelper;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class DailyHelperApplication {

    public static void main(String[] args) {
        SpringApplication.run(DailyHelperApplication.class, args);
    }
}
