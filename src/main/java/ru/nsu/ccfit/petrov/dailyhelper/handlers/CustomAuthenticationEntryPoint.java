package ru.nsu.ccfit.petrov.dailyhelper.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import ru.nsu.ccfit.petrov.dailyhelper.models.dtos.ErrorResponse;

public class CustomAuthenticationEntryPoint
    implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException)
        throws IOException {
        ErrorResponse errorResponse = new ErrorResponse("Failed to authenticate",
                                                        authException.getMessage());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
    }
}
