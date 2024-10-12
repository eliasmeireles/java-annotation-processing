package com.softwareplace.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AnnotationApplication {

    private static final Logger logger = LoggerFactory.getLogger(AnnotationApplication.class);

    @Value("${server.port:8080}")
    private int serverPort;

    @Value("${server.servlet.context-path:/}")
    private String contextPath;

    public static void main(String[] args) {
        SpringApplication.run(AnnotationApplication.class, args);
    }

    @Bean
    public CommandLineRunner logApplicationUrl() {
        return args -> {
            String url = String.format("http://localhost:%d%s", serverPort, contextPath);
            logger.info("Application is running at: {}swagger-ui/index.html", url);
        };
    }
}
