package com.deenn.mkoboassessment.service;

import com.deenn.mkoboassessment.entity.Patient;
import com.deenn.mkoboassessment.entity.PatientLog;
import com.deenn.mkoboassessment.exception.PatientException;
import com.deenn.mkoboassessment.pojo.requests.PatientRequest;
import com.deenn.mkoboassessment.pojo.responses.PatientResponse;
import com.deenn.mkoboassessment.repository.PatientLogRepository;
import com.deenn.mkoboassessment.repository.PatientRepository;
import com.deenn.mkoboassessment.usecase.DownloadPatientUseCase;
import com.deenn.mkoboassessment.usecase.Validator;
import com.opencsv.CSVWriter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDateTime;

import static com.deenn.mkoboassessment.pojo.constants.AppConstants.DOWNLOAD_FAILED;
import static com.deenn.mkoboassessment.pojo.constants.AppConstants.DOWNLOAD_SUCCESSFUL;

@Service
@AllArgsConstructor
@Slf4j
public class DownloadPatientUseCaseProcessor implements DownloadPatientUseCase {

    private final PatientRepository patientRepository;

    private final PatientLogRepository patientLogRepository;

    private final Validator validator;


    @Override
    public PatientResponse execute(PatientRequest request) {
        validator.validate(request.getUuid());
        Patient patient = patientRepository.findById(request.getPatientId()).orElseThrow(() -> new PatientException("Patient not found"));
        try {
            StringWriter writer = getStringWriter(patient);
            HttpHeaders headers = new HttpHeaders();
            headers.add(
                    "Content-Disposition",
                    "attachment; filename=patient-profile-"
                            + patient.getName().toLowerCase().replace(" ", "-")
                            + "-"
                            + LocalDateTime.now() + ".csv");
            String csv = writer.toString();
            patientLogRepository.save(PatientLog.builder().payload(request.toString()).status(DOWNLOAD_SUCCESSFUL).build());
            return PatientResponse.builder().csv(csv).build();
        } catch (IOException e) {
            log.error("patient csv download failed {}:", e.getMessage());
            patientLogRepository.save(PatientLog.builder().payload(request.toString()).status(DOWNLOAD_FAILED).reason(e.getMessage()).build());
            return PatientResponse.builder().csv(e.getMessage()).build();
        }
    }

    private static StringWriter getStringWriter(Patient patient) throws IOException {
        StringWriter writer = new StringWriter();
        try (CSVWriter csvWriter = new CSVWriter(writer)) {
            String[] header = {"id", "name", "age", "lastVisitDate"};
            csvWriter.writeNext(header);

            String[] data = {
                    patient.getId().toString(),
                    patient.getName(),
                    String.valueOf(patient.getAge()),
                    patient.getLastVisitDate().toString()
            };
            csvWriter.writeNext(data);
        }
        return writer;
    }
}
