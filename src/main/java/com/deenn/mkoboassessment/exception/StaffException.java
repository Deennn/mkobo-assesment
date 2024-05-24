package com.deenn.mkoboassessment.exception;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Getter
@Data
public class StaffException extends RuntimeException {

    private final String message;

    public StaffException(String message) {
        super(message);
        this.message = message;
    }
}
