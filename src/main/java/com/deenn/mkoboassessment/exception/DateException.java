package com.deenn.mkoboassessment.exception;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
@Getter
@Data
public class DateException extends RuntimeException {

    private final String message;

    public DateException(String message) {
        super(message);
        this.message = message;
    }
}
