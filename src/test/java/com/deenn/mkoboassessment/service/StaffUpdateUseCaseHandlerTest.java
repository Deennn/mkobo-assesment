package com.deenn.mkoboassessment.service;

import com.deenn.mkoboassessment.entity.Staff;
import com.deenn.mkoboassessment.entity.StaffLog;
import com.deenn.mkoboassessment.pojo.constants.AppConstants;
import com.deenn.mkoboassessment.pojo.requests.StaffUpdateRequest;
import com.deenn.mkoboassessment.pojo.responses.StaffUpdateResponse;
import com.deenn.mkoboassessment.repository.StaffLogRepository;
import com.deenn.mkoboassessment.repository.StaffRepository;
import com.deenn.mkoboassessment.usecase.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.deenn.mkoboassessment.pojo.constants.AppConstants.UPDATE_SUCCESSFUL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class StaffUpdateUseCaseHandlerTest {

    @Mock
    private StaffLogRepository staffLogRepository;

    @Mock
    private StaffRepository staffRepository;

    @Mock
    private Validator validator;

    @InjectMocks
    private StaffUpdateUseCaseHandler staffUpdateUseCaseHandler;

    @Test
    void givenValidStaff_whenUpdateStaff_thenReturnTrue() {
            StaffUpdateRequest request = new StaffUpdateRequest();
            request.setUuid("staff_uuid");
            request.setName("Updated Name");
            Staff staff = new Staff();
            staff.setUuid("staff_uuid");
            staff.setName("Original Name");
            when(validator.validate("staff_uuid")).thenReturn(staff);
            when(staffRepository.save(any(Staff.class))).thenReturn(staff);
            StaffUpdateResponse response = staffUpdateUseCaseHandler.execute(request);
            assertTrue(response.isSuccess());
            assertEquals(UPDATE_SUCCESSFUL, response.getStatus());
            verify(staffRepository, times(1)).save(staff);
            verify(staffLogRepository, times(1)).save(any(StaffLog.class));
    }


    @Test
    void givenInvalidStaff_whenUpdateStaff_thenReturnFalse() {
        StaffUpdateRequest request = new StaffUpdateRequest();
        request.setUuid("staff_uuid");
        request.setName("Updated Name");

        when(validator.validate("staff_uuid")).thenReturn(any(Staff.class));
        StaffUpdateResponse response = staffUpdateUseCaseHandler.execute(request);

        assertFalse(response.isSuccess());
        assertEquals(AppConstants.UPDATE_FAILED, response.getStatus());

    }

}