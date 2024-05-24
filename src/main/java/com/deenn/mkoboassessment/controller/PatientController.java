package com.deenn.mkoboassessment.controller;

import com.deenn.mkoboassessment.pojo.requests.DeletePatientRequest;
import com.deenn.mkoboassessment.pojo.requests.GetPatientRequest;
import com.deenn.mkoboassessment.pojo.requests.PatientRequest;
import com.deenn.mkoboassessment.pojo.responses.DeletedPatientsResponse;
import com.deenn.mkoboassessment.pojo.responses.PaginatedResponse;
import com.deenn.mkoboassessment.pojo.responses.PatientResponse;
import com.deenn.mkoboassessment.usecase.DeletePatientsUseCase;
import com.deenn.mkoboassessment.usecase.DownloadPatientUseCase;
import com.deenn.mkoboassessment.usecase.FetchPatientUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.deenn.mkoboassessment.pojo.constants.AppConstants.PAGE_NUMBER;
import static com.deenn.mkoboassessment.pojo.constants.AppConstants.PAGE_SIZE;

@RestController
@RequestMapping(path = "api/")
@RequiredArgsConstructor
@Validated
public class PatientController {

    private final FetchPatientUseCase useCase;

    private final DownloadPatientUseCase downloadPatientUseCase;

    private final DeletePatientsUseCase deletePatientProfiles;

    @GetMapping("/v1/patients")
    public ResponseEntity<PaginatedResponse> getPatients(@RequestParam String uuid, @RequestParam(defaultValue = PAGE_NUMBER) int pageNo,
                                                           @RequestParam(defaultValue = PAGE_SIZE) int pageSize) {
        return ResponseEntity.ok(useCase.execute(GetPatientRequest.builder().uuid(uuid).pageNumber(pageNo).pageSize(pageSize).build()));
    }

    @GetMapping("/v1/download-patient-profile")
    public ResponseEntity<String> downloadPatientProfile (@RequestParam String uuid, @RequestParam Long patientId) {
        PatientResponse execute = downloadPatientUseCase.execute(PatientRequest.builder().uuid(uuid).patientId(patientId).build());
        if (execute == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok(execute.getCsv());
    }

    @DeleteMapping( "/v1/delete-patient-profiles")
    public ResponseEntity<DeletedPatientsResponse> deletePatientProfiles (@RequestParam String uuid,
                                                                      @RequestParam String startDate,
                                                                      @RequestParam String endDate) {
        DeletedPatientsResponse response = deletePatientProfiles.execute(DeletePatientRequest.builder().uuid(uuid).startDate(startDate).endDate(endDate).build());
        return ResponseEntity.ok(response);
    }
}
