package com.deenn.mkoboassessment.pojo.responses;

import lombok.*;

import java.time.Instant;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PatientResponse {

    private String csv;
}
