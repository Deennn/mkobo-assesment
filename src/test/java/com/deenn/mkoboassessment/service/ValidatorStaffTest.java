package com.deenn.mkoboassessment.service;

import com.deenn.mkoboassessment.entity.Staff;
import com.deenn.mkoboassessment.exception.StaffException;
import com.deenn.mkoboassessment.repository.StaffRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ValidatorStaffTest {


    @Mock
    private StaffRepository staffRepository;

    @InjectMocks
    private ValidatorStaff validatorStaff;


    @Test
    void givenValidUuid_whenValid_thenReturnStaff() {

        String uuid = "uuid";
        Staff expectedStaff = new Staff();
        when(staffRepository.findByUuid(uuid)).thenReturn(Optional.of(expectedStaff));

        Staff actualStaff = validatorStaff.validate(uuid);

        assertEquals(expectedStaff, actualStaff);
        verify(staffRepository, times(1)).findByUuid(uuid);

    }

    @Test
    void givenInValidUuid_thenThrowStaffException() {

        String uuid = "uuid";
        when(staffRepository.findByUuid(uuid)).thenReturn(Optional.empty());

        StaffException exception = assertThrows(StaffException.class, () -> validatorStaff.validate(uuid));
        assertEquals("Staff not found", exception.getMessage());
        verify(staffRepository, times(1)).findByUuid(uuid);

    }

}