package datacat.restapi;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
// Spring Boot
import org.springframework.http.*;
import org.springframework.web.client.*;
import org.springframework.stereotype.Service;
import org.springframework.boot.web.client.RestTemplateBuilder;

// Java
import java.util.ArrayList;
import java.util.List;

// Logging
import org.slf4j.*;

// Mapping
import com.fasterxml.jackson.databind.*;
// import com.fasterxml.jackson.core.JsonProcessingException;

// Internal
import datacat.models.*;
import datacat.customization.CustomProperties;
import datacat.graphql.GraphQLClass;
import datacat.graphql.GraphQLDatacatSpecifics;
import datacat.graphql.GraphQLDictionary;
import datacat.graphql.IdExtractor;
import datacat.graphql.ResponseDeserializer;

// =====================================================================================================================
// S E R V I C E   S E C T I O N
// merging of requests from controller and custom rest template
// working out sending request and parsing json response
// perspectively the service will include further parsing and processing of the response
// =====================================================================================================================
@Service
public class ApiService {
    private static final Logger logger = LoggerFactory.getLogger(ApiService.class);

    private final RestTemplate restTemplate;
    private final CustomProperties customProperties;
    private final ResponseDeserializer responseDeserializer;
    private final IdExtractor idExtractor;

    public ApiService(CustomProperties customProperties, RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper, IdExtractor idExtractor) {
        this.restTemplate = restTemplateBuilder.build();
        this.customProperties = customProperties;
        this.responseDeserializer = new ResponseDeserializer(objectMapper);
        this.idExtractor = idExtractor;
    }

    // =====================================================================================================================
    // U T I L I T Y   M E T H O D S
    // utility methods for query execution and response deserialization
    // perspectively this section can be outsourced to a separate class
    // =====================================================================================================================
    // main logic for query execution
    public String executeQuery(String query, String bearerToken) {
        logger.info(" E X E C U T I O N ");

        String url = customProperties.getServerUrl() + customProperties.getBasePath(); // URL string creation from custom application properties
        logger.debug("URL: {}", url);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        if (bearerToken != null) {
            requestHeaders.setBearerAuth(bearerToken);
            logger.debug("Bearer Token used: {}", bearerToken);
            // logger.debug("Bearer Token used.");
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
    private <T> T deserializeGetResponse(String response, String rootField, Class<T> valueType) {
        return responseDeserializer.deserializeGetResponse(response, rootField, valueType);
    }

    private <T> T deserializeFindResponse(String response, String rootField, Class<T> valueType) {
        return responseDeserializer.deserializeFindResponse(response, rootField, valueType);
    }

    private <T> List<T> deserializeResponseAsList(String response, String rootField, Class<T> valueType) {
        return responseDeserializer.deserializeResponseAsList(response, rootField, valueType);
    }

    private List<String> extractGroupIdsFromResponse(String groupsResponse) {
        return idExtractor.extractGroupIdsFromResponse(groupsResponse);
    }

    // =====================================================================================================================
    // E N D P O I N T   L O G I C
    // specified endpoint logic for each query
    // newly added endpoints should have a t least one processing method here
    // rootField should match the field in the response (e.g. "getSubject", "getBag", "findSubjects", "findProperties")
    // =====================================================================================================================
    // SECTION: CLASS
    // =====================================================================================================================
    // ENDPOINT: /api/Class/v1
    public ClassContractV1 getClassDetails(String bearerToken, String id, boolean includeProperties, String languageCode) {

        String query = GraphQLClass.getClassDetailsQuery(id, includeProperties, languageCode);
        String response = executeQuery(query, bearerToken);
        String rootField = "getSubject";
        
        ClassContractV1 classDetails = deserializeGetResponse(response, rootField, ClassContractV1.class);
        logger.debug("Deserialized Class Details Response: {}", classDetails);

        if (classDetails != null) {
            String serverUrl = customProperties.getServerUrl(); 
            classDetails.generateUri(serverUrl);
            classDetails.transformToLowerCase();
            for (ClassPropertyContractV1 property : classDetails.getClassProperties()) {
                property.generateUri(serverUrl);
                property.transformToLowerCase();
            }
        }
        return classDetails;
    }


    // =====================================================================================================================
    // ENDPOINT: /api/Class/Relations/v1


    // =====================================================================================================================
    // ENDPOINT: /api/Class/Properties/v1
    public ClassPropertiesContractV1 classPropertiesGet(String bearerToken, String id, int queryOffset, int queryLimit, String languageCode) {
        String query = GraphQLClass.getClassPropertiesQuery(id, queryOffset, queryLimit, languageCode);
        logger.debug("GraphQL Query: {}", query);
        String response = executeQuery(query, bearerToken);
        String rootField = "findSubjects";
    
        logger.debug("Response from GraphQL: {}", response);
    
        ClassPropertiesContractV1 classProperties = deserializeFindResponse(response, rootField, ClassPropertiesContractV1.class);
        logger.debug("Deserialized Class Properties Response: {}", classProperties);
    
        if (classProperties != null) {
            for (ClassPropertyItemContractV1 property : classProperties.getClassProperties()) {
                String serverUrl = customProperties.getServerUrl();
                property.generateUri(serverUrl);
                property.transformToLowerCase();
            }
            return classProperties;
        }
        return null;
    }


    // =====================================================================================================================
    // ENDPOINT: /api/Dictionary/v1
    // OPTION 1: query to fetch all dictionaries
    public DictionaryResponseContractV1 getOneDictionary(String bearerToken, String id, boolean includeTestDict, int offset, int limit) {
        
        String query = GraphQLDictionary.getOneDictionary(id, bearerToken);
        String response = executeQuery(query, bearerToken);
        String rootField = "findBags";
        
        DictionaryResponseContractV1 dictionaryResponse = deserializeFindResponse(response, rootField, DictionaryResponseContractV1.class);
        logger.debug("Deserialized Dictionary Response: {}", dictionaryResponse);

        return dictionaryResponse;
    }
    // OPTION 2: query to fetch all dictionaries
    public DictionaryResponseContractV1 getAllDictionaries(String bearerToken, boolean includeTestDict, int offset, int limit) {
        
        String query = GraphQLDictionary.getAllDictionaries(bearerToken);
        String response = executeQuery(query, bearerToken);
        String rootField = "findBags";
        
        DictionaryResponseContractV1 dictionaryResponse = deserializeFindResponse(response, rootField, DictionaryResponseContractV1.class);
        logger.debug("Deserialized Dictionaries Response: {}", dictionaryResponse);

        return dictionaryResponse;
    }


    // =====================================================================================================================
    // ENDPOINT: /api/Dictionary/v1/Classes
    public DictionaryClassesResponseContractV1Classes getDictionaryClasses(String bearerToken, String id, int offset, int limit, String languageCode) {
        
        // STEP 1: Execute the first query to fetch dictionary and groups related to the requested dictionary
        String dictGroupQuery = GraphQLDictionary.getDictionaryGroupQuery(id, languageCode);
        String dictGroupResponse = executeQuery(dictGroupQuery, bearerToken);
        logger.debug("FIRST QUERY EXECUTED");

        // STEP 2: Extract dictionary attributes, and group IDs from the first query response
        List<String> internalGroupIds = extractGroupIdsFromResponse(dictGroupResponse);
        logger.debug("Internal Group IDs: {}", internalGroupIds);

        // STEP 3: Execute second query for each group ID
        List<ClassListItemContractV1Classes> allClasses = new ArrayList<>();
        for (String groupId : internalGroupIds) {
            String classesQuery = GraphQLDictionary.getGroupClassesQuery(groupId, limit, languageCode);
            String classesResponse = executeQuery(classesQuery, bearerToken);
            String rootField = "getBag";

            List<ClassListItemContractV1Classes> classes = deserializeResponseAsList(classesResponse, rootField, ClassListItemContractV1Classes.class);
            
            if (classes != null) {
                String serverUrl = customProperties.getServerUrl();
                for (ClassListItemContractV1Classes classItem : classes) {
                    classItem.generateUri(serverUrl);
                    classItem.transformToLowerCase();
                }
            }
            allClasses.addAll(classes);
        }
        String rootField = "getBag";

        DictionaryClassesResponseContractV1Classes dictionaryResponse = deserializeGetResponse(dictGroupResponse, rootField, DictionaryClassesResponseContractV1Classes.class); 
        logger.debug("Deserialized Dictionary Response: {}", dictionaryResponse);

        if (dictionaryResponse != null) {
            String serverUrl = customProperties.getServerUrl();
            dictionaryResponse.generateUri(serverUrl);
            dictionaryResponse.transformToLowerCase();
        }
        
        // STEP 4: Set the classes in the dictionary response object and return the final object
        dictionaryResponse.setClasses(allClasses);

        return dictionaryResponse;
    }
  
    // =====================================================================================================================


    // =====================================================================================================================
    // ENDPOINT: /api/Statistics
    public StatisticsResponseContractV1 getStatistics() {
        String query = GraphQLDatacatSpecifics.getStatisticsQuery();
        String response = executeQuery(query, null);
        String rootField = "statistics";

        StatisticsResponseContractV1 statistics = deserializeGetResponse(response, rootField, StatisticsResponseContractV1.class);
        return statistics;
    }   

    // add new endpoints here

}