package ru.nsu.ccfit.petrov.dailyhelper.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class VerificationTokenNotFoundException
    extends RuntimeException {

    public VerificationTokenNotFoundException(String message) {
        super(message);
    }
}
