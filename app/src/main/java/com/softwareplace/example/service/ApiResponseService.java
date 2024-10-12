package com.softwareplace.example.service;

import com.softwareplace.example.model.ApiResponse;
import com.softwareplace.example.model.ApiVersion;
import org.springframework.stereotype.Service;

@Service
public class ApiResponseService {

    public ApiResponse getResponse() {
        ApiVersion apiVersion = ApiVersion.builder()
                .version("1.0")
                .build("1")
                .build();
        return ApiResponse.builder()
                .statusCode(200L)
                .message("Success")
                .timestamp(System.currentTimeMillis())
                .apiVersion(apiVersion)
                .build();
    }
}
