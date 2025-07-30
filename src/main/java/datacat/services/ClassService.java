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
        // log.info("INPUT PARAMETERS:");
        // log.info("QUERY OFFSET: {}", queryOffset);
        // log.info("QUERY LIMIT: {}", queryLimit);
        // log.info("PAGE SIZE: {}", pageSize);

        // STEP 1: Execute the query to fetch the response
        String query = GraphQLClass.getClassPropertiesQuery(id, pageSize, languageCode);
        String response = baseApiService.executeQuery(query, bearerToken);

        // STEP 2: Deserialize the outer fields of the response
        String rootField = "findSubjects";

        ClassPropertiesContractV1 classProperties = baseApiService.deserializeOuterFindResponse(response, rootField,
                ClassPropertiesContractV1.class);
        log.debug("Deserialized Outer Fields of Class Properties Response: {}", classProperties);

        // STEP 3: Deserialize the inner fields (nodes array) of the response
        List<ClassPropertyContractV1> classPropertyItems = baseApiService.deserializeClassInnerFindResponse(response, rootField,
                ClassPropertyContractV1.class);
        log.debug("Deserialized Inner Fields (Nodes) of Class Properties Response: {}", classPropertyItems);

        // STEP 4: Combine the results
        if (classProperties != null) {
            classProperties.setClassProperties(classPropertyItems);

            // Extract and set classUri from the first node if available
            String extractedClassUri = baseApiService.getResponseDeserializer().extractClassUriFromFindResponse(response, rootField);
            if (extractedClassUri != null) {
                classProperties.setClassUri(extractedClassUri);
                log.debug("Extracted classUri from response: {}", extractedClassUri);
            }

            // Generate URIs for the classProperties object and individual properties
            classProperties.generateUri(baseApiService.getServerUrl());

            if (classProperties.getClassProperties() != null) {
                // Check if the offset is higher than the actual count of elements
                if (queryOffset >= classPropertyItems.size()) {
                    log.warn("Query offset {} is higher than the number of class property items {}", queryOffset,
                            classPropertyItems.size());
                    return null;
                }

                // Skip the number of elements specified by queryOffset and limit the results to
                // queryLimit
                int endIndex = Math.min(queryOffset + queryLimit, classPropertyItems.size());
                List<ClassPropertyContractV1> paginatedItems = classPropertyItems.subList(queryOffset, endIndex);
                log.debug("Total Class Property Items: {}", classPropertyItems.size());
                log.debug("SubList from {} to {}", queryOffset, endIndex);
                for (ClassPropertyContractV1 property : paginatedItems) {
                    property.generateUri(baseApiService.getServerUrl());
                    property.transformToLowerCase();
                }
                classProperties.setClassProperties(paginatedItems);
            } else {
                log.warn("Class properties are not included or are null for class ID: {}", id);
            }
        }

        return classProperties;
    }
}
