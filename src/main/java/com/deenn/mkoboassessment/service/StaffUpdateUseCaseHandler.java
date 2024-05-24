package com.deenn.mkoboassessment.service;

import com.deenn.mkoboassessment.entity.Staff;
import com.deenn.mkoboassessment.entity.StaffLog;
import com.deenn.mkoboassessment.exception.StaffException;
import com.deenn.mkoboassessment.pojo.requests.StaffUpdateRequest;
import com.deenn.mkoboassessment.pojo.responses.StaffUpdateResponse;
import com.deenn.mkoboassessment.repository.StaffLogRepository;
import com.deenn.mkoboassessment.repository.StaffRepository;
import com.deenn.mkoboassessment.usecase.StaffUpdateUseCase;
import com.deenn.mkoboassessment.usecase.Validator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.deenn.mkoboassessment.pojo.constants.AppConstants.*;


@Service
@AllArgsConstructor
@Slf4j
public class StaffUpdateUseCaseHandler implements StaffUpdateUseCase {

    private final StaffLogRepository staffLogRepository;

    private final StaffRepository staffRepository;

    private Validator validator;

    @Override
    public StaffUpdateResponse execute(StaffUpdateRequest request) {
        log.info("StaffUpdateRequest: {}", request);
        try {
            Staff staff = validator.validate(request.getUuid());
            if (staff == null)
                throw new StaffException("customer not found");

            staff.setName(request.getName());
            staffRepository.save(staff);
            staffLogRepository.save(StaffLog.builder().status(ACCEPTED).payload(request.toString()).build());
        } catch (StaffException e) {
            log.error("error occurred while processing staff registration request {}:", e.getMessage());
            staffLogRepository.save(StaffLog.builder().status(REJECTED).payload(request.toString()).reason(e.getMessage()).build());
            return StaffUpdateResponse.builder().status(UPDATE_FAILED).success(false).build();
        }
        return StaffUpdateResponse.builder().status(UPDATE_SUCCESSFUL).success(true).build();
    }


}
