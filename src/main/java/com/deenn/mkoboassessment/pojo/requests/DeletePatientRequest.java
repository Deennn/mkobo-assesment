package com.deenn.mkoboassessment.pojo.requests;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DeletePatientRequest {

    private String uuid;

    private String startDate;

    private String endDate;
}
