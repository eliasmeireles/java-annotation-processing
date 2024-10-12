package com.softwareplace.example.controller;

import com.softwareplace.example.model.mapper.ApiResponseMapper;
import com.softwareplace.example.rest.controller.AnnotationTestController;
import com.softwareplace.example.rest.model.ApiResponseRest;
import com.softwareplace.example.service.ApiResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AnnotationControllerImpl implements AnnotationTestController {

    private final ApiResponseMapper mapper;
    private final ApiResponseService service;

    @Override
    public ResponseEntity<ApiResponseRest> getResponseTest() {
        ApiResponseRest response = mapper.map(service.getResponse());
        return ResponseEntity.ok(response);
    }
}
