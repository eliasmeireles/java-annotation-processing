package com.softwareplace.example.model;

import com.softwareplace.example.annotation.mapper.Mapper;
import com.softwareplace.example.rest.model.ApiVersionRest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Mapper(value = ApiVersionRest.class, generateImpl = false)
public class ApiVersion {
    private String version;

    private String build;
}
