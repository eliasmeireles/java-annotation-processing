# Annotation Processing Example

## Overview

> To generate the mapper with MapStruct, simply add the `@Mapper` annotation to the model object.

This project is a Spring Boot application that provides RESTful APIs for handling annotations. It uses Java and Kotlin
as the primary programming languages and Gradle for build automation.

> Instead of manually writing the mapper, you can use the `@Mapper` annotation to generate the mapper for you. Just add
> the annotation to the model object as following example.

```java

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Mapper(
        value = ApiVersionRest.class,
        generateImpl = false
)
public class ApiVersion {
    // ...
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Mapper(
        priority = 1,
        value = ApiResponseRest.class,
        componentModel = "spring",
        extendsTo = ApiVersionMapper.class
)
public class ApiResponse {
    // ...
}
```

## Project Structure

- **Controller**: Handles incoming HTTP requests and returns responses.
- **Service**: Contains business logic.
- **Mapper**: Maps between different object models.
- **Model**: Defines the data structures used in the application.

### Models

- **ApiResponse**: Represents the API response in the service layer.
- **ApiResponseRest**: Represents the API response in the REST layer.
- **ApiVersion**: Represents the version information in the service layer.
- **ApiVersionRest**: Represents the version information in the REST layer.

## Configuration

The application configuration is defined in the `application.yaml` file.

```yaml
server:
  servlet:
    context-path: /api/annotation/v1/

spring:
  application:
    name: annotation
```

## Example Usage

To get a response from the API, send a GET request to `/api/annotation/v1/responseTest`.

### Example Request

```sh
curl -X GET http://localhost:8080/api/annotation/v1/responseTest
```

### Example Response

```json
{
  "statusCode": 200,
  "message": "Success",
  "timestamp": 1728773537564,
  "apiVersion": {
    "version": "1.0",
    "build": "1"
  }
}
```

## Dependencies

- Spring Boot
- MapStruct
- Lombok

## Build and Run

To build and run the application, use the following Gradle commands:

```sh
./gradlew build
./gradlew bootRun
```

## Requirements

- Java 21
