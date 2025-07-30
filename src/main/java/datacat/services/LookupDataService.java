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
import datacat.graphql.GraphQLLookupData;

// =====================================================================================================================
// L O O K U P   D A T A   S E R V I C E   S E C T I O N
// Service for Lookup Data-related endpoints
// =====================================================================================================================
@Service
@Slf4j
public class LookupDataService {

    private final BaseApiService baseApiService;

    public LookupDataService(BaseApiService baseApiService) {
        this.baseApiService = baseApiService;
    }

    // =====================================================================================================================
    // LOOKUP DATA ENDPOINTS
    // =====================================================================================================================
    
    // ENDPOINT: /api/ReferenceDocument/v1
    public List<ReferenceDocumentContractV1> getReferenceDocuments(String bearerToken) {

        String query = GraphQLLookupData.getReferenceDocumentsQuery();
        String response = baseApiService.executeQuery(query, bearerToken);
        String rootField = "findExternalDocuments";

        List<ReferenceDocumentContractV1> refDocs = baseApiService.deserializeGeneralInnerFindResponse(response, rootField,
                ReferenceDocumentContractV1.class);
        log.debug("Deserialized Reference Document Response: {}", refDocs);

        return refDocs;
    }

    // =====================================================================================================================
    // ENDPOINT: /api/Language/v1
    public List<LanguageContractV1> getLanguages(String bearerToken) {

        String query = GraphQLLookupData.getLanguagesQuery();
        String response = baseApiService.executeQuery(query, bearerToken);
        String rootField = "findLanguages";

        List<LanguageContractV1> languages = baseApiService.deserializeGeneralInnerFindResponse(response, rootField,
                LanguageContractV1.class);
        log.debug("Deserialized Languages Response: {}", languages);

        return languages;
    }

    // =====================================================================================================================
    // ENDPOINT: /api/Country/v1
    public List<CountryContractV1> getCountries(String bearerToken) {

        String query = GraphQLLookupData.getCountriesQuery();
        String response = baseApiService.executeQuery(query, bearerToken);
        String rootField = "findCountries";

        List<CountryContractV1> countries = baseApiService.deserializeGeneralInnerFindResponse(response, rootField,
                CountryContractV1.class);
        log.debug("Deserialized Countries Response: {}", countries);

        return countries;
    }
}
