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

// Internal
import datacat.models.*;
import datacat.customization.CustomProperties;
import datacat.graphql.GraphQLClass;
import datacat.graphql.GraphQLDatacatSpecifics;
import datacat.graphql.GraphQLDictionary;
import datacat.graphql.GraphQLLookupData;
import datacat.graphql.GraphQLProperty;
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
    private final IdExtractor idExtractor; // no idea why marked as not in use

    public ApiService(CustomProperties customProperties, RestTemplateBuilder restTemplateBuilder, ResponseDeserializer responseDeserializer, IdExtractor idExtractor) {
        this.restTemplate = restTemplateBuilder.build();
        this.customProperties = customProperties;
        this.responseDeserializer = responseDeserializer;
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
    // pointing to deserialization logic
    private <T> T deserializeGetResponse(String response, String rootField, Class<T> modelType) {
        return responseDeserializer.deserializeGetResponse(response, rootField, modelType);
    }

    private <T> T deserializeOuterFindResponse(String response, String rootField, Class<T> modelType) {
        return responseDeserializer.deserializeOuterFindResponse(response, rootField, modelType);
    }

    private <T> List<T> deserializeGeneralInnerFindResponse(String response, String rootField, Class<T> modelType) {
        return responseDeserializer.deserializeGeneralInnerFindResponse(response, rootField, modelType);
    }

    private <T> List<T> deserializeDictionaryInnerFindResponse(String response, String rootField, Class<T> modelType) {
        return responseDeserializer.deserializeDictionaryInnerFindResponse(response, rootField, modelType);
    }

    private <T> List<T> deserializeClassInnerFindResponse(String response, String rootField, Class<T> modelType) {
        return responseDeserializer.deserializeClassInnerFindResponse(response, rootField, modelType);
    }

    private <T> List<T> deserializeResponseAsList(String response, String rootField, Class<T> modelType) {
        return responseDeserializer.deserializeResponseAsList(response, rootField, modelType);
    }

    private List<String> extractGroupIdsFromResponse(String groupsResponse) {
        return idExtractor.extractGroupIdsFromResponse(groupsResponse);
    }

    
    // =====================================================================================================================
    // E N D P O I N T   L O G I C
    // specified endpoint logic for each query
    // newly added endpoints should have at least one processing method here
    // rootField should match the field in the response (e.g. "getSubject", "getBag", "findSubjects", "findProperties", etc.)
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

            if (includeProperties && classDetails.getClassProperties() != null) {
                for (ClassPropertyContractV1 property : classDetails.getClassProperties()) {
                    property.generateUri(serverUrl);
                    property.transformToLowerCase();
                }
            } else {
                logger.warn("Class properties are not included or are null for class ID: {}", id);
            }
        }

        return classDetails;
    }


    // =====================================================================================================================
    // ENDPOINT: /api/Class/Relations/v1

    // =====================================================================================================================
    // ENDPOINT: /api/Class/Properties/v1
    // DONE
    public ClassPropertiesContractV1 classPropertiesGet(String bearerToken, String id, int queryOffset, int queryLimit, int pageSize, String languageCode) {
        // logger.info("INPUT PARAMETERS:");
        // logger.info("QUERY OFFSET: {}", queryOffset);
        // logger.info("QUERY LIMIT: {}", queryLimit);
        // logger.info("PAGE SIZE: {}", pageSize);
    
        // STEP 1: Execute the query to fetch the response
        String query = GraphQLClass.getClassPropertiesQuery(id, pageSize, languageCode);
        String response = executeQuery(query, bearerToken);
    
        // STEP 2: Deserialize the outer fields of the response
        String rootField = "findSubjects";
    
        ClassPropertiesContractV1 classProperties = deserializeOuterFindResponse(response, rootField, ClassPropertiesContractV1.class);
        logger.debug("Deserialized Outer Fields of Class Properties Response: {}", classProperties);
    
        // STEP 3: Deserialize the inner fields (nodes array) of the response
        List<ClassPropertyItemContractV1> classPropertyItems = deserializeClassInnerFindResponse(response, rootField, ClassPropertyItemContractV1.class);
        logger.debug("Deserialized Inner Fields (Nodes) of Class Properties Response: {}", classPropertyItems);
    
        // STEP 4: Combine the results
        if (classProperties != null) {
            classProperties.setClassProperties(classPropertyItems);
    
            String serverUrl = customProperties.getServerUrl();
            classProperties.generateUri(serverUrl);
    
            if (classProperties.getClassProperties() != null) {
                // Check if the offset is higher than the actual count of elements
                if (queryOffset >= classPropertyItems.size()) {
                    logger.warn("Query offset {} is higher than the number of class property items {}", queryOffset, classPropertyItems.size());
                    return null;
                }

                // Skip the number of elements specified by queryOffset and limit the results to queryLimit
                int endIndex = Math.min(queryOffset + queryLimit, classPropertyItems.size());
                List<ClassPropertyItemContractV1> paginatedItems = classPropertyItems.subList(queryOffset, endIndex);
                logger.debug("Total Class Property Items: {}", classPropertyItems.size());
                logger.debug("SubList from {} to {}", queryOffset, endIndex);
                for (ClassPropertyItemContractV1 property : paginatedItems) {
                    property.generateUri(serverUrl);
                    property.transformToLowerCase();
                }
                classProperties.setClassProperties(paginatedItems);
            } else {
                logger.warn("Class properties are not included or are null for class ID: {}", id);
            }
        }
    
        return classProperties;
    }


    // =====================================================================================================================
    // SECTION: DICTIONARY
    // =====================================================================================================================
    // ENDPOINT: /api/Dictionary/v1
    // DONE
    // OPTION 1: query to fetch  dictionary by ID
    public DictionaryResponseContractV1 getDictionaryById(String bearerToken, String id, int queryOffset, int queryLimit) {
        
        // STEP 1: Execute the query to fetch the response
        String query = GraphQLDictionary.getDictionaryByIdQuery(id, queryLimit);
        String response = executeQuery(query, bearerToken);
    
        // STEP 2: Deserialize the outer fields of the response
        String rootField = "findBags";
    
        DictionaryResponseContractV1 dictionaryResponse = deserializeOuterFindResponse(response, rootField, DictionaryResponseContractV1.class);
        logger.debug("Deserialized Dictionary By Id Response: {}", dictionaryResponse);
    
        // STEP 3: Deserialize the inner fields (nodes array) of the response
        List<DictionaryContractV1> dictionaryItems = deserializeDictionaryInnerFindResponse(response, rootField, DictionaryContractV1.class);
        logger.debug("Deserialized Inner Fields (Nodes) of Dictionary Response: {}", dictionaryItems);
    
        // STEP 4: Combine the results
        if (dictionaryResponse != null) {
            dictionaryResponse.setDictionaries(dictionaryItems);

            String serverUrl = customProperties.getServerUrl();
            for (DictionaryContractV1 dictionary : dictionaryResponse.getDictionaries()) {
                dictionary.generateUri(serverUrl);
                dictionary.transformToLowerCase();
            }

        } else {
            logger.warn("Dictionaries are not included or are null for the ID: {}", id);
        }

        return dictionaryResponse;
        
    }

    // OPTION 2: query to fetch all dictionaries
    public DictionaryResponseContractV1 getAllDictionaries(String bearerToken, int queryOffset, int queryLimit) {
        int pageSize = queryOffset + queryLimit; // Calculate pageSize as the sum of offset and limit
    
        // STEP 1: Execute the query to fetch the response
        String query = GraphQLDictionary.getAllDictionariesQuery(pageSize);
        String response = executeQuery(query, bearerToken);
    
        // STEP 2: Deserialize the outer fields of the response
        String rootField = "findBags";
    
        DictionaryResponseContractV1 dictionaryResponse = deserializeOuterFindResponse(response, rootField, DictionaryResponseContractV1.class);
        logger.debug("Deserialized Dictionary By Id Response: {}", dictionaryResponse);
    
        // STEP 3: Deserialize the inner fields (nodes array) of the response
        List<DictionaryContractV1> dictionaryItems = deserializeDictionaryInnerFindResponse(response, rootField, DictionaryContractV1.class);
        logger.debug("Deserialized Inner Fields (Nodes) of Dictionary Response: {}", dictionaryItems);
    
        // STEP 4: Combine the results
        if (dictionaryResponse != null) {
            dictionaryResponse.setDictionaries(dictionaryItems);
    
            String serverUrl = customProperties.getServerUrl();
            for (DictionaryContractV1 dictionary : dictionaryResponse.getDictionaries()) {
                dictionary.generateUri(serverUrl);
                dictionary.transformToLowerCase();
            }

            // Check if the offset is higher than the actual count of elements
            if (queryOffset >= dictionaryItems.size()) {
                logger.warn("Query offset {} is higher than the number of dictionary items {}", queryOffset, dictionaryItems.size());
                return null;
            }
    
            // Skip the number of elements specified by queryOffset and limit the results to queryLimit
            int endIndex = Math.min(queryOffset + queryLimit, dictionaryItems.size());
            List<DictionaryContractV1> paginatedItems = dictionaryItems.subList(queryOffset, endIndex);
            dictionaryResponse.setDictionaries(paginatedItems);
        } else {
            logger.warn("Dictionaries are not included");
        }
    
        return dictionaryResponse;
    }

    // =====================================================================================================================
    // ENDPOINT: /api/Dictionary/v1/Properties
    

    // =====================================================================================================================
    // ENDPOINT: /api/Dictionary/v1/Classes
    public DictionaryClassesResponseContractV1Classes getDictionaryClasses(String bearerToken, String id, int queryOffset, int queryLimit, int pageSize, String languageCode) {
        // STEP 1: Execute the query to fetch the group IDs
        String dictGroupQuery = GraphQLDictionary.getDictionaryGroupQuery(id, languageCode);
        String dictGroupResponse = executeQuery(dictGroupQuery, bearerToken);
    
        // STEP 2: Deserialize the group IDs
        List<String> groupIds = extractGroupIdsFromResponse(dictGroupResponse);
        logger.debug("Deserialized Group IDs: {}", groupIds);
    
        List<ClassListItemContractV1Classes> allClasses = new ArrayList<>();
    
        // STEP 3: Fetch classes for each group
        for (String groupId : groupIds) {
            String classesQuery = GraphQLDictionary.getGroupClassesQuery(groupId, pageSize, languageCode);
            String classesResponse = executeQuery(classesQuery, bearerToken);
    
            List<ClassListItemContractV1Classes> classes = deserializeResponseAsList(classesResponse, "getBag", ClassListItemContractV1Classes.class);
    
            if (classes != null) {
                String serverUrl = customProperties.getServerUrl();
                for (ClassListItemContractV1Classes classItem : classes) {
                    classItem.generateUri(serverUrl);
                    classItem.transformToLowerCase();
                }
                allClasses.addAll(classes);
            }
        }
    
        // STEP 4: Check if the offset is higher than the actual count of elements
        if (queryOffset >= allClasses.size()) {
            logger.warn("Query offset {} is higher than the number of class items {}", queryOffset, allClasses.size());
            return null;
        }

        // STEP 5: Apply pagination logic
        int totalClasses = allClasses.size();
        int endIndex = Math.min(queryOffset + queryLimit, totalClasses);
        List<ClassListItemContractV1Classes> paginatedClasses = allClasses.subList(queryOffset, endIndex);
    
        // STEP 5: Create and return the response
        DictionaryClassesResponseContractV1Classes dictionaryResponse = new DictionaryClassesResponseContractV1Classes();
        dictionaryResponse.setClasses(paginatedClasses);
    
        String serverUrl = customProperties.getServerUrl();
        dictionaryResponse.generateUri(serverUrl);
        dictionaryResponse.transformToLowerCase();
    
        return dictionaryResponse;
    }

    // =====================================================================================================================
    // SECTION: POPULAR DICTIONARY
    // =====================================================================================================================
    // ENDPOINT: /api/Dictionary/Popular/v1


    // =====================================================================================================================
    // SECTION: PROPERTY
    // =====================================================================================================================
    // ENDPOINT: /api/Property/v4
    public PropertyContractV4 getPropertyDetails(String bearerToken, String id, boolean includeClasses, String languageCode) {

        String query = GraphQLProperty.getPropertyDetailsQuery(id, includeClasses, languageCode);
        String response = executeQuery(query, bearerToken);
        String rootField = "getProperty";

        PropertyContractV4 propertyDetails = deserializeGetResponse(response, rootField, PropertyContractV4.class);
        logger.debug("Deserialized Property Details Response: {}", propertyDetails);

        if (propertyDetails != null) {
            String serverUrl = customProperties.getServerUrl();
            propertyDetails.generateUri(serverUrl);
            propertyDetails.transformToLowerCase();

            if (includeClasses && propertyDetails.getPropertyClasses() != null) {
                for (PropertyClassContractV4 classItem : propertyDetails.getPropertyClasses()) {
                    classItem.generateUri(serverUrl);
                    classItem.transformToLowerCase();
                }
            } else {
                logger.warn("Classes are not included or are null for property ID: {}", id);
            }

        }

        return propertyDetails;
    }


    // =====================================================================================================================
    // ENDPOINT: /api/Property/Relations/v1
    // =====================================================================================================================
    // ENDPOINT: /api/Property/Classes/v1
    public PropertyClassesContractV1 getPropertyClasses(String bearerToken, String ID, int queryOffset, int queryLimit, String languageCode) {

        // STEP 1: Execute the query to fetch the response
        String query = GraphQLProperty.getPropertyClassesQuery(ID, queryOffset, queryLimit, languageCode);
        String response = executeQuery(query, bearerToken);


        // STEP 2: Deserialize the outer fields of the response
        String rootField = "findProperties";

        PropertyClassesContractV1 propertyClasses = deserializeOuterFindResponse(response, rootField, PropertyClassesContractV1.class);
        logger.debug("Deserialized Outer Fields of Property Classes Response: {}", propertyClasses);

        // STEP 3: Deserialize the inner fields (nodes array) of the response
        List<PropertyClassItemContractV1> propertyClassItems = deserializeClassInnerFindResponse(response, rootField, PropertyClassItemContractV1.class);
        logger.debug("Deserialized Inner Fields (Nodes) of Property Classes Response: {}", propertyClassItems);

        // STEP 4: Combine the results
        if (propertyClasses != null) {
            propertyClasses.setPropertyClasses(propertyClassItems);

            String serverUrl = customProperties.getServerUrl();
            propertyClasses.generateUri(serverUrl);

            if (propertyClasses.getPropertyClasses() != null) {
                for (PropertyClassItemContractV1 propertyClass : propertyClasses.getPropertyClasses()) {
                    propertyClass.generateUri(serverUrl);
                }
            } else {
                logger.warn("Property classes are not included or are null for property ID: {}", ID);
            }
        }

        return propertyClasses;
    }


    // =====================================================================================================================
    // ENDPOINT: /api/PropertyValue/v2


    // =====================================================================================================================
    // SECTION: SEARCH
    // =====================================================================================================================
    // ENDPOINT: /api/TextSearch/v2
    // =====================================================================================================================
    // ENDPOINT: /api/TextSearch/v1
    // =====================================================================================================================
    // ENDPOINT: /api/SearchInDictionary/v1
    // =====================================================================================================================
    // ENDPOINT: /api/Class/Search/v1


    // =====================================================================================================================
    // SECTION: LOOKUP DATA
    // =====================================================================================================================
    // ENDPOINT: /api/Unit/v1
    // public List<UnitContractV1> getUnits(String bearerToken) {

    //     String query = GraphQlLookupData.getUnitsQuery();
    //     String response = executeQuery(query, bearerToken);
    //     String rootField = "findUnits";

    //     List<UnitContractV1> units = deserializeInnerFindResponse(response, rootField, UnitContractV1.class);
    //     logger.debug("Deserialized Units Response: {}", units);

    //     return units;
    // }


    // =====================================================================================================================
    // ENDPOINT: /api/ReferenceDocument/v1
    // DONE
    public List<ReferenceDocumentContractV1> getReferenceDocuments(String bearerToken) {

        String query = GraphQLLookupData.getReferenceDocumentsQuery();
        String response = executeQuery(query, bearerToken);
        String rootField = "findDocuments";

        List<ReferenceDocumentContractV1> refDocs = deserializeGeneralInnerFindResponse(response, rootField, ReferenceDocumentContractV1.class);
        logger.debug("Deserialized Reference Document Response: {}", refDocs);

        return refDocs;
    }

    // =====================================================================================================================
    // ENDPOINT: /api/Language/v1
    // DONE
    public List<LanguageContractV1> getLanguages(String bearerToken) {

        String query = GraphQLLookupData.getLanguagesQuery();
        String response = executeQuery(query, bearerToken);
        String rootField = "languages";

        List<LanguageContractV1> languages = deserializeGeneralInnerFindResponse(response, rootField, LanguageContractV1.class);
        logger.debug("Deserialized Languages Response: {}", languages);

        return languages;
    }


    // =====================================================================================================================
    // ENDPOINT: /api/Country/v1
    // DONE
    public List<CountryContractV1> getCountries(String bearerToken) {

        String query = GraphQLLookupData.getCountriesQuery();
        String response = executeQuery(query, bearerToken);
        String rootField = "languages";

        List<CountryContractV1> countries = deserializeGeneralInnerFindResponse(response, rootField, CountryContractV1.class);
        logger.debug("Deserialized Countries Response: {}", countries);

        return countries;
    }

    // =====================================================================================================================
    // SECTION: DATACAT SPECIFICS
    // =====================================================================================================================
    // ENDPOINT: /api/Statistics
    // DONE
    public StatisticsResponseContractV1 getStatistics() {

        String query = GraphQLDatacatSpecifics.getStatisticsQuery();
        String response = executeQuery(query, null);
        String rootField = "statistics";

        StatisticsResponseContractV1 statistics = deserializeGetResponse(response, rootField, StatisticsResponseContractV1.class);
        logger.debug("Deserialized Statistics Response: {}", statistics);

        return statistics;
    }

}   