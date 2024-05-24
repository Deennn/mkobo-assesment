package com.deenn.mkoboassessment.usecase;

import com.deenn.mkoboassessment.pojo.requests.GetPatientRequest;
import com.deenn.mkoboassessment.pojo.responses.PaginatedResponse;

public interface FetchPatientUseCase extends UseCaseFunction<GetPatientRequest, PaginatedResponse>{
}
