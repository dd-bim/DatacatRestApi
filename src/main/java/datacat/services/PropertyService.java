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
import datacat.graphql.GraphQLProperty;

// =====================================================================================================================
// P R O P E R T Y   S E R V I C E   S E C T I O N
// Service for Property-related endpoints
// =====================================================================================================================
@Service
@Slf4j
public class PropertyService {

    private final BaseApiService baseApiService;

    public PropertyService(BaseApiService baseApiService) {
        this.baseApiService = baseApiService;
    }

    // =====================================================================================================================
    // PROPERTY ENDPOINTS
    // =====================================================================================================================
    
    // ENDPOINT: /api/Property/v5
    public PropertyContractV5 getPropertyDetails(String bearerToken, String id, String languageCode) {
        String response = baseApiService.executeQuery(
            GraphQLProperty.getPropertyDetailsQuery(id, languageCode), 
            bearerToken
        );

        PropertyContractV5 propertyDetails = baseApiService.deserializeGetResponse(response, "getProperty", PropertyContractV5.class);
        log.debug("Deserialized Property Details Response: {}", propertyDetails);

        if (propertyDetails != null) {
            propertyDetails.generateUri(baseApiService.getServerUrl());
            propertyDetails.transformToLowerCase();
        }

        return propertyDetails;
    }

    // =====================================================================================================================
    // ENDPOINT: /api/Property/Classes/v1
    public PropertyClassesContractV1 getPropertyClasses(String bearerToken, String ID, int queryOffset, int queryLimit,
            String languageCode) {

        // STEP 1: Execute the query to fetch the response
        String query = GraphQLProperty.getPropertyClassesQuery(ID, queryOffset, queryLimit, languageCode);
        String response = baseApiService.executeQuery(query, bearerToken);

        // STEP 2: Deserialize the outer fields of the response
        String rootField = "findProperties";

        PropertyClassesContractV1 propertyClasses = baseApiService.deserializeOuterFindResponse(response, rootField,
                PropertyClassesContractV1.class);
        log.debug("Deserialized Outer Fields of Property Classes Response: {}", propertyClasses);

        // STEP 3: Deserialize the inner fields (nodes array) of the response
        List<PropertyClassItemContractV1> propertyClassItems = baseApiService.deserializePropertyClassesInnerFindResponse(response, rootField,
                PropertyClassItemContractV1.class);
        log.debug("Deserialized Inner Fields (Nodes) of Property Classes Response: {}", propertyClassItems);

        // STEP 4: Combine the results
        if (propertyClasses != null) {
            propertyClasses.setPropertyClasses(propertyClassItems);

            // Extract and set propertyUri from the first node if available (similar to ClassService)
            String extractedPropertyUri = baseApiService.getResponseDeserializer().extractPropertyUriFromFindResponse(response, rootField);
            if (extractedPropertyUri != null) {
                propertyClasses.setPropertyUri(extractedPropertyUri);
                log.debug("Extracted propertyUri from response: {}", extractedPropertyUri);
            }

            propertyClasses.generateUri(baseApiService.getServerUrl());

            if (propertyClasses.getPropertyClasses() != null) {
                for (PropertyClassItemContractV1 propertyClass : propertyClasses.getPropertyClasses()) {
                    propertyClass.generateUri(baseApiService.getServerUrl());
                }
            } else {
                log.warn("Property classes are not included or are null for property ID: {}", ID);
            }
        }

        return propertyClasses;
    }
}
