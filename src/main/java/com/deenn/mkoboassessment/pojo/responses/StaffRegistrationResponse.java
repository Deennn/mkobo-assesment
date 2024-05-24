package com.deenn.mkoboassessment.pojo.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StaffRegistrationResponse {

    private String status;

    private boolean success;

}
