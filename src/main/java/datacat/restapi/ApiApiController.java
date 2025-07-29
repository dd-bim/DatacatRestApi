package datacat.restapi;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
// Spring Boot
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

// OpenAPI
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;

// Java 
import java.net.URISyntaxException;
import java.util.List;

// Lombok Logging
import lombok.extern.slf4j.Slf4j;

// Jakarta
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
// Internal
import datacat.models.*;
import datacat.auth.AuthenticationService;
import datacat.graphql.IdExtractor;

// =====================================================================================================================
// C O N T R O L L E R   S E C T I O N
// =====================================================================================================================
@Controller
@Slf4j
public class ApiApiController implements ApiApi {

    private final AuthenticationService authenticationService;
    private final ApiService apiService;

    public ApiApiController(@Lazy AuthenticationService authenticationService, @Lazy ApiService apiService) {
        this.authenticationService = authenticationService;
        this.apiService = apiService;
    }
    

    // =====================================================================================================================
    // SECTION: CLASS
    // =====================================================================================================================
    // ENDPOINT: /api/Class/v1
    // DONE
    @Override
    @Tag(name = "Class")
    public ResponseEntity<ClassContractV1> classGet(
        @NotNull @Parameter(name = "Uri", description = "URI of the class, e.g.<br> https://datacat.org/class/41e55123-7bf4-4e2f-8c04-a713f3a087c6<br>", required = true, in = ParameterIn.QUERY) @Valid @RequestParam(value = "Uri", required = true) String uri,
        @Parameter(name = "IncludeClassProperties", description = "Use this option to include properties of the class. By default, it is set to true.<br>", in = ParameterIn.QUERY) @Valid @RequestParam(value = "IncludeClassProperties", required = false) Boolean includeClassProperties,
        @Parameter(name = "IncludeChildClassReferences", deprecated = true, description = "! This option is not compatible with datacat.org or any of its instances", in = ParameterIn.QUERY) @Valid @RequestParam(value = "IncludeChildClassReferences", required = false) @Deprecated Boolean includeChildClassReferences,
        @Parameter(name = "IncludeClassRelations", deprecated = true, description = "! This option is not compatible with datacat.org or any of its instances", in = ParameterIn.QUERY) @Valid @RequestParam(value = "IncludeClassRelations", required = false) @Deprecated Boolean includeClassRelations,
        @Parameter(name = "IncludeReverseRelations", deprecated = true, description = "! This option is not compatible with datacat.org or any of its instances", in = ParameterIn.QUERY) @Valid @RequestParam(value = "IncludeReverseRelations", required = false) @Deprecated Boolean includeReverseRelations,
        @Parameter(name = "ReverseRelationDictionaryUris", deprecated = true, description = "! This option is not compatible with datacat.org or any of its instances", in = ParameterIn.QUERY) @Valid @RequestParam(value = "ReverseRelationDictionaryUris", required = false) @Deprecated List<String> reverseRelationDictionaryUris,
        @Parameter(name = "languageCode", deprecated = true, description = "Specify language (case sensitive).<br> For those items the text is not available in the requested language, the text will be returned in the default language of the dictionary<br> ! This option is not yet implemented.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "languageCode", required = false) @Deprecated String languageCode
    ) {
        
        String ID;
        try {
            ID = IdExtractor.extractIdFromUri(uri, "/class/");
        } catch (URISyntaxException e) {
            log.error("Invalid URI format: {}", uri, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            log.error("Failed to extract ID from URI", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            HttpHeaders headers = authenticationService.getAuthorizationHeaders();
            String bearerToken = headers.getFirst("Authorization").substring(7);
            boolean includeProperties = includeClassProperties != null ? includeClassProperties : false; // default value is false
            String language = languageCode != null ? languageCode : "de"; // default value is "de" (german)

            ClassContractV1 classDetails = apiService.getClassDetails(bearerToken, ID, includeProperties, language);
            return new ResponseEntity<>(classDetails, HttpStatus.OK);

        } catch (HttpServerErrorException e) {
            log.error("Error executing query for getClassDetails", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error("Error fetching class details", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    // =====================================================================================================================
    // ENDPOINT: /api/Class/Relations/v1
    // @Override
    // @Tag(name = "Class")

    // =====================================================================================================================
    // ENDPOINT: /api/Class/Properties/v1
    @Override
    @Tag(name = "Class")
    public ResponseEntity<ClassPropertiesContractV1> classPropertiesGet(
        @NotNull @Parameter(name = "ClassUri", description = "URI of the class, e.g.<br>https://datacat.org/class/41e55123-7bf4-4e2f-8c04-a713f3a087c6<br>", required = true, in = ParameterIn.QUERY) @Valid @RequestParam(value = "ClassUri", required = true) String classUri,
        @Parameter(name = "PropertySet", deprecated = true, description = "'Optional: Property set to filter the properties'<br> ! This option is not yet implemented.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "PropertySet", required = false) @Deprecated String propertySet,
        @Parameter(name = "PropertyCode", deprecated = true, description = "'Optional: Property code to filter the properties'<br> ! This option is not yet implemented.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "PropertyCode", required = false) @Deprecated String propertyCode,
        @Parameter(name = "SearchText", deprecated = true, description = "\"Optional: Search text to filter the properties.<br> Search is done in the property name, property description and property code.<br> Cannot be used together with PropertySet or PropertyCode.\"<br> ! This option is not yet implemented.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "SearchText", required = false) @Deprecated String searchText,
        @Parameter(name = "Offset", description = "Zero-based offset of the first item to be returned. Default is 0.<br>", in = ParameterIn.QUERY) @Valid @RequestParam(value = "Offset", required = false) Integer offset,
        @Parameter(name = "Limit", description = "Limit number of items to be returned. The default and maximum number<br> of items returned is 1000. When Offset is specified, then the<br> default limit is 100.<br>", in = ParameterIn.QUERY) @Valid @RequestParam(value = "Limit", required = false) Integer limit,
        @Parameter(name = "languageCode", deprecated = true, description = "Specify language (case sensitive).<br> For those items the text is not available in the requested language, the text will be returned in the default language of the dictionary<br> ! This option is not yet implemented.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "languageCode", required = false) @Deprecated String languageCode
    ) {

        String ID;
        try {
            ID = IdExtractor.extractIdFromUri(classUri, "/class/");
            log.debug("Extracted ID from URI: {}", ID);
        } catch (URISyntaxException e) {
            log.error("Invalid URI format: {}", classUri, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            log.error("Failed to extract ID from URI", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            HttpHeaders headers = authenticationService.getAuthorizationHeaders();
            String bearerToken = headers.getFirst("Authorization").substring(7);
            int queryOffset = offset != null ? offset : 0; // default value is 0
            int queryLimit = limit != null ? limit : (queryOffset != 0 ? 100 : 1000); // default value is 1000, but 100 if offset is not 0
            int pageSize = queryOffset + queryLimit; // pageSize is the sum of offset and limit

            ClassPropertiesContractV1 response = apiService.classPropertiesGet(bearerToken, ID, queryOffset, queryLimit, pageSize, languageCode);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (HttpServerErrorException e) {
            log.error("Error executing query for classPropertiesGet", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error("Error fetching class properties", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }


    // =====================================================================================================================
    // SECTION: DICTIONARY
    // =====================================================================================================================
    // ENDPOINT: /api/Dictionary/v1
    // DONE
    @Override
    @Tag(name = "Dictionary")
    public ResponseEntity<DictionaryResponseContractV1> dictionaryGet(
        @Parameter(name = "Uri", description = "Optional filtering, URI of a specific dictionary, e.g. <br>https://datacat.org/model/34mDkKGrz2FhzL8laZhy9W<br>", in = ParameterIn.QUERY) @Valid @RequestParam(value = "Uri", required = false) String uri,
        @Parameter(name = "IncludeTestDictionaries", deprecated = true, description = "! This option is not compatible with datacat.org or any of its instances", in = ParameterIn.QUERY) @Valid @RequestParam(value = "IncludeTestDictionaries", required = false) @Deprecated Boolean includeTestDictionaries,
        @Parameter(name = "Offset", description = "Zero-based offset of the first item to be returned. Default is 0.<br>", in = ParameterIn.QUERY) @Valid @RequestParam(value = "Offset", required = false) Integer offset,
        @Parameter(name = "Limit", description = "Limit number of items to be returned. The default and maximum number<br> of items returned is 1000. When Offset is specified, then the<br> default limit is 100.<br>", in = ParameterIn.QUERY) @Valid @RequestParam(value = "Limit", required = false) Integer limit
    ) {

        try {
            HttpHeaders headers = authenticationService.getAuthorizationHeaders();
            String bearerToken = headers.getFirst("Authorization").substring(7);
            int queryOffset = offset != null ? offset : 0; // default value is 0
            int queryLimit = limit != null ? limit : (queryOffset != 0 ? 100 : 1000); // default value is 1000, but 100 if offset is not 0
            int pageSize = queryOffset + queryLimit; // pageSize is the sum of offset and limit
    
            if (uri != null) {
                String ID;
                try {
                    ID = IdExtractor.extractIdFromUri(uri, "/model/");
                } catch (URISyntaxException e) {
                    log.error("Invalid URI format: {}", uri, e);
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                } catch (IllegalArgumentException e) {
                    log.error("Failed to extract ID from URI", e);
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }

                // Set offset to 0 and limit to 1 when ID is used
                queryOffset = 0;
                queryLimit = 1;
                pageSize = queryOffset + queryLimit;
    
                DictionaryResponseContractV1 dictionaryResponse = apiService.getDictionaryById(bearerToken, ID, queryOffset, queryLimit);
                return new ResponseEntity<>(dictionaryResponse, HttpStatus.OK);
            } else {
                DictionaryResponseContractV1 dictionaryResponse = apiService.getAllDictionaries(bearerToken, queryOffset, queryLimit);
                return new ResponseEntity<>(dictionaryResponse, HttpStatus.OK);
            }
        } catch (HttpServerErrorException e) {
            log.error("Error executing query for dictionaryGet", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error("Error fetching dictionary", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    // =====================================================================================================================
    // ENDPOINT: /api/Dictionary/v1/Properties
    // @Override
    // @Tag(name = "Dictionary")
    // =====================================================================================================================
    // ENDPOINT: /api/Dictionary/v1/Classes
    // DONE
    @Override
    @Tag(name = "Dictionary")
    public ResponseEntity<DictionaryClassesResponseContractV1Classes> dictionaryClassesGetWithClasses(
        @NotNull @Parameter(name = "Uri", description = "URI of the dictionary, e.g.<br>https://datacat.org/model/2CCiym3eLCQOUDoIy7tUE_<br>", required = true, in = ParameterIn.QUERY) @Valid @RequestParam(value = "Uri", required = true) String uri,
        @Parameter(name = "UseNestedClasses", deprecated = true, description = "! This option is not compatible with datacat.org or any of its instances", in = ParameterIn.QUERY) @Valid @RequestParam(value = "UseNestedClasses", required = false) @Deprecated Boolean useNestedClasses,
        @Parameter(name = "ClassType", deprecated = true, description = "! This option is not compatible with datacat.org or any of its instances", in = ParameterIn.QUERY) @Valid @RequestParam(value = "ClassType", required = false) @Deprecated String classType,
        @Parameter(name = "SearchText", deprecated = true, description = "! This option is not compatible with datacat.org or any of its instances", in = ParameterIn.QUERY) @Valid @RequestParam(value = "SearchText", required = false) @Deprecated String searchText,
        @Parameter(name = "RelatedIfcEntity", deprecated = true, description = "! This option is not compatible with datacat.org or any of its instances", in = ParameterIn.QUERY) @Valid @RequestParam(value = "RelatedIfcEntity", required = false) @Deprecated String relatedIfcEntity,
        @Parameter(name = "Offset", description = "Zero-based offset of the first item to be returned. Default is 0.<br>", in = ParameterIn.QUERY) @Valid @RequestParam(value = "Offset", required = false) Integer offset,
        @Parameter(name = "Limit", description = "Limit number of items to be returned. The default and maximum number<br> of items returned is 1000. When Offset is specified, then the<br> default limit is 100.<br>", in = ParameterIn.QUERY) @Valid @RequestParam(value = "Limit", required = false) Integer limit,
        @Parameter(name = "languageCode", deprecated = true, description = "Specify language (case sensitive).<br> For those items the text is not available in the requested language, the text will be returned in the default language of the dictionary<br> ! This option is not yet implemented.", in = ParameterIn.QUERY) @Valid @RequestParam(required = false) @Deprecated String languageCode
    ) {

        String ID;
        try {
            ID = IdExtractor.extractIdFromUri(uri, "/model/");
        } catch (URISyntaxException e) {
            log.error("Invalid URI format: {}", uri, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            log.error("Failed to extract ID from URI", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            HttpHeaders headers = authenticationService.getAuthorizationHeaders();
            String bearerToken = headers.getFirst("Authorization").substring(7);
            int queryOffset = offset != null ? offset : 0; // default value is 0
            int queryLimit = limit != null ? limit : (queryOffset != 0 ? 100 : 1000); // default value is 1000, but 100 if offset is not 0
            int pageSize = queryOffset + queryLimit; // pageSize is the sum of offset and limit

            DictionaryClassesResponseContractV1Classes dictionaryResponse = apiService.getDictionaryClasses(bearerToken, ID, queryOffset, queryLimit, pageSize, languageCode); // No query passed here
            return new ResponseEntity<>(dictionaryResponse, HttpStatus.OK);

        } catch (HttpServerErrorException e) {
            log.error("Error executing query for getDictionaryClasses", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error("Error fetching dictionary classes", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    // =====================================================================================================================
    // SECTION: POPULAR DICTIONARY
    // =====================================================================================================================
    // ENDPOINT: /api/Dictionary/Popular/v1
    // @Override
    // @Tag(name = "Popular Dictionary")


    // =====================================================================================================================
    // SECTION: PROPERTY
    // =====================================================================================================================
    // ENDPOINT: /api/Property/v5
    @Override
    @Tag(name = "Property")
    public ResponseEntity<PropertyContractV5> propertyGet(
        @NotNull @Parameter(name = "Uri", description = "URI of the class, e.g.<br>https://datacat.org/property/6<br>", required = true, in = ParameterIn.QUERY) @Valid @RequestParam(value = "Uri", required = true) String uri,
        @Parameter(name = "languageCode", deprecated = true, description = "Specify language (case sensitive).<br> For those items the text is not available in the requested language, the text will be returned in the default language of the dictionary<br> ! This option is not yet implemented.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "languageCode", required = false) @Deprecated String languageCode
    ) {
        
        String ID;
        try {
            ID = IdExtractor.extractIdFromUri(uri, "/property/");
        } catch (URISyntaxException e) {
            log.error("Invalid URI format: {}", uri, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            log.error("Failed to extract ID from URI", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            HttpHeaders headers = authenticationService.getAuthorizationHeaders();
            String bearerToken = headers.getFirst("Authorization").substring(7);
            String language = languageCode != null ? languageCode : "de"; // default value is "de" (german)

            PropertyContractV5 propertyDetails = apiService.getPropertyDetails(bearerToken, ID, language);
            return new ResponseEntity<>(propertyDetails, HttpStatus.OK);

        } catch (HttpServerErrorException e) {
            log.error("Error executing query for getPropertyDetails", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error("Error fetching property details", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
     }


    // =====================================================================================================================
    // ENDPOINT: /api/Property/Relations/v1
    // @Override
    // @Tag(name = "Property")


    // =====================================================================================================================
    // ENDPOINT: /api/Property/Classes/v1
    @Override
    @Tag(name = "Property")
    public ResponseEntity<PropertyClassesContractV1> propertyClassesGet(
        @NotNull @Parameter(name = "PropertyUri", description = "URI of the class, e.g.<br>https://datacat.org/property/6<br>", required = true, in = ParameterIn.QUERY) @Valid @RequestParam(value = "PropertyUri", required = true) String propertyUri,
        @Parameter(name = "SearchText", description = "Search text to filter classes<br> ! This option is not yet implemented.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "SearchText", required = false) String searchText,
        @Parameter(name = "Offset", deprecated = true, description = "Zero-based offset of the first item to be returned. Default is 0.<br> ! This option is not yet implemented.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "Offset", required = false) @Deprecated Integer offset,
        @Parameter(name = "Limit", deprecated = true, description = "Limit number of items to be returned. The default and maximum number<br> of items returned is 1000. When Offset is specified, then the<br> default limit is 100.<br> ! This option is not yet implemented.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "Limit", required = false) @Deprecated Integer limit,
        @Parameter(name = "languageCode", description = "Specify language (case sensitive).<br> For those items the text is not available in the requested language, the text will be returned in the default language of the dictionary<br> ! This option is not yet implemented.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "languageCode", required = false) String languageCode
    ) { 
        
        String ID;
        try {
            ID = IdExtractor.extractIdFromUri(propertyUri, "/property/");
        } catch (URISyntaxException e) {
            log.error("Invalid URI format: {}", propertyUri, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            log.error("Failed to extract ID from URI", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            HttpHeaders headers = authenticationService.getAuthorizationHeaders();
            String bearerToken = headers.getFirst("Authorization").substring(7);
            int queryOffset = offset != null ? offset : 0; // default value is 0
            int queryLimit = limit != null ? limit : 1000; // default value is 1000

            PropertyClassesContractV1 response = apiService.getPropertyClasses(bearerToken, ID, queryOffset, queryLimit, languageCode);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (HttpServerErrorException e) {
            log.error("Error executing query for propertyClassesGet", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error("Error fetching property classes", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // =====================================================================================================================
    // ENDPOINT: /api/PropertyValue/v2
    // @Override
    // @Tag(name = "Property")
    // =====================================================================================================================
    // SECTION: SEARCH
    // =====================================================================================================================
    // ENDPOINT: /api/TextSearch/v2
    // @Override
    // @Tag(name = "Search")
    // =====================================================================================================================
    // ENDPOINT: /api/TextSearch/v1
    // @Override
    // @Tag(name = "Search")
    // =====================================================================================================================
    // ENDPOINT: /api/SearchInDictionary/v1
    // @Override
    // @Tag(name = "Search")
    // =====================================================================================================================
    // ENDPOINT: /api/Class/Search/v1
    // @Override
    // @Tag(name = "Search")


    // =====================================================================================================================
    // SECTION: LOOKUP DATA
    // =====================================================================================================================
    // ENDPOINT: /api/Unit/v1
    // @Override
    // @Tag(name = "Lookup Data")
    // public ResponseEntity<List<UnitContractV1>> unitGet(
    // ) { 
        
    //     try {
    //         HttpHeaders headers = authenticationService.getAuthorizationHeaders();
    //         String bearerToken = headers.getFirst("Authorization").substring(7);

    //         List<UnitContractV1> units = apiService.getUnits(bearerToken);
    //         return new ResponseEntity<>(units, HttpStatus.OK);

    //     } catch (HttpServerErrorException e) {
    //         logger.error("Error executing query for getUnits", e);
    //         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    //     } catch (Exception e) {
    //         logger.error("Error fetching units", e);
    //         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    //     }

    //  }

    // =====================================================================================================================
    // ENDPOINT: /api/ReferenceDocument/v1
    // DONE
    @Override
    @Tag(name = "Lookup Data")
    public ResponseEntity<List<ReferenceDocumentContractV1>> referenceDocumentGet(
    ) { 

        try {
            HttpHeaders headers = authenticationService.getAuthorizationHeaders();
            String bearerToken = headers.getFirst("Authorization").substring(7);

            List<ReferenceDocumentContractV1> referenceDocuments = apiService.getReferenceDocuments(bearerToken);
            return new ResponseEntity<>(referenceDocuments, HttpStatus.OK);

        } catch (HttpServerErrorException e) {
            log.error("Error executing query for getReferenceDocuments", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error("Error fetching reference documents", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

     }


    // =====================================================================================================================
    // ENDPOINT: /api/Language/v1
    @Override
    @Tag(name = "Lookup Data")
    public ResponseEntity<List<LanguageContractV1>> languageGet( 
    ) { 

        try {
            HttpHeaders headers = authenticationService.getAuthorizationHeaders();
            String bearerToken = headers.getFirst("Authorization").substring(7);

            List<LanguageContractV1> languages = apiService.getLanguages(bearerToken);
            return new ResponseEntity<>(languages, HttpStatus.OK);

        } catch (HttpServerErrorException e) {
            log.error("Error executing query for getLanguages", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error("Error fetching languages", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    // =====================================================================================================================
    // ENDPOINT: /api/Country/v1
    @Override
    @Tag(name = "Lookup Data")
    public ResponseEntity<List<CountryContractV1>> countryGet(
    ) { 

        try {
            HttpHeaders headers = authenticationService.getAuthorizationHeaders();
            String bearerToken = headers.getFirst("Authorization").substring(7);

            List<CountryContractV1> countries = apiService.getCountries(bearerToken);
            return new ResponseEntity<>(countries, HttpStatus.OK);

        } catch (HttpServerErrorException e) {
            log.error("Error executing query for getCountries", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error("Error fetching countries", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    // =====================================================================================================================
    // SECTION: DATACAT SPECIFICS
    // =====================================================================================================================
    // ENDPOINT: /api/Statistics
    // DONE
    @Override
    @Tag(name = "Datacat Specifics")
    public ResponseEntity<StatisticsResponseContractV1> getStatistics() { 

        try {

            StatisticsResponseContractV1 statistics = apiService.getStatistics();
            return new ResponseEntity<>(statistics, HttpStatus.OK);

        } catch (HttpServerErrorException e) {
            log.error("Error executing query for getStatistics", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error("Error fetching statistics", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

}