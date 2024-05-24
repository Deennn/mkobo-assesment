package com.deenn.mkoboassessment.service;

import com.deenn.mkoboassessment.entity.Patient;
import com.deenn.mkoboassessment.pojo.requests.GetPatientRequest;
import com.deenn.mkoboassessment.pojo.responses.PaginatedResponse;
import com.deenn.mkoboassessment.repository.PatientRepository;
import com.deenn.mkoboassessment.usecase.FetchPatientUseCase;
import com.deenn.mkoboassessment.usecase.Validator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class GetAllPatientsProfileImplementation implements FetchPatientUseCase {

    private final PatientRepository patientRepository;

    private final Validator validator;

    @Override
    public PaginatedResponse execute(GetPatientRequest request) {
        log.info("GetAllPatientsProfileImplementation execute");
        validator.validate(request.getUuid());

        Page<Patient> patients = patientRepository.findAllPatientsWithAgeUpToTwo(PageRequest.of(request.getPageNumber(), request.getPageSize()));
        return PaginatedResponse.builder().data(patients.getContent()).page(request.getPageNumber())
                .pages(patients.getTotalPages()).size(patients.getNumberOfElements())
                .hasNext(patients.hasNext())
                .isFirst(patients.isFirst())
                .isLast(patients.isLast())
                .hasPrevious(patients.hasPrevious())
                .build();
    }
}
