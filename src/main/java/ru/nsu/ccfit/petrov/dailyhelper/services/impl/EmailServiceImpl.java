package ru.nsu.ccfit.petrov.dailyhelper.services.impl;

import jakarta.mail.internet.MimeMessage;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import ru.nsu.ccfit.petrov.dailyhelper.services.EmailService;
import ru.nsu.ccfit.petrov.dailyhelper.utils.EnvUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl
    implements EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final EnvUtils envUtils;

    @Value("${spring.mail.username}")
    private String from;

    @SneakyThrows
    @Async
    public void sendMessage(String to, String subject, String text) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true);

        mailSender.send(mimeMessage);
        log.info("Letter \"{}\" was sent to {}", subject, to);
    }

    @Override
    public void sendWelcomeAndActivateAccount(String to, String token) {
        Context context = new Context();
        context.setVariables(Map.of("username", to,
                                    "host", envUtils.getHostUrl(),
                                    "token", token));

        String htmlText = templateEngine.process("welcome-activation-account", context);

        sendMessage(to, "Welcome and Account activation", htmlText);
    }

    @Override
    public void sendConfirmPasswordChange(String to, String token) {
        Context context = new Context();
        context.setVariables(Map.of("username", to,
                                    "host", envUtils.getHostUrl(),
                                    "token", token));

        String htmlText = templateEngine.process("confirm-password-change", context);

        sendMessage(to, "Confirm password change", htmlText);
    }
}
