package com.softwareplace.example.model;


import com.softwareplace.example.annotation.mapper.Mapper;
import com.softwareplace.example.model.mapper.ApiVersionMapper;
import com.softwareplace.example.rest.model.ApiResponseRest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Mapper(
        value = ApiResponseRest.class,
        priority = 1,
        componentModel = "spring",
        extendsTo = ApiVersionMapper.class
)
public class ApiResponse {
    private Long statusCode;

    private String message;

    private Long timestamp;

    private ApiVersion apiVersion;
}
