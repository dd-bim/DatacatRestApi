package datacat.service;

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
// import java.util.Collections;
// import java.util.List;
// import java.util.List;

// Logging
import org.slf4j.*;

// Mapping
import com.fasterxml.jackson.databind.*;

// Internal
import datacat.models.*;

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
                // Deserialize the "nodes" array into a List of valueType
                return objectMapper.treeToValue(nodesNode, objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, valueType));
            }
    
            T result = objectMapper.treeToValue(dataNode, valueType); // deserializes the single object
    
            // if (dataNode.has("synonyms")) { // handles synonyms separately
            //     JsonNode synonymsNode = dataNode.path("synonyms");
            //     List<String> synonyms = new ArrayList<>();
            //     for (JsonNode synonymNode : synonymsNode) {
            //         synonyms.add(synonymNode.path("value").asText());
            //     }
            //     ((ClassDetailsResponseV1) result).setSynonyms(synonyms);
            // }
    
            return result;
        } catch (Exception e) {
            logger.error("Error deserializing response", e);
            return null;
        }
    }
    // private <T> T deserializeResponse(String response, String rootField, Class<T> valueType) {
    //     try {
    //         JsonNode rootNode = objectMapper.readTree(response); // parses response into generic map to skip root node
    //         JsonNode dataNode = rootNode.path("data").path(rootField);
    //         if (dataNode.isMissingNode()) { // checks if the data node is missing (e.g. when the query is invalid)
    //             logger.error("No '{}' field in the 'data' response", rootField);
    //             return null;
    //         }

    //         JsonNode nodesNode = dataNode.path("nodes"); // checks if the dataNode contains a "nodes" field
    //         if (!nodesNode.isMissingNode() && nodesNode.isArray()) {
    //             // Deserialize the "nodes" array into a List of valueType
    //             return objectMapper.treeToValue(nodesNode, objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, valueType));
    //         }

    //         T result = objectMapper.treeToValue(dataNode, valueType); // deserializes the single object

    //         if (dataNode.has("synonyms")) { // handles synonyms separately
    //             JsonNode synonymsNode = dataNode.path("synonyms");
    //             List<String> synonyms = new ArrayList<>();
    //             for (JsonNode synonymNode : synonymsNode) {
    //                 synonyms.add(synonymNode.path("value").asText());
    //             }
    //             ((ClassDetailsResponseV1) valueType.getDeclaredConstructor().newInstance()).setSynonyms(synonyms);
    //         }
            
    //         return result;
    //     } catch (Exception e) {
    //         logger.error("Error deserializing response", e);
    //         return null;
    //     }
    // }

    // =====================================================================================================================
    // specified endpoint logic for each query
    // newly added endpoints should have a t least one processing method here
    // rootField should match the field in the response (e.g. "getSubject", "getBag", "findSubjects", "findProperties")
    // =====================================================================================================================
    // getClassDetails endpoint
    public ClassContractV1 getClassDetails(String id, String bearerToken) {

        // features that are not implemented yet are commented out
        // lesser effort: 
        // - name with lang input
        // greater effort (needs further implementation in model class and deserialization):
        // - synonyms
        // - collectedBy
        // - classProperties

        String query = "{ getSubject(id: \\\"" + id + "\\\") { " +
                        // "   collectedBy { " +
                        // "       nodes { " +
                        // "           parentClassReference:relatingCollection { " +
                        // "           uri:id " +
                        // "           name " +
                        // "           } " +
                        // "       } " +
                        // "   } " +
                        "   classProperties:properties { " +
                        "       name " +
                        "       description " +
                        // "       dataType:recordType " +
                        "       propertyUri:id " +
                        "   } " +
                        "   activationDateUtc:created " +
                        "   description " +
                        "   name(input:{languageTags:\\\"de\\\"}) " + // with input leads to error
                        // "   name " +
                        "   revisionDateUtc:lastModified " +
                        "   uid:id " +
                        "   versionDateUtc:versionDate " +
                        "   versionNumber:versionId " +
                        "   classType:recordType " +
                        // "   synonyms:names { " + // wrong approach
                        // "       value " +
                        // "       }" +
                        "   } " +
                        "} ";

        String response = executeQuery(query, bearerToken);
        
        ClassContractV1 classDetails = deserializeResponse(response, "getSubject", ClassContractV1.class);

        if (classDetails != null) {
            classDetails.generateUriFromUid();
        }
    
        return classDetails;
    }

    // =====================================================================================================================
    // getDictionary (optionally) by ID
    // public DictionaryResponseV1 getDictionary(String id, String bearerToken) {
    //     final String dictTag = "6f96aaa7-e08f-49bb-ac63-93061d4c5db2"; // 'dictTag' is a constant internal value pointing only on models in XtdBag
    //     // String query = "{ getBag(id: \\\"" + id + "\\\") { uri:id name version:versionId } }";
    //     String query = "{ findBags(input:{pageSize:1 tagged:\\\"" + dictTag + "\\\" idIn: \\\"" + id + "\\\"}) { " +
    //                     "   dictionaries:nodes { " +
    //                     "       uri:id " +
    //                     "       name " +
    //                     "       version:versionId " +
    //                     "   }" +
    //                     "   dictionariesTotalCount:totalElements" +
    //                     "} }";
    //     String response = executeQuery(query, bearerToken);
    //     return deserializeResponse(response, "findBags", DictionaryResponseV1.class);
    // }

    // =====================================================================================================================
    // getAllDictionaries
    // public DictionaryResponseV1 getAllDictionaries(String bearerToken) {
    //     final String dictTag = "6f96aaa7-e08f-49bb-ac63-93061d4c5db2"; // 'dictTag' is a constant internal value pointing only on models in XtdBag
    //     String query = "{ findBags(input:{pageSize:10000 tagged: \\\"" + dictTag + "\\\"}) {" +
    //                     "   dictionaries:nodes {" +
    //                     "       uri:id " +
    //                     "       name " +
    //                     "       version:versionId " +
    //                     "   }" +
    //                     "   dictionariesTotalCount:totalElements" +
    //                     "} }";
    //     String response = executeQuery(query, bearerToken);
    //     return deserializeResponse(response, "findBags", DictionaryResponseV1.class);
    // }

    // =====================================================================================================================
    // getClasses
    // public ClassesResponseV1 getClasses(String id, String bearerToken) {
    //     final String dictTag = "6f96aaa7-e08f-49bb-ac63-93061d4c5db2"; // 'dictTag' is a constant internal value pointing only on models in XtdBag
    //     // STEP 1: first query to fetch the groups related to the requested dictionary
    //     String groupQuery = "{ findBags(input:{pageSize:200 tagged: \\\"" + dictTag + "\\\" idIn: \\\"" + id + "\\\"}) {" +
    //                     "   dictionary:nodes {" +
    //                     "       name " + // when working fine, this should only be useful for debugging (internal console log)
    //                     "       collects {" +
    //                     "       nodes {" +
    //                     "           groupTag:id " +
    //                     "           groups:relatedThings {" +
    //                     "               id " +
    //                     "               groupName:name " +
    //                     "} } } } } }";
    //     String groupResponse = executeQuery(groupQuery, bearerToken);
    //     List<String> groupIds = extractGroupIdsFromResponse(groupResponse);
    //     // STEP 2: queries fetch classes related to certain groups
    //     List<ClassesResponseV1> allClasses = new ArrayList<>();
    //     for (String groupId : groupIds) {
    //         String classQuery = "{ getBag(id: \"" + groupId + "\") {" +
    //                             "   gourpName:name " +
    //                             "   collects {" +
    //                             "       nodes {" +
    //                             "           classTag:id " +
    //                             "           classes:relatedThings {" +
    //                             "               className:name " +
    //                             "} } } } }";
    //         String classResponse = executeQuery(classQuery, bearerToken);
    //         List<ClassesResponseV1> classes = extractClassesFromResponse(classResponse);
    //         allClasses.addAll(classes);
    //     }
    //     // String response = executeQuery(query, bearerToken);
    //     // return deserializeResponse(response, "findSubjects", ClassesResponseV1.class);
    //     return combineClassesResponse(allClasses); 
    // }
    // private List<String> extractGroupIdsFromResponse(String response) {
    //     List<String> groupIds = new ArrayList<>();
    //     try {
    //         ObjectMapper mapper = new ObjectMapper();
    //         JsonNode rootNode = mapper.readTree(response);
    //         JsonNode groupNodes = rootNode.path("data").path("findBags").path("dictionary").path("nodes");
    //         // Loop through the nodes to extract group IDs
    //         for (JsonNode dictionaryNode : groupNodes) {
    //             JsonNode collectsNode = dictionaryNode.path("collects").path("nodes");
    //             for (JsonNode collectNode : collectsNode) {
    //                 String groupId = collectNode.path("groupTag").asText();
    //                 groupIds.add(groupId);
    //             }
    //         }
    //     } catch (Exception e) {
    //         // Handle parsing exceptions (logging, rethrowing, etc.)
    //         logger.error("Failed to parse group response", e);
    //     }
    //     return groupIds;
    // }
    // private List<ClassesResponseV1> extractClassesFromResponse(String response) {
    //     List<ClassesResponseV1> classList = new ArrayList<>();
    //     try {
    //         ObjectMapper mapper = new ObjectMapper();
    //         JsonNode rootNode = mapper.readTree(response);
    //         JsonNode classNodes = rootNode.path("data").path("getBag").path("collects").path("nodes");
    //         // Loop through nodes to extract classes
    //         for (JsonNode collectNode : classNodes) {
    //             JsonNode relatedThingsNode = collectNode.path("classes").path("relatedThings");
    //             for (JsonNode classNode : relatedThingsNode) {
    //                 ClassesResponseV1 classResponse = new ClassesResponseV1();
    //                 classResponse.setUri(classNode.path("id").asText());
    //                 classResponse.setName(classNode.path("className").asText());
    //                 classList.add(classResponse);
    //             }
    //         }
    //     } catch (Exception e) {
    //         // Handle parsing exceptions
    //         logger.error("Failed to parse class response", e);
    //     }
    //     return classList;
    // }
    // private List<ClassesResponseV1> combineClassesResponse(List<ClassesResponseV1> classList) {
    //     if (classList == null || classList.isEmpty()) {
    //         logger.warn("No classes found");
    //         return Collections.emptyList(); // returns empty list if nothing found -> null body case
    //     }
    //     return classList; // returns the full list of classes
    // }

    // =====================================================================================================================
    // getProperties
    // public PropertiesResponseV1 getProperties(String id, String bearerToken) {
    //     String query = "{ findProperties(input:{pageSize:10000}) { properties:nodes { uri:id name version:versionId } propertiesTotalCount:totalElements } }";
    //     String response = executeQuery(query, bearerToken);
    //     return deserializeResponse(response, "findProperties", PropertiesResponseV1.class);
    // }

    // =====================================================================================================================
    // getStatistics
    public StatisticsResponseV1 getStatistics() {
        String query = "{ statistics { catalogueItem:items { id count } } }";
        String response = executeQuery(query, null);
        return deserializeResponse(response, "statistics", StatisticsResponseV1.class);
    }   

    // add new endpoints here

}