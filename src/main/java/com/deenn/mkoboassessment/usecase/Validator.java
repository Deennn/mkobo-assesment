package com.deenn.mkoboassessment.usecase;

import com.deenn.mkoboassessment.entity.Staff;

public interface Validator {

    Staff validate(String uuid);
}
