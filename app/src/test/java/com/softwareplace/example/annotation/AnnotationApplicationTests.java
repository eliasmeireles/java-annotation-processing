package com.softwareplace.example.annotation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AnnotationApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
        // Run the request to the endpoint and validate the response
        ResponseEntity<String> response = restTemplate.getForEntity("/check", String.class);

        // Validate the status code
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Validate the response body (customize based on your endpoint)
        assertThat(response.getBody()).contains("\"statusCode\":200");
        assertThat(response.getBody()).contains("\"message\":\"Success\"");
        assertThat(response.getBody()).contains("{\"version\":\"1.0\",\"build\":\"1\"}");
    }
}
