package com.deenn.mkoboassessment.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
@Getter
@Data
public class PatientException extends RuntimeException {

    private final String message;

    public PatientException(String message) {
        super(message);
        this.message = message;
    }
}
