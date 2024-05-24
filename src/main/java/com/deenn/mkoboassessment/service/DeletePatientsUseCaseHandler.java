package com.deenn.mkoboassessment.service;

import com.deenn.mkoboassessment.entity.PatientLog;
import com.deenn.mkoboassessment.exception.DateException;
import com.deenn.mkoboassessment.pojo.requests.DeletePatientRequest;
import com.deenn.mkoboassessment.pojo.responses.DeletedPatientsResponse;
import com.deenn.mkoboassessment.repository.PatientLogRepository;
import com.deenn.mkoboassessment.repository.PatientRepository;
import com.deenn.mkoboassessment.usecase.DeletePatientsUseCase;
import com.deenn.mkoboassessment.usecase.Validator;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import static com.deenn.mkoboassessment.pojo.constants.AppConstants.*;

@Service
@AllArgsConstructor
@Slf4j
public class DeletePatientsUseCaseHandler implements DeletePatientsUseCase {

    private final PatientRepository patientRepository;

    private final PatientLogRepository patientLogRepository;

    private final Validator validator;

    @Override
    public DeletedPatientsResponse execute(DeletePatientRequest request) {

        validator.validate(request.getUuid());;
        return DeletedPatientsResponse.builder().
                numberOfDeletedPatients(validateDateAndDelete(request))
                .status(DELETE_SUCCESSFUL)
                .build();
    }



    @SneakyThrows
    private int validateDateAndDelete(DeletePatientRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate localDate = LocalDate.parse(request.getStartDate(), formatter);
            LocalDate localDate1 = LocalDate.parse(request.getEndDate(), formatter);
            Instant instant = localDate.atStartOfDay().toInstant(ZoneOffset.UTC);
            Instant instant1 = localDate1.atStartOfDay().toInstant(ZoneOffset.UTC);
            if (instant1.isBefore(instant))
                throw new DateException("End date is earlier than start date.");
            int i = patientRepository.deleteByCreationDateBetween(instant, instant1);
            log.info("Deleted {} patients.", i);
            patientLogRepository.save(PatientLog.builder().payload(request.toString()).reason("Deleted patients " + i).status(DELETE_SUCCESSFUL).build());
            return i;
        } catch (Exception e) {
            log.error(e.getMessage());
            patientLogRepository.save(PatientLog.builder().payload(request.toString()).status(DELETE_FAILED).reason(e.getMessage()).build());
            throw new DateException(e.getMessage());
        }
    }
}
