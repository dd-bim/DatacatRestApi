package datacat.graphql;

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
import com.fasterxml.jackson.core.JsonProcessingException;

// Internal
import datacat.models.*;
import datacat.customization.CustomProperties;

// =====================================================================================================================
// S E R V I C E   S E C T I O N
// merging of requests from controller and custom rest template
// working out sending request and parsing json response
// perspectively the service will include further parsing and processing of the response
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
    // deserialization for easier handling as Java object and compile-time type checking
    // generic response parsing for all endpoints
    private <T> List<T> deserializeResponseAsList(String response, String rootField, Class<T> valueType) {
        try {
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode dataNode = rootNode.path("data").path(rootField);
    
            if (dataNode.isMissingNode()) {
                logger.error("No '{}' field in the 'data' response", rootField);
                return new ArrayList<>();
            }
    
            JsonNode collectsNode = dataNode.path("collects").path("nodes"); // navigates to collects.nodes[].relatedThings based on the provided JSON structure
            List<T> resultList = new ArrayList<>();
    
            if (collectsNode.isArray()) { // loops through each node in collects.nodes and access relatedThings array within each node
                for (JsonNode node : collectsNode) {
                    JsonNode relatedThingsNode = node.path("relatedThings");
                    
                    if (relatedThingsNode.isArray()) { // deserialize each relatedThings entry into instances of valueType and add them to the resultList
                        List<T> relatedThings = objectMapper.treeToValue(
                            relatedThingsNode,
                            objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, valueType)
                        );
                        resultList.addAll(relatedThings);
                    }
                }
            }
    
            return resultList;
        } catch (Exception e) {
            logger.error("Error deserializing response", e);
            return new ArrayList<>();
        }
    }

    private <T> T deserializeResponse(String response, String rootField, Class<T> valueType) {
        try {
            JsonNode rootNode = objectMapper.readTree(response); // parses response into generic map to skip root node
            JsonNode dataNode = rootNode.path("data").path(rootField);
            if (dataNode.isMissingNode()) { // checks if the data node is missing (e.g. when the query is invalid)
                logger.error("No '{}' field in the 'data' response", rootField);
                return null;
            }
    
            JsonNode nodesNode = dataNode.path("nodes"); // checks if the dataNode contains a "nodes" field
            if (!nodesNode.isMissingNode() && nodesNode.isArray()) {
                return objectMapper.treeToValue(nodesNode, objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, valueType)); // deserializes the "nodes" array into a List of valueType
            }
    
            T result = objectMapper.treeToValue(dataNode, valueType); // deserializes the single object
        
            return result;
        } catch (Exception e) {
            logger.error("Error deserializing response", e);
            return null;
        }
    }

    // =====================================================================================================================
    // extracting ids from responses into lists
    // this method should be generalized, so it is also accessible to other endpoints
    private List<String> extractGroupIdsFromResponse(String groupsResponse) {
        List<String> groupIds = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper(); // initializes the ObjectMapper
            JsonNode rootNode = objectMapper.readTree(groupsResponse); // parses the response into a JsonNode
    
            // the following should be transformed into an if-else statement according to the rootField (get or find)
            // currently only working for 'getBag'
            JsonNode relatedThingsNode = rootNode.at("/data/getBag/collects/nodes/0/relatedThings"); // navigates through the response to find the "relatedThings" node
            // JsonNode relatedThingsNode = rootNode.at("/data/findBags/nodes/0/collects/nodes/0/relatedThings"); 

            if (relatedThingsNode.isArray()) { // checks if the node is an array and extract all "internalGroupId" values
                for (JsonNode thingNode : relatedThingsNode) {
                    JsonNode groupIdNode = thingNode.get("internalGroupId"); // extracts and add the internalGroupId to the list
                    if (groupIdNode != null) {
                        groupIds.add(groupIdNode.asText());
                    }
                }
            }
        } catch (JsonProcessingException e) {
            logger.error("Error parsing JSON response for group IDs", e);
        }
        logger.debug("Extracted Group IDs: {}", groupIds);
        return groupIds;
    }


    // =====================================================================================================================
    // E N D P O I N T   L O G I C
    // specified endpoint logic for each query
    // newly added endpoints should have a t least one processing method here
    // rootField should match the field in the response (e.g. "getSubject", "getBag", "findSubjects", "findProperties")
    // =====================================================================================================================
    // ENDPOINT: /api/Class/v1
    public ClassContractV1 getClassDetails(String bearerToken, String id, boolean includeProperties, String languageCode) {

        String query = GraphQLClass.getClassDetailsQuery(id, includeProperties, languageCode);
        String response = executeQuery(query, bearerToken);
        
        ClassContractV1 classDetails = deserializeResponse(response, "getSubject", ClassContractV1.class);
        logger.debug("Deserialized Dictionary Response: {}", classDetails);

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
    // ENDPOINT: /api/Dictionary/v1
    // NOT FUNCTIONAL
    // OPTION 1: query to fetch all dictionaries
    public DictionaryResponseContractV1 getDictionary(String bearerToken, String id, boolean includeTestDict, int offset, int limit) {
        
        String query = GraphQLDictionary.getDictionary(id, bearerToken);
        String response = executeQuery(query, bearerToken);
        
        DictionaryResponseContractV1 dictionaryResponse = deserializeResponse(response, "getBag", DictionaryResponseContractV1.class);
        logger.debug("Deserialized Dictionary Response: {}", dictionaryResponse);

        return dictionaryResponse;
    }
    // OPTION 2: query to fetch all dictionaries
    public DictionaryResponseContractV1 getAllDictionaries(String bearerToken, boolean includeTestDict, int offset, int limit) {
        
        String query = GraphQLDictionary.getAllDictionaries(bearerToken);
        String response = executeQuery(query, bearerToken);
        
        DictionaryResponseContractV1 dictionaryResponse = deserializeResponse(response, "findBags", DictionaryResponseContractV1.class);
        logger.debug("Deserialized Dictionary Response: {}", dictionaryResponse);

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

            List<ClassListItemContractV1Classes> classes = deserializeResponseAsList(classesResponse, "getBag", ClassListItemContractV1Classes.class);
            
            if (classes != null) {
                String serverUrl = customProperties.getServerUrl();
                for (ClassListItemContractV1Classes classItem : classes) {
                    classItem.generateUri(serverUrl);
                    classItem.transformToLowerCase();
                }
            }
            allClasses.addAll(classes);
        }

        DictionaryClassesResponseContractV1Classes dictionaryResponse = deserializeResponse(dictGroupResponse, "getBag", DictionaryClassesResponseContractV1Classes.class); 
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
    // getProperties
    // public PropertiesResponseV1 getProperties(String id, String bearerToken) {
    //     String query = "{ findProperties(input:{pageSize:10000}) { properties:nodes { uri:id name version:versionId } propertiesTotalCount:totalElements } }";
    //     String response = executeQuery(query, bearerToken);
    //     return deserializeResponse(response, "findProperties", PropertiesResponseV1.class);
    // }

    // =====================================================================================================================
    // ENDPOINT: /api/Statistics
    public StatisticsResponseContractV1 getStatistics() {
        String query = GraphQLDatacatSpecifics.getStatisticsQuery();
        String response = executeQuery(query, null);

        StatisticsResponseContractV1 statistics = deserializeResponse(response, "statistics", StatisticsResponseContractV1.class);
        return statistics;
    }   

    // add new endpoints here

}