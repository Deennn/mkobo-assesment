package com.deenn.mkoboassessment.pojo.requests;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class GetPatientRequest {

    private String uuid;

    private int pageNumber;

    private int pageSize;
}
