package com.deenn.mkoboassessment.pojo.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import static com.deenn.mkoboassessment.pojo.constants.AppConstants.NAME_REQUIRED;
import static com.deenn.mkoboassessment.pojo.constants.AppConstants.UUID_REQUIRED;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class StaffUpdateRequest {

    @NotNull(message = UUID_REQUIRED)
    @NotEmpty(message = UUID_REQUIRED)
    private String uuid;

    @NotNull(message = NAME_REQUIRED)
    @NotEmpty(message = NAME_REQUIRED)
    private String name;
}
