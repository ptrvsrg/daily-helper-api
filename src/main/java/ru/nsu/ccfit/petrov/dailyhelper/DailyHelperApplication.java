package ru.nsu.ccfit.petrov.dailyhelper;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.RequestBody;
import ru.nsu.ccfit.petrov.dailyhelper.services.EmailService;

@SpringBootApplication
@RequiredArgsConstructor
public class DailyHelperApplication {

    public static void main(String[] args) {
        SpringApplication.run(DailyHelperApplication.class, args);
    }
}
