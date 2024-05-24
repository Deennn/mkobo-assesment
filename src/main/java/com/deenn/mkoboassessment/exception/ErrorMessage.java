package com.deenn.mkoboassessment.exception;

import lombok.Getter;

@Getter
public class ErrorMessage {


    private final String message;
    private final boolean isError;

    public ErrorMessage(String message, boolean isError) {
        this.message = message;
        this.isError = isError;
    }
   }