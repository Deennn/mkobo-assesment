package com.deenn.mkoboassessment.usecase;

public interface UseCaseFunction<INPUT, OUTPUT> {

    OUTPUT execute(INPUT input);
}
