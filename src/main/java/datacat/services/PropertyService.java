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

        // Apply default limit rules
        int effectiveLimit = baseApiService.calculateEffectiveLimit(queryOffset, queryLimit);

        // For consistency with other services, let's fetch more data and paginate client-side
        // Use a larger pageSize to ensure we get enough data for client-side pagination
        int pageSize = queryOffset + effectiveLimit * 2; // Fetch more to ensure we have enough data
        
        // STEP 1: Execute the query to fetch the response (use 0 offset to get all data up to pageSize)
        String query = GraphQLProperty.getPropertyClassesQuery(ID, 0, pageSize, languageCode);
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

            if (propertyClassItems != null && !propertyClassItems.isEmpty()) {
                int totalElements = propertyClassItems.size();
                
                // Validate offset bounds (allow empty results for offset >= totalElements)
                boolean allowEmptyResult = true;
                baseApiService.validateOffsetBounds(queryOffset, totalElements, "property class", allowEmptyResult);
                
                // Apply client-side pagination
                List<PropertyClassItemContractV1> paginatedItems;
                int actualCount;
                
                if (queryOffset >= totalElements) {
                    // Return empty list but with correct metadata
                    paginatedItems = List.of();
                    actualCount = 0;
                } else {
                    // Normal client-side pagination
                    actualCount = baseApiService.calculateActualCount(queryOffset, effectiveLimit, totalElements);
                    int endIndex = Math.min(queryOffset + actualCount, totalElements);
                    paginatedItems = propertyClassItems.subList(queryOffset, endIndex);
                }
                
                // Generate URIs for the paginated items
                for (PropertyClassItemContractV1 propertyClass : paginatedItems) {
                    propertyClass.generateUri(baseApiService.getServerUrl());
                }
                
                // Update the response with paginated data
                propertyClasses.setPropertyClasses(paginatedItems);
                
                // Set response metadata
                propertyClasses.setOffset(queryOffset);
                propertyClasses.setCount(actualCount);
                propertyClasses.setTotalCount(totalElements);
                
                log.debug("Applied client-side pagination: offset={}, limit={}, totalElements={}, actualCount={}", 
                    queryOffset, effectiveLimit, totalElements, actualCount);
            } else {
                // Handle empty result case
                propertyClasses.setPropertyClasses(List.of());
                propertyClasses.setOffset(queryOffset);
                propertyClasses.setCount(0);
                propertyClasses.setTotalCount(0);
                log.warn("Property classes are empty for property ID: {}", ID);
            }
        }

        return propertyClasses;
    }
}
