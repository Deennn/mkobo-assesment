package com.deenn.mkoboassessment.pojo.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeletedPatientsResponse {

    private int numberOfDeletedPatients;

    private String status;
}
