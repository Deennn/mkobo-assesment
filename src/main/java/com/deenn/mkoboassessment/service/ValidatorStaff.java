package com.deenn.mkoboassessment.service;

import com.deenn.mkoboassessment.entity.Staff;
import com.deenn.mkoboassessment.exception.StaffException;
import com.deenn.mkoboassessment.repository.StaffRepository;
import com.deenn.mkoboassessment.usecase.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidatorStaff implements Validator {

    private final StaffRepository staffRepository;


    @Override
    public Staff validate(String uuid) {
        return staffRepository.findByUuid(uuid).orElseThrow(() -> new StaffException("Staff not found"));
    }


}
