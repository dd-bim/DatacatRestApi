package datacat.service;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
// Spring Boot
import org.springframework.http.*;
import org.springframework.web.client.*;
import org.springframework.stereotype.Service;
import org.springframework.boot.web.client.RestTemplateBuilder;

// Logging
import org.slf4j.*;
import java.io.IOException;

// Mapping
import com.fasterxml.jackson.databind.*;

// Internal
import datacat.models.*;

// =====================================================================================================================
// S E R V I C E   S E C T I O N
// merging of requests from controller and custom rest template
// working out sending request and parsing json response
// perspectively the the service will include further parsing and processing of the response
// =====================================================================================================================
@Service
public class GraphQLService {
    private static final Logger logger = LoggerFactory.getLogger(GraphQLService.class);

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;
    private final CustomProperties customProperties;

    public GraphQLService(CustomProperties customProperties, RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper) {
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = objectMapper;
        this.customProperties = customProperties;
    }

    // =====================================================================================================================
    // main logic for query execution
    public String executeQuery(String query, String bearerToken) {
        logger.info(" E X E C U T I O N ");

        String url = customProperties.getServerUrl() + customProperties.getBasePath(); // URL string creation from custom application properties
        // logger.debug("URL: {}", url);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        if (bearerToken != null) {
            requestHeaders.setBearerAuth(bearerToken);
            logger.debug("Bearer Token used");
        } else {
            logger.debug("No Bearer Token used");
        }

        String requestBody = "{\"query\":\"" + query + "\"}";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, requestHeaders);
        logger.debug("Request Headers: {}", requestHeaders);
        logger.debug("Request Body: {}", requestBody);        

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            logger.debug("Raw response from GraphQLService: {}", response.getBody());
            return response.getBody();
        } catch (HttpServerErrorException e) {
            logger.error("Server error when executing query: status code = {}, response body = {}, headers = {}", 
                          e.getStatusCode(), e.getResponseBodyAsString(), e.getResponseHeaders());
            throw new RuntimeException("Server error", e);
        } catch (HttpClientErrorException e) {
            logger.error("Client error when executing query: status code = {}, response body = {}, headers = {}", 
                          e.getStatusCode(), e.getResponseBodyAsString(), e.getResponseHeaders());
            throw new RuntimeException("Client error", e);
        } catch (ResourceAccessException e) {
            logger.error("Resource access error when executing query: {}", e.getMessage());
            throw new RuntimeException("Resource access error", e);
        } catch (Exception e) {
            logger.error("Error executing query", e);
            throw new RuntimeException("Error executing query", e);
        }
    }

    // =====================================================================================================================
    // deserialization for easier handling as Java object and compile-time type checking
    // generic response parsing for all endpoints
    // newly added endpoints should be added to the list of response parsers below 
    private <T> T deserializeResponse(String response, String rootField, Class<T> valueType) {
        try {
            JsonNode rootNode = objectMapper.readTree(response); // parses response into generic map

            JsonNode errorsNode = rootNode.get("errors"); // extracts 'errors' node from the map
            if (errorsNode != null && errorsNode.isArray() && errorsNode.size() > 0) {
                logger.error("GraphQL errors: {}", errorsNode.toString());
                return null;
            }
            JsonNode dataNode = rootNode.get("data"); // extracts 'data' node from the map
            if (dataNode == null) {
                logger.error("No 'data' node in response");
                return null;
            }

            JsonNode unwrappedNode = dataNode.get(rootField); // extracts 'rootField' node from the 'data' node
            if (unwrappedNode == null) {
                logger.error("No '{}' field in the 'data' response", rootField);
                return null;
            }

            T parsedResponse = objectMapper.treeToValue(unwrappedNode, valueType); // converts the unwrapped node to the desired type
            logger.debug("Parsed response to {}: {}", valueType.getSimpleName(), parsedResponse);
            return parsedResponse;
        } catch (IOException e) {
            logger.error("Error parsing response to {}: {}", valueType.getSimpleName(), e.getMessage());
            return null;
        } 
    }

    // =====================================================================================================================
    // specified endpoint logic for each query
    public ClassDetailsResponseV1 getClassDetails(String id, String bearerToken) {
        // String query = "{ getSubject(id: \\\"" + id + "\\\") { classProperties:properties { name description propertyUri:comment propertySet:groups { nodes { id } } } name uri:comment uid: id versionDateUtc:created } }";
        String query = "{ getSubject(id: \\\"" + id + "\\\") { classProperties:properties { name description propertyUri:comment } name uri:comment uid: id versionDateUtc:created } }";
        String response = executeQuery(query, bearerToken);
        return deserializeResponse(response, "getSubject", ClassDetailsResponseV1.class);
    }

    public DictionaryResponseV1 getDictionary(String id, String bearerToken) {
        String query = "{ findBags (input:{pageSize:10000}) { nodes { uri:id, name, version:versionId } } }";
        String response = executeQuery(query, bearerToken);
        return deserializeResponse(response, "findBags", DictionaryResponseV1.class);
    }

    public ClassesResponseV1 getClasses(String id, String bearerToken) {
        String query = "{ findSubjects (input:{pageSize:10000}) { nodes { uri:id name version:versionId } } }";
        String response = executeQuery(query, bearerToken);
        return deserializeResponse(response, "findSubjects", ClassesResponseV1.class);
    }

    public PropertiesResponseV1 getProperties(String id, String bearerToken) {
        String query = "{ findProperties (input:{pageSize:10000}) { nodes { uri:id name version:versionId } } }";
        String response = executeQuery(query, bearerToken);
        return deserializeResponse(response, "findProperties", PropertiesResponseV1.class);
    }

    public StatisticsResponseV1 getStatistics() {
        String query = "{ statistics { catalogueItem:items { id count } } }";
        String response = executeQuery(query, null);
        return deserializeResponse(response, "statistics", StatisticsResponseV1.class);
    }   

    // add new endpoints here

}