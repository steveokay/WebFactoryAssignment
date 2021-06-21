package com.webfactory.assignment.exceptionHandlers;

import com.webfactory.assignment.web.models.RatesResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    )
    {
        Map<String,String> errorList = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error->errorList.put(error.getField(),error.getDefaultMessage()));

        RatesResponse responseString = new RatesResponse();
        responseString.setStatusCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
        responseString.setDescription(errorList.toString());

        // response to send back to client
        return new ResponseEntity<>(responseString, HttpStatus.OK);
    }

}
