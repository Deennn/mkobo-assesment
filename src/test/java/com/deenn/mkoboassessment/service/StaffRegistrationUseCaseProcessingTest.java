package com.deenn.mkoboassessment.service;

import com.deenn.mkoboassessment.entity.Staff;
import com.deenn.mkoboassessment.entity.StaffLog;
import com.deenn.mkoboassessment.exception.StaffException;
import com.deenn.mkoboassessment.pojo.constants.AppConstants;
import com.deenn.mkoboassessment.pojo.requests.StaffRegistrationRequest;
import com.deenn.mkoboassessment.pojo.responses.StaffRegistrationResponse;
import com.deenn.mkoboassessment.repository.StaffLogRepository;
import com.deenn.mkoboassessment.repository.StaffRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StaffRegistrationUseCaseProcessingTest {


    @Mock
    private StaffRepository staffRepository;

    @Mock
    private StaffLogRepository staffLogRepository;


    @InjectMocks
    private StaffRegistrationUseCaseProcessing staffRegistrationUseCase;

    @Test
    void given_valid_request_should_return_true_when_registration_is_valid() {

        StaffRegistrationRequest request = new StaffRegistrationRequest("John Doe");
        StaffRegistrationResponse expectedResponse = StaffRegistrationResponse.builder().status(AppConstants.REGISTRATION_SUCCESSFUL).success(true).build();
        Staff staff = Staff.builder()
                .uuid(UUID.randomUUID().toString())
                .name(request.getName())
                .registrationDate(Instant.now())
                .build();

        when(staffRepository.save(any(Staff.class))).thenReturn(staff);
        when(staffLogRepository.save(any(StaffLog.class))).thenReturn(null);


        StaffRegistrationResponse response = staffRegistrationUseCase.execute(request);
        assertNotNull(response);
        assertEquals(expectedResponse, response);
        assertEquals(expectedResponse.getStatus(), response.getStatus());
        assertEquals(expectedResponse.isSuccess(), response.isSuccess());

        verify(staffRepository, times(1)).save(any(Staff.class));
        verify(staffLogRepository, times(1)).save(any(StaffLog.class));
    }

    @Test
    void given_invalid_request_should_return_true_when_registration_is_not_successful() {

        StaffRegistrationRequest request = new StaffRegistrationRequest("John Doe");
        RuntimeException exception =new StaffException("Some error");
        StaffRegistrationResponse expectedResponse = StaffRegistrationResponse.builder().status(AppConstants.REGISTRATION_FAILED).success(false).build();

        when(staffRepository.save(any(Staff.class))).thenThrow(exception);
        when(staffLogRepository.save(any(StaffLog.class))).thenReturn(null);

        StaffRegistrationResponse response = staffRegistrationUseCase.execute(request);

        assertNotNull(response);
        assertEquals(expectedResponse, response);
        assertEquals(expectedResponse.getStatus(), response.getStatus());
        assertEquals(expectedResponse.isSuccess(), response.isSuccess());

        verify(staffRepository, times(1)).save(any(Staff.class));
        verify(staffLogRepository, times(1)).save(any(StaffLog.class));


    }

}