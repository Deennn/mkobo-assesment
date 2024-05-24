package com.deenn.mkoboassessment.service;

import com.deenn.mkoboassessment.entity.Staff;
import com.deenn.mkoboassessment.entity.StaffLog;
import com.deenn.mkoboassessment.exception.StaffException;
import com.deenn.mkoboassessment.pojo.requests.StaffRegistrationRequest;
import com.deenn.mkoboassessment.pojo.responses.StaffRegistrationResponse;
import com.deenn.mkoboassessment.repository.StaffLogRepository;
import com.deenn.mkoboassessment.repository.StaffRepository;
import com.deenn.mkoboassessment.usecase.StaffRegistrationUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

import static com.deenn.mkoboassessment.pojo.constants.AppConstants.*;

@Service
@AllArgsConstructor
@Slf4j
public class StaffRegistrationUseCaseProcessing implements StaffRegistrationUseCase {

    private final StaffLogRepository staffLogRepository;

    private final StaffRepository staffRepository;


    @Override
    public StaffRegistrationResponse execute(StaffRegistrationRequest staffRegistrationRequest) {
        log.info("staffRegistrationRequest: {}", staffRegistrationRequest);
        try {
            staffRepository.save(Staff.builder()
                    .uuid(UUID.randomUUID().toString())
                    .name(staffRegistrationRequest.getName())
                    .registrationDate(Instant.now()).build());
            staffLogRepository.save(StaffLog.builder().status(ACCEPTED).payload(staffRegistrationRequest.toString()).build());
        } catch (Exception e) {
            log.error("error occurred while processing staff registration request {}:", e.getMessage());
            staffLogRepository.save(StaffLog.builder().status(REJECTED).payload(staffRegistrationRequest.toString()).reason(e.getMessage()).build());
            return StaffRegistrationResponse.builder().status(REGISTRATION_FAILED).success(false).build();
        }
        return StaffRegistrationResponse.builder().status(REGISTRATION_SUCCESSFUL).success(true).build();
    }
}
