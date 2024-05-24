package com.deenn.mkoboassessment.service;

import com.deenn.mkoboassessment.entity.Patient;
import com.deenn.mkoboassessment.entity.PatientLog;
import com.deenn.mkoboassessment.exception.DateException;
import com.deenn.mkoboassessment.pojo.constants.AppConstants;
import com.deenn.mkoboassessment.pojo.requests.DeletePatientRequest;
import com.deenn.mkoboassessment.pojo.responses.DeletedPatientsResponse;
import com.deenn.mkoboassessment.repository.PatientLogRepository;
import com.deenn.mkoboassessment.repository.PatientRepository;
import com.deenn.mkoboassessment.usecase.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeletePatientsUseCaseHandlerTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private  PatientLogRepository patientLogRepository;

    @Mock
    private Validator validator;

    @InjectMocks
    private DeletePatientsUseCaseHandler deletePatientsUseCaseHandler;


    @Test
    void givenValidRequest_whenDeletePatients_thenReturnTrue() {

        DeletePatientRequest deletePatientRequest = new DeletePatientRequest();
        deletePatientRequest.setUuid("123-456-789");
        deletePatientRequest.setStartDate("2024-01-01");
        deletePatientRequest.setEndDate("2024-01-31");

        when(validator.validate(any(String.class))).thenReturn(null);
        when(patientRepository.deleteByCreationDateBetween(any(Instant.class), any(Instant.class))).thenReturn(5);
        ArgumentCaptor<DeletedPatientsResponse> deletedPatientsResponseArgumentCaptor = ArgumentCaptor.forClass(DeletedPatientsResponse.class);
        when(patientLogRepository.save(any(PatientLog.class))).thenReturn(null);

        DeletedPatientsResponse response = deletePatientsUseCaseHandler.execute(deletePatientRequest);
        verify(validator, times(1)).validate("123-456-789");
        verify(patientRepository, times(1)).deleteByCreationDateBetween(any(Instant.class), any(Instant.class));
        verify(patientLogRepository, times(1)).save(any(PatientLog.class));
        assertEquals(DeletedPatientsResponse.builder().numberOfDeletedPatients(5).status(AppConstants.DELETE_SUCCESSFUL).build(), response);

    }


    @Test
    void givenInvalidRequest_whenDeletePatients_thenReturnFalse() {
        DeletePatientRequest deletePatientRequest = new DeletePatientRequest();
        deletePatientRequest.setUuid("123-456-789");
        deletePatientRequest.setStartDate("2024-01-31");
        deletePatientRequest.setEndDate("2024-01-01");
        when(validator.validate(any(String.class))).thenReturn(any());


        assertThrows(
                DateException.class,
                () -> deletePatientsUseCaseHandler.execute(deletePatientRequest)
        );
        verify(validator, times(1)).validate("123-456-789");
        verify(patientRepository, never()).deleteByCreationDateBetween(any(Instant.class), any(Instant.class));
        verify(patientLogRepository, times(1)).save(any(PatientLog.class));
    }

}