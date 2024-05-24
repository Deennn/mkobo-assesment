package com.deenn.mkoboassessment.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {



    @ExceptionHandler(StaffException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleStaffException(StaffException ex) {
        log.error("throwing ::::: StaffException()");
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(ex.getMessage(), false));
    }

    @ExceptionHandler(PatientException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handlePatientException(PatientException ex) {
        log.error("throwing ::::: PatientException()");
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(ex.getMessage(), false));
    }

    @ExceptionHandler(DateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleDateException(DateException ex) {
        log.error("throwing ::::: DateException()");
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(ex.getMessage(), false));
    }

    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> errorList = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        log.info("errors : {}",errorList);
        String message = ex.getBindingResult().getFieldError() == null ? ex.getAllErrors().get(0).getDefaultMessage() :
                ex.getBindingResult().getFieldError().getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(message, false));

    }

    @ExceptionHandler(value = { HttpMessageNotReadableException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage>  httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException ex) {
        log.info("throwing HttpMessageNotReadableException at : {}", ex.getStackTrace()[0].toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(Objects.requireNonNullElse(ex.getLocalizedMessage()," ")
                .split(";")[0], false));
    }

    @ExceptionHandler(value = { NotFoundException.class })
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessage>   misMatchErrorHandler(NotFoundException ex) {
        log.info("throwing NotFoundException at : {}", ex.getStackTrace()[0].toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(ex.getMessage(), false));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage>  handleMissingServletRequestParamException(MissingServletRequestParameterException ex) {
        log.error("throwing this::::::::::::: MissingServletRequestParameterException at {}",ex.getStackTrace()[0]);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(ex.getMessage(), false));
    }

}
