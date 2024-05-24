package com.deenn.mkoboassessment.service;

import com.deenn.mkoboassessment.entity.Patient;
import com.deenn.mkoboassessment.pojo.requests.GetPatientRequest;
import com.deenn.mkoboassessment.pojo.responses.PaginatedResponse;
import com.deenn.mkoboassessment.repository.PatientRepository;
import com.deenn.mkoboassessment.usecase.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class GetAllPatientsProfileImplementationTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private Validator validator;

    @InjectMocks
    private GetAllPatientsProfileImplementation getAllPatientsProfileImplementation;


    @Test
    void getAllPatientsProfile() {
        GetPatientRequest request = new GetPatientRequest();
        request.setPageNumber(0);
        request.setPageSize(10);
        when(validator.validate(anyString())).thenReturn(null);
        List<Patient> patientList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            patientList.add(new Patient());
        }
        Page<Patient> patientsPage = new PageImpl<>(patientList);
        when(patientRepository.findAllPatientsWithAgeUpToTwo(any(PageRequest.class))).thenReturn(patientsPage);
        PaginatedResponse response = getAllPatientsProfileImplementation.execute(request);
        verify(validator, times(1)).validate(anyString());
        verify(patientRepository, times(1)).findAllPatientsWithAgeUpToTwo(any(PageRequest.class));
        assertEquals(patientList.size(), response.getData().size());
        assertEquals(request.getPageNumber(), response.getPage());
        assertEquals(1, response.getPages());
        assertEquals(patientList.size(), response.getSize());
        assertEquals(patientList.size(), response.getTotal());
        assertTrue(response.isFirst());
        assertTrue(response.isLast());
        assertFalse(response.isHasNext());
        assertFalse(response.isHasPrevious());
    }


}