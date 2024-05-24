package com.deenn.mkoboassessment.pojo.requests;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PatientRequest {

    private String uuid;

    private Long patientId;
}
