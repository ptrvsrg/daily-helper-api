package ru.nsu.ccfit.petrov.dailyhelperapi.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class UserAlreadyExistException
    extends RuntimeException {

    public UserAlreadyExistException(String message) {
        super(message);
    }
}
