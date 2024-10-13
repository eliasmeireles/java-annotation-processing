I# Annotation Processing Example

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

> By following the above example, the mapper will be generated automatically, and you can find the implementation in
> project/build/generated/sources/annotationProcessor/java/main.

- **ApiResponseMapper**: Maps between `ApiResponse` and `ApiResponseRest`.

````java
public interface ApiVersionMapper {
    ApiVersionRest map(ApiVersion input);

    ApiVersion map(ApiVersionRest input);
}

@Mapper(
        componentModel = "spring"
)
public interface ApiResponseMapper extends ApiVersionMapper {
    ApiResponseRest map(ApiResponse input);

    ApiResponse map(ApiResponseRest input);
}

````

- **ApiVersionMapperImpl**

````java

@Generated(
        value = "org.mapstruct.ap.MappingProcessor",
        date = "2024-10-12T19:54:40-0300",
        comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.2.jar, environment: Java 21.0.3 (Oracle Corporation)"
)
@Component
public class ApiResponseMapperImpl implements ApiResponseMapper {

    @Override
    public ApiVersionRest map(ApiVersion input) {
        if (input == null) {
            return null;
        }

        ApiVersionRest.ApiVersionRestBuilder apiVersionRest = ApiVersionRest.builder();

        apiVersionRest.version(input.getVersion());
        apiVersionRest.build(input.getBuild());

        return apiVersionRest.build();
    }

    @Override
    public ApiVersion map(ApiVersionRest input) {
        if (input == null) {
            return null;
        }

        ApiVersion.ApiVersionBuilder apiVersion = ApiVersion.builder();

        apiVersion.version(input.getVersion());
        apiVersion.build(input.getBuild());

        return apiVersion.build();
    }

    @Override
    public ApiResponseRest map(ApiResponse input) {
        if (input == null) {
            return null;
        }

        ApiResponseRest.ApiResponseRestBuilder apiResponseRest = ApiResponseRest.builder();

        if (input.getStatusCode() != null) {
            apiResponseRest.statusCode(input.getStatusCode().intValue());
        }
        apiResponseRest.message(input.getMessage());
        apiResponseRest.timestamp(input.getTimestamp());
        apiResponseRest.apiVersion(map(input.getApiVersion()));

        return apiResponseRest.build();
    }

    @Override
    public ApiResponse map(ApiResponseRest input) {
        if (input == null) {
            return null;
        }

        ApiResponse.ApiResponseBuilder apiResponse = ApiResponse.builder();

        if (input.getStatusCode() != null) {
            apiResponse.statusCode(input.getStatusCode().longValue());
        }
        apiResponse.message(input.getMessage());
        apiResponse.timestamp(input.getTimestamp());
        apiResponse.apiVersion(map(input.getApiVersion()));

        return apiResponse.build();
    }
}
````

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

To get a response from the API, send a GET request to `/api/annotation/v1/check`.

### Example Request

```sh
curl -X GET http://localhost:8080/api/annotation/v1/check
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
