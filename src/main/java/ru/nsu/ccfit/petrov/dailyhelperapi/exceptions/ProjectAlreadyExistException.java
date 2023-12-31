package ru.nsu.ccfit.petrov.dailyhelperapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class ProjectAlreadyExistException
    extends RuntimeException {

    public ProjectAlreadyExistException(String message) {
        super(message);
    }
}
