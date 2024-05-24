package com.deenn.mkoboassessment.controller;

import com.deenn.mkoboassessment.pojo.requests.StaffRegistrationRequest;
import com.deenn.mkoboassessment.pojo.requests.StaffUpdateRequest;
import com.deenn.mkoboassessment.pojo.responses.StaffRegistrationResponse;
import com.deenn.mkoboassessment.pojo.responses.StaffUpdateResponse;
import com.deenn.mkoboassessment.usecase.StaffRegistrationUseCase;
import com.deenn.mkoboassessment.usecase.StaffUpdateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/")
@RequiredArgsConstructor
@Validated
public class StaffController {

    private final StaffRegistrationUseCase useCase;

    private final StaffUpdateUseCase updateUseCase;


    @PostMapping("/v1/registration/register")
    public ResponseEntity<StaffRegistrationResponse> registerStaff(@RequestBody @Validated StaffRegistrationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(useCase.execute(request));
    }

    @PutMapping("/v1/update/staff")
    public ResponseEntity<StaffUpdateResponse> updateStaff(@RequestBody @Validated StaffUpdateRequest request) {
        return ResponseEntity.ok(updateUseCase.execute(request));
    }

}
