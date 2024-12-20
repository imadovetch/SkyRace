package com.PigeonSkyRace.Auth.Exception.shared;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ErrorMessageDTO errorMessageDTO = ErrorMessageDTO.builder()
                .message("Access denied: You do not have permission to access this resource.")
                .timestamp(new Date())
                .code(HttpStatus.FORBIDDEN.value())
                .build();

        // Send the 403 status with the custom error message
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        response.getWriter().write(errorMessageDTO.toString()); // Convert to JSON
    }
}
