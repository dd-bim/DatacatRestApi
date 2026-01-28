package datacat.services;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
import org.springframework.stereotype.Service;

// Java
import java.util.List;

// Logging
import lombok.extern.slf4j.Slf4j;

// Internal
import datacat.models.*;
import datacat.graphql.GraphQLClass;

// =====================================================================================================================
// C L A S S   S E R V I C E   S E C T I O N
// Service for Class-related endpoints
// =====================================================================================================================
@Service
@Slf4j
public class ClassService {

    private final BaseApiService baseApiService;

    public ClassService(BaseApiService baseApiService) {
        this.baseApiService = baseApiService;
    }

    // =====================================================================================================================
    // CLASS ENDPOINTS
    // =====================================================================================================================
    
    // ENDPOINT: /api/Class/v1
    public ClassContractV1 getClassDetails(String bearerToken, String id, boolean includeProperties,
            String languageCode) {

        String query = GraphQLClass.getClassDetailsQuery(id, includeProperties, languageCode);
        String response = baseApiService.executeQuery(query, bearerToken);
        String rootField = "getSubject";

        ClassContractV1 classDetails = baseApiService.deserializeGetResponse(response, rootField, ClassContractV1.class);
        log.debug("Deserialized Class Details Response: {}", classDetails);
        // Dictionary URI wird jetzt wieder im Deserializer extrahiert, aber aus einfacher dictionary Struktur

        if (classDetails != null) {
            classDetails.generateUri(baseApiService.getServerUrl());
            classDetails.transformToLowerCase();

            if (includeProperties && classDetails.getClassProperties() != null) {
                for (ClassPropertyContractV1 property : classDetails.getClassProperties()) {
                    property.generateUri(baseApiService.getServerUrl());
                    property.transformToLowerCase();
                }
            } else {
                log.warn("Class properties are not included or are null for class ID: {}", id);
            }
        }

        return classDetails;
    }

    // =====================================================================================================================
    // ENDPOINT: /api/Class/Properties/v1
    public ClassPropertiesContractV1 classPropertiesGet(String bearerToken, String id, int queryOffset, int queryLimit,
            int pageSize, String languageCode) {
        
        // Apply default limit rules
        int effectiveLimit = baseApiService.calculateEffectiveLimit(queryOffset, queryLimit);
        log.debug("Effective limit calculated: {}", effectiveLimit);

        // STEP 1: Execute the query to fetch the response
        log.debug("About to execute query for class properties");
        String query = GraphQLClass.getClassPropertiesQuery(id, pageSize, languageCode);
        String response = baseApiService.executeQuery(query, bearerToken);
        log.debug("Query executed successfully, response length: {}", response != null ? response.length() : "null");

        // STEP 2: Deserialize the outer fields of the response
        String rootField = "findSubjects";
        log.debug("About to deserialize outer fields for rootField: {}", rootField);

        ClassPropertiesContractV1 classProperties = baseApiService.deserializeOuterFindResponse(response, rootField,
                ClassPropertiesContractV1.class);
        log.debug("Deserialized Outer Fields of Class Properties Response: {}", classProperties);

        // STEP 3: Deserialize the inner fields (nodes array) of the response
        List<ClassPropertyContractV1> classPropertyItems = baseApiService.deserializeClassInnerFindResponse(response, rootField,
                ClassPropertyContractV1.class);
        log.debug("Deserialized Inner Fields (Nodes) of Class Properties Response: {}", classPropertyItems);

        // STEP 4: Combine the results
        if (classProperties != null) {
            log.debug("Setting class properties to classProperties object");
            classProperties.setClassProperties(classPropertyItems);

            // Extract and set classUri from the first node if available
            String extractedClassUri = baseApiService.getResponseDeserializer().extractClassUriFromFindResponse(response, rootField);
            if (extractedClassUri != null) {
                classProperties.setClassUri(extractedClassUri);
                log.debug("Extracted classUri from response: {}", extractedClassUri);
            }

            // Generate URIs for the classProperties object and individual properties
            log.debug("About to generate URI for classProperties");
            try {
                classProperties.generateUri(baseApiService.getServerUrl());
                log.debug("Successfully generated URI for classProperties");
            } catch (Exception e) {
                log.error("Error generating URI for classProperties", e);
                throw e;
            }

            if (classProperties.getClassProperties() != null) {
                log.debug("Class properties list is not null, starting pagination");
                // Validate offset bounds (allow empty results for offset >= totalElements)
                boolean allowEmptyResult = true; // Set to false if you want the old error behavior
                baseApiService.validateOffsetBounds(queryOffset, classPropertyItems.size(), "class property", allowEmptyResult);

                int totalElements = classPropertyItems.size();
                
                // Calculate actual count and handle pagination
                int actualCount;
                List<ClassPropertyContractV1> paginatedItems;
                
                if (queryOffset >= totalElements) {
                    // Return empty list but with correct metadata
                    paginatedItems = List.of(); // Empty list
                    actualCount = 0;
                } else {
                    // Normal pagination
                    log.debug("Starting normal pagination");
                    actualCount = baseApiService.calculateActualCount(queryOffset, effectiveLimit, totalElements);
                    int endIndex = Math.min(queryOffset + actualCount, totalElements);
                    paginatedItems = classPropertyItems.subList(queryOffset, endIndex);
                    
                    log.debug("Generating URIs for {} properties", paginatedItems.size());
                    for (ClassPropertyContractV1 property : paginatedItems) {
                        try {
                            property.generateUri(baseApiService.getServerUrl());
                            property.transformToLowerCase();
                        } catch (Exception e) {
                            log.error("Error generating URI for property: {}", property.getPropertyCode(), e);
                            throw e;
                        }
                    }
                    log.debug("Finished generating URIs for properties");
                }
                
                log.debug("Total Class Property Items: {}", totalElements);
                log.debug("SubList from {} to {}", queryOffset, Math.min(queryOffset + actualCount, totalElements));
                
                log.debug("Setting paginated properties");
                classProperties.setClassProperties(paginatedItems);
                
                // Update response with correct offset and count values
                classProperties.setOffset(queryOffset);
                classProperties.setCount(actualCount);
                classProperties.setTotalCount(totalElements);
                log.debug("Finished setting metadata. Returning classProperties");
            } else {
                log.warn("Class properties are not included or are null for class ID: {}", id);
            }
        } else {
            log.error("classProperties object is null after deserialization");
        }

        log.debug("Returning classProperties: {}", classProperties != null ? "not null" : "null");
        return classProperties;
    }
}
