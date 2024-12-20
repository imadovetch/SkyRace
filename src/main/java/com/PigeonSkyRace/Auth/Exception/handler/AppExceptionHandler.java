package com.PigeonSkyRace.Auth.Exception.handler;




import com.PigeonSkyRace.Auth.Exception.EntityAlreadyExistsException;
import com.PigeonSkyRace.Auth.Exception.EntityNotFoundException;
import com.PigeonSkyRace.Auth.Exception.UsernameAlreadyExistsException;
import com.PigeonSkyRace.Auth.Exception.ValidationException;
import com.PigeonSkyRace.Auth.Exception.shared.ErrorMessageDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<Object> entityNotFoundException(EntityNotFoundException ex) {
        ErrorMessageDTO errorMessageDTO = ErrorMessageDTO.builder()
                .message(ex.getMessage())
                .timestamp(new Date())
                .code(404)
                .build();
        return new ResponseEntity<>(errorMessageDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> illegalArgumentException(IllegalArgumentException ex) {
        ErrorMessageDTO errorMessageDTO = ErrorMessageDTO.builder()
                .message(ex.getMessage())
                .timestamp(new Date())
                .code(409)  // Conflict status code
                .build();
        return new ResponseEntity<>(errorMessageDTO, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = {UsernameAlreadyExistsException.class})
    public ResponseEntity<Object> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex) {
        ErrorMessageDTO errorMessageDTO = ErrorMessageDTO.builder()
                .message(ex.getMessage())
                .timestamp(new Date())
                .code(409)  // Conflict status code
                .build();
        return new ResponseEntity<>(errorMessageDTO, HttpStatus.CONFLICT);
    }

    // Handle missing request body
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleMissingBody(HttpMessageNotReadableException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", "Required request body is missing.");
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> HandleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        System.out.println("hello");
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = {EntityAlreadyExistsException.class})
    public ResponseEntity<Object> entityAlreadyExistsException(EntityAlreadyExistsException ex) {
        ErrorMessageDTO errorMessageDTO = ErrorMessageDTO.builder()
                .message(ex.getMessage())
                .timestamp(new Date())
                .code(409)
                .build();
        return new ResponseEntity<>(errorMessageDTO, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(value = {ValidationException.class})
    public ResponseEntity<Object> handleValidationException(ValidationException ex) {
        System.out.println("ee");
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("field", ex.getField());
        errorDetails.put("error", ex.getMessage());

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex) {
        // Custom error message for bad credentials
        System.out.println("dd");
        ErrorMessageDTO errorMessageDTO = ErrorMessageDTO.builder()
                .message("Authentication failed: Invalid email or password.")
                .timestamp(new Date())
                .code(HttpStatus.UNAUTHORIZED.value()) // Set status to 401 (Unauthorized)
                .build();
        return new ResponseEntity<>(errorMessageDTO, HttpStatus.UNAUTHORIZED);
    }



}