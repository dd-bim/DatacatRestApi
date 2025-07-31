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
import datacat.graphql.GraphQLDictionary;

// =====================================================================================================================
// D I C T I O N A R Y   S E R V I C E   S E C T I O N
// Service for Dictionary-related endpoints
// =====================================================================================================================
@Service
@Slf4j
public class DictionaryService {

    private final BaseApiService baseApiService;

    public DictionaryService(BaseApiService baseApiService) {
        this.baseApiService = baseApiService;
    }

    // =====================================================================================================================
    // DICTIONARY ENDPOINTS
    // =====================================================================================================================
    
    // ENDPOINT: /api/Dictionary/v1
    // OPTION 1: query to fetch dictionary by ID
    public DictionaryResponseContractV1 getDictionaryById(String bearerToken, String id, int queryOffset,
            int queryLimit) {

        // Apply default limit rules
        int effectiveLimit = baseApiService.calculateEffectiveLimit(queryOffset, queryLimit);

        // STEP 1: Execute the query to fetch the response
        String query = GraphQLDictionary.getDictionaryByIdQuery(id, effectiveLimit);
        String response = baseApiService.executeQuery(query, bearerToken);

        // STEP 2: Deserialize the outer fields of the response
        String rootField = "findDictionaries";

        DictionaryResponseContractV1 dictionaryResponse = baseApiService.deserializeOuterFindResponse(response, rootField,
                DictionaryResponseContractV1.class);
        log.debug("Deserialized Dictionary By Id Response: {}", dictionaryResponse);

        // STEP 3: Deserialize the inner fields (nodes array) of the response
        List<DictionaryContractV1> dictionaryItems = baseApiService.deserializeDictionaryInnerFindResponse(response, rootField,
                DictionaryContractV1.class);
        log.debug("Deserialized Inner Fields (Nodes) of Dictionary Response: {}", dictionaryItems);

        // STEP 4: Combine the results
        if (dictionaryResponse != null) {
            dictionaryResponse.setDictionaries(dictionaryItems);

            for (DictionaryContractV1 dictionary : dictionaryResponse.getDictionaries()) {
                dictionary.generateUri(baseApiService.getServerUrl());
                dictionary.transformToLowerCase();
            }

            // Update response with correct offset and count values
            int totalElements = dictionaryItems != null ? dictionaryItems.size() : 0;
            
            // Validate offset bounds (allow empty results for offset >= totalElements)
            boolean allowEmptyResult = true; // Set to false if you want the old error behavior
            baseApiService.validateOffsetBounds(queryOffset, totalElements, "dictionary", allowEmptyResult);
            
            // Calculate actual count
            int actualCount;
            if (queryOffset >= totalElements) {
                // Return empty result but with correct metadata
                actualCount = 0;
                dictionaryResponse.setDictionaries(List.of()); // Empty list
            } else {
                actualCount = baseApiService.calculateActualCount(queryOffset, effectiveLimit, totalElements);
                // Keep the existing dictionaries list (no additional pagination needed for getDictionaryById)
            }
            
            dictionaryResponse.setOffset(queryOffset);
            dictionaryResponse.setCount(actualCount);
            dictionaryResponse.setTotalCount(totalElements);

        } else {
            log.warn("Dictionaries are not included or are null for the ID: {}", id);
        }

        return dictionaryResponse;

    }

    // OPTION 2: query to fetch all dictionaries
    public DictionaryResponseContractV1 getAllDictionaries(String bearerToken, int queryOffset, int queryLimit) {
        // Apply default limit rules
        int effectiveLimit = baseApiService.calculateEffectiveLimit(queryOffset, queryLimit);

        int pageSize = queryOffset + effectiveLimit; // Calculate pageSize as the sum of offset and limit

        // STEP 1: Execute the query to fetch the response
        String query = GraphQLDictionary.getAllDictionariesQuery(pageSize);
        String response = baseApiService.executeQuery(query, bearerToken);

        // STEP 2: Deserialize the outer fields of the response
        String rootField = "findDictionaries";

        DictionaryResponseContractV1 dictionaryResponse = baseApiService.deserializeOuterFindResponse(response, rootField,
                DictionaryResponseContractV1.class);
        log.debug("Deserialized Dictionary By Id Response: {}", dictionaryResponse);

        // STEP 3: Deserialize the inner fields (nodes array) of the response
        List<DictionaryContractV1> dictionaryItems = baseApiService.deserializeDictionaryInnerFindResponse(response, rootField,
                DictionaryContractV1.class);
        log.debug("Deserialized Inner Fields (Nodes) of Dictionary Response: {}", dictionaryItems);

        // STEP 4: Combine the results
        if (dictionaryResponse != null) {
            dictionaryResponse.setDictionaries(dictionaryItems);

            for (DictionaryContractV1 dictionary : dictionaryResponse.getDictionaries()) {
                dictionary.generateUri(baseApiService.getServerUrl());
                dictionary.transformToLowerCase();
            }

            // Validate offset bounds (allow empty results for offset >= totalElements)
            boolean allowEmptyResult = true; // Set to false if you want the old error behavior
            baseApiService.validateOffsetBounds(queryOffset, dictionaryItems.size(), "dictionary", allowEmptyResult);

            int totalElements = dictionaryItems.size();
            
            // Calculate actual count
            int actualCount = baseApiService.calculateActualCount(queryOffset, effectiveLimit, totalElements);

            // Handle case where offset >= totalElements
            List<DictionaryContractV1> paginatedItems;
            if (queryOffset >= totalElements) {
                // Return empty list but with correct metadata
                paginatedItems = List.of(); // Empty list
                actualCount = 0;
            } else {
                // Normal pagination
                int endIndex = Math.min(queryOffset + actualCount, totalElements);
                paginatedItems = dictionaryItems.subList(queryOffset, endIndex);
            }
            
            dictionaryResponse.setDictionaries(paginatedItems);
            
            // Update response with correct offset and count values
            dictionaryResponse.setOffset(queryOffset);
            dictionaryResponse.setCount(actualCount);
            dictionaryResponse.setTotalCount(totalElements);
        } else {
            log.warn("Dictionaries are not included");
        }

        return dictionaryResponse;
    }

    // =====================================================================================================================
    // ENDPOINT: /api/Dictionary/v1/Classes
    public DictionaryClassesResponseContractV1Classes getDictionaryClasses(String bearerToken, String id,
            int queryOffset, int queryLimit, int pageSize, String languageCode) {
        
        // Apply default limit rules
        int effectiveLimit = baseApiService.calculateEffectiveLimit(queryOffset, queryLimit);
        
        String response = baseApiService.executeQuery(
            GraphQLDictionary.getDictionaryGroupQuery(id, languageCode), 
            bearerToken
        );

        DictionaryClassesResponseContractV1Classes dictionaryResponse = baseApiService.deserializeGetResponse(response,
                "getDictionary", DictionaryClassesResponseContractV1Classes.class);
        log.debug("Deserialized Dictionary Classes Response: {}", dictionaryResponse);

        if (dictionaryResponse == null) {
            return null;
        }

        // Apply business logic to dictionary fields
        dictionaryResponse.generateUri(baseApiService.getServerUrl());
        dictionaryResponse.transformToLowerCase();

        List<ClassListItemContractV1Classes> allClasses = dictionaryResponse.getClasses();
        if (allClasses == null || allClasses.isEmpty()) {
            return dictionaryResponse;
        }

        // Apply URI generation and transformation to all classes
        allClasses.forEach(classItem -> {
            classItem.generateUri(baseApiService.getServerUrl());
            classItem.transformToLowerCase();
        });

        // Validate offset bounds (allow empty results for offset >= totalElements)
        boolean allowEmptyResult = true; // Set to false if you want the old error behavior
        baseApiService.validateOffsetBounds(queryOffset, allClasses.size(), "class", allowEmptyResult);

        int totalElements = allClasses.size();
        
        // Calculate actual count and handle pagination
        int actualCount;
        List<ClassListItemContractV1Classes> paginatedClasses;
        
        if (queryOffset >= totalElements) {
            // Return empty list but with correct metadata
            paginatedClasses = List.of(); // Empty list
            actualCount = 0;
        } else {
            // Normal pagination
            actualCount = baseApiService.calculateActualCount(queryOffset, effectiveLimit, totalElements);
            int endIndex = Math.min(queryOffset + actualCount, totalElements);
            paginatedClasses = allClasses.subList(queryOffset, endIndex);
        }

        // Update response with paginated data
        dictionaryResponse.setClasses(paginatedClasses);
        dictionaryResponse.setClassesTotalCount(totalElements);
        dictionaryResponse.setClassesOffset(queryOffset);
        dictionaryResponse.setClassesCount(actualCount);

        return dictionaryResponse;
    }
}
