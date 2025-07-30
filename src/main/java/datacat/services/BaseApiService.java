package datacat.services;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
// Spring Boot
import org.springframework.http.*;
import org.springframework.web.client.*;
import org.springframework.stereotype.Service;
import org.springframework.boot.web.client.RestTemplateBuilder;

// Java
import java.util.List;

// Logging
import lombok.extern.slf4j.Slf4j;

// Internal
import datacat.customization.CustomProperties;
import datacat.graphql.GraphQLResponseDeserializer;

// =====================================================================================================================
// B A S E   S E R V I C E   S E C T I O N
// Base service containing common utility methods for all API services
// =====================================================================================================================
@Service
@Slf4j
public class BaseApiService {

    private final RestTemplate restTemplate;
    private final CustomProperties customProperties;
    private final GraphQLResponseDeserializer responseDeserializer;
    private final String serverUrl; // Cache the server URL to avoid repeated calls

    public BaseApiService(CustomProperties customProperties, RestTemplateBuilder restTemplateBuilder,
            GraphQLResponseDeserializer responseDeserializer) {
        this.restTemplate = restTemplateBuilder.build();
        this.customProperties = customProperties;
        this.responseDeserializer = responseDeserializer;
        this.serverUrl = customProperties.getServerUrl(); // Initialize once
    }

    // =====================================================================================================================
    // U T I L I T Y M E T H O D S
    // utility methods for query execution and response deserialization
    // =====================================================================================================================
    
    // main logic for query execution
    public String executeQuery(String query, String bearerToken) {
        log.info(" E X E C U T I O N ");

        String url = serverUrl + customProperties.getBasePath(); // URL string creation from
                                                                 // custom application properties
        log.debug("URL: {}", url);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        if (bearerToken != null) {
            requestHeaders.setBearerAuth(bearerToken);
            log.debug("Bearer Token used: {}", bearerToken);
            // log.debug("Bearer Token used.");
        } else {
            log.debug("No Bearer Token used");
        }

        String requestBody = "{\"query\":\"" + query + "\"}";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, requestHeaders);
        log.debug("Request Headers: {}", requestHeaders);
        log.debug("Request Body: {}", requestBody);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            log.debug("Raw response from GraphQLService: {}", response.getBody());
            return response.getBody();
        } catch (HttpServerErrorException e) {
            log.error("Server error when executing query: status code = {}, response body = {}, headers = {}",
                    e.getStatusCode(), e.getResponseBodyAsString(), e.getResponseHeaders());
            throw new RuntimeException("Server error", e);
        } catch (HttpClientErrorException e) {
            log.error("Client error when executing query: status code = {}, response body = {}, headers = {}",
                    e.getStatusCode(), e.getResponseBodyAsString(), e.getResponseHeaders());
            throw new RuntimeException("Client error", e);
        } catch (ResourceAccessException e) {
            log.error("Resource access error when executing query: {}", e.getMessage());
            throw new RuntimeException("Resource access error", e);
        } catch (Exception e) {
            log.error("Error executing query", e);
            throw new RuntimeException("Error executing query", e);
        }
    }

    // =====================================================================================================================
    // Deserialization utility methods
    // =====================================================================================================================
    
    protected <T> T deserializeGetResponse(String response, String rootField, Class<T> modelType) {
        return responseDeserializer.deserializeGetResponse(response, rootField, modelType);
    }

    protected <T> T deserializeOuterFindResponse(String response, String rootField, Class<T> modelType) {
        return responseDeserializer.deserializeOuterFindResponse(response, rootField, modelType);
    }

    protected <T> List<T> deserializeGeneralInnerFindResponse(String response, String rootField, Class<T> modelType) {
        return responseDeserializer.deserializeGeneralInnerFindResponse(response, rootField, modelType);
    }

    protected <T> List<T> deserializeDictionaryInnerFindResponse(String response, String rootField, Class<T> modelType) {
        return responseDeserializer.deserializeDictionaryInnerFindResponse(response, rootField, modelType);
    }

    protected <T> List<T> deserializeClassInnerFindResponse(String response, String rootField, Class<T> modelType) {
        return responseDeserializer.deserializeClassInnerFindResponse(response, rootField, modelType);
    }

    protected <T> List<T> deserializePropertyClassesInnerFindResponse(String response, String rootField, Class<T> modelType) {
        return responseDeserializer.deserializePropertyClassesInnerFindResponse(response, rootField, modelType);
    }

    protected String extractPropertyUriFromFindResponse(String response, String rootField) {
        return responseDeserializer.extractPropertyUriFromFindResponse(response, rootField);
    }

    // =====================================================================================================================
    // Getter methods for common dependencies
    // =====================================================================================================================
    
    protected String getServerUrl() {
        return serverUrl;
    }

    protected GraphQLResponseDeserializer getResponseDeserializer() {
        return responseDeserializer;
    }
}
