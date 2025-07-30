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

        // STEP 1: Execute the query to fetch the response
        String query = GraphQLDictionary.getDictionaryByIdQuery(id, queryLimit);
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

        } else {
            log.warn("Dictionaries are not included or are null for the ID: {}", id);
        }

        return dictionaryResponse;

    }

    // OPTION 2: query to fetch all dictionaries
    public DictionaryResponseContractV1 getAllDictionaries(String bearerToken, int queryOffset, int queryLimit) {
        int pageSize = queryOffset + queryLimit; // Calculate pageSize as the sum of offset and limit

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

            // Check if the offset is higher than the actual count of elements
            if (queryOffset >= dictionaryItems.size()) {
                log.warn("Query offset {} is higher than the number of dictionary items {}", queryOffset,
                        dictionaryItems.size());
                return null;
            }

            // Skip the number of elements specified by queryOffset and limit the results to
            // queryLimit
            int endIndex = Math.min(queryOffset + queryLimit, dictionaryItems.size());
            List<DictionaryContractV1> paginatedItems = dictionaryItems.subList(queryOffset, endIndex);
            dictionaryResponse.setDictionaries(paginatedItems);
        } else {
            log.warn("Dictionaries are not included");
        }

        return dictionaryResponse;
    }

    // =====================================================================================================================
    // ENDPOINT: /api/Dictionary/v1/Classes
    public DictionaryClassesResponseContractV1Classes getDictionaryClasses(String bearerToken, String id,
            int queryOffset, int queryLimit, int pageSize, String languageCode) {
        
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

        // Check pagination bounds
        if (queryOffset >= allClasses.size()) {
            log.warn("Query offset {} is higher than the number of class items {}", queryOffset, allClasses.size());
            return null;
        }

        // Apply pagination
        int totalClasses = allClasses.size();
        int endIndex = Math.min(queryOffset + queryLimit, totalClasses);
        List<ClassListItemContractV1Classes> paginatedClasses = allClasses.subList(queryOffset, endIndex);

        // Update response with paginated data
        dictionaryResponse.setClasses(paginatedClasses);
        dictionaryResponse.setClassesTotalCount(totalClasses);
        dictionaryResponse.setClassesOffset(queryOffset);
        dictionaryResponse.setClassesCount(paginatedClasses.size());

        return dictionaryResponse;
    }
}
