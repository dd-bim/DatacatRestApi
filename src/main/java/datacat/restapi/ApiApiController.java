package datacat.restapi;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
// Spring Boot
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

// OpenAPI
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;

// Java 
import java.net.URISyntaxException;
import java.util.List;

// Logging
import org.slf4j.*;


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
public class ApiApiController implements ApiApi {

    private static final Logger logger = LoggerFactory.getLogger(ApiApiController.class);

    private final AuthenticationService authenticationService;
    private final ApiService graphQLService;

    @SuppressWarnings("unused")
    private final NativeWebRequest request; // not know why it is marked as unused - it needed for the constructor below
    @SuppressWarnings("unused")
    private final IdExtractor idExtractor; // not know why it is marked as unused - it needed for the constructor below

    public ApiApiController(NativeWebRequest request, @Lazy ApiService graphQLService, IdExtractor idExtractor, @Lazy AuthenticationService authenticationService) {
        this.request = request;
        this.graphQLService = graphQLService;
        this.idExtractor = idExtractor;
        this.authenticationService = authenticationService;
    }
    
    // =====================================================================================================================
    // SECTION: CLASS
    // =====================================================================================================================
    // ENDPOINT: /api/Class/v1
    @Override
    @Tag(name = "Class")
    public ResponseEntity<ClassContractV1> classGet(
        @NotNull @Parameter(name = "URI", description = "URI of the class, e.g.<br> DATACAT: https://datacat.org/class/41e55123-7bf4-4e2f-8c04-a713f3a087c6<br> CAFM: https://cafm.datacat.org/class/442.90<br> IBPDI: https://ibpdi.datacat.org/class/c102a240-c3f2-11ec-ac20-5d24a21d559a", required = true, in = ParameterIn.QUERY) @Valid @RequestParam(value = "URI", required = true) String URI,
        @Parameter(name = "IncludeClassProperties", description = "Use this option to include properties of the class. By default, it is set to true.<br>", in = ParameterIn.QUERY) @Valid @RequestParam(value = "IncludeClassProperties", required = false) Boolean includeClassProperties,
        @Parameter(name = "IncludeChildClassReferences", deprecated = true, description = "! This option is not compatible with datacat.org or any of its instances", in = ParameterIn.QUERY) @Valid @RequestParam(value = "IncludeChildClassReferences", required = false) @Deprecated Boolean includeChildClassReferences,
        @Parameter(name = "IncludeClassRelations", deprecated = true, description = "! This option is not compatible with datacat.org or any of its instances", in = ParameterIn.QUERY) @Valid @RequestParam(value = "IncludeClassRelations", required = false) @Deprecated Boolean includeClassRelations,
        @Parameter(name = "IncludeReverseRelations", deprecated = true, description = "! This option is not compatible with datacat.org or any of its instances", in = ParameterIn.QUERY) @Valid @RequestParam(value = "IncludeReverseRelations", required = false) @Deprecated Boolean includeReverseRelations,
        @Parameter(name = "ReverseRelationDictionaryUris", deprecated = true, description = "! This option is not compatible with datacat.org or any of its instances", in = ParameterIn.QUERY) @Valid @RequestParam(value = "ReverseRelationDictionaryUris", required = false) @Deprecated List<String> reverseRelationDictionaryUris,
        @Parameter(name = "languageCode", description = "Specify language (case sensitive).<br> For those items the text is not available in the requested language, the text will be returned in the default language of the dictionary<br> ! This option is not yet implemented.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "languageCode", required = false) String languageCode
    ) {
            String ID;
            try {
                ID = IdExtractor.extractIdFromUri(URI, "/class/");
            } catch (URISyntaxException e) {
                logger.error("Invalid URI format: {}", URI, e);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } catch (IllegalArgumentException e) {
                logger.error("Failed to extract ID from URI", e);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
    
            try {
                HttpHeaders headers = authenticationService.getAuthorizationHeaders();
                String bearerToken = headers.getFirst("Authorization").substring(7);
                boolean includeProperties = includeClassProperties != null ? includeClassProperties : false; // default value is false
                String language = languageCode != null ? languageCode : "de"; // default value is "de" (german)

                ClassContractV1 classDetails = graphQLService.getClassDetails(bearerToken, ID, includeProperties, language);
                return new ResponseEntity<>(classDetails, HttpStatus.OK);

            } catch (HttpServerErrorException e) {
                logger.error("Error executing query for getClassDetails", e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (Exception e) {
                logger.error("Error fetching class details", e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }

    // =====================================================================================================================
    // ENDPOINT: /api/Class/Relations/v1


    // =====================================================================================================================
    // ENDPOINT: /api/Class/Properties/v1
    @Override
    @Tag(name = "Class")
    public ResponseEntity<ClassPropertiesContractV1> classPropertiesGet(
        @NotNull @Parameter(name = "ClassUri", description = "URI of the class, e.g.<br> DATACAT: https://datacat.org/class/41e55123-7bf4-4e2f-8c04-a713f3a087c6<br> CAFM: https://cafm.datacat.org/class/442.90<br> IBPDI: https://ibpdi.datacat.org/class/c102a240-c3f2-11ec-ac20-5d24a21d559a", required = true, in = ParameterIn.QUERY) @Valid @RequestParam(value = "ClassUri", required = true) String classUri,
        @Parameter(name = "PropertySet", deprecated = true, description = "'Optional: Property set to filter the properties'<br> ! This option is not yet implemented.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "PropertySet", required = false) @Deprecated String propertySet,
        @Parameter(name = "PropertyCode", deprecated = true, description = "'Optional: Property code to filter the properties'<br> ! This option is not yet implemented.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "PropertyCode", required = false) @Deprecated String propertyCode,
        @Parameter(name = "SearchText", deprecated = true, description = "\"Optional: Search text to filter the properties.<br> Search is done in the property name, property description and property code.<br> Cannot be used together with PropertySet or PropertyCode.\"<br> ! This option is not yet implemented.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "SearchText", required = false) @Deprecated String searchText,
        @Parameter(name = "Offset", deprecated = true, description = "Zero-based offset of the first item to be returned. Default is 0.<br> ! This option is not yet implemented.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "Offset", required = false) @Deprecated Integer offset,
        @Parameter(name = "Limit", deprecated = true, description = "Limit number of items to be returned. The default and maximum number<br> of items returned is 1000. When Offset is specified, then the<br> default limit is 100.<br> ! This option is not yet implemented.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "Limit", required = false) @Deprecated Integer limit,
        @Parameter(name = "languageCode", deprecated = true, description = "Specify language (case sensitive).<br> For those items the text is not available in the requested language, the text will be returned in the default language of the dictionary<br> ! This option is not yet implemented.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "languageCode", required = false) @Deprecated String languageCode
    ) {

        String ID;
        try {
            ID = IdExtractor.extractIdFromUri(classUri, "/class/");
            logger.debug("Extracted ID from URI: {}", ID);
        } catch (URISyntaxException e) {
            logger.error("Invalid URI format: {}", classUri, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            logger.error("Failed to extract ID from URI", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            HttpHeaders headers = authenticationService.getAuthorizationHeaders();
            String bearerToken = headers.getFirst("Authorization").substring(7);
            int queryOffset = offset != null ? offset : 0; // default value is 0
            int queryLimit = limit != null ? limit : 1000; // default value is 1000
            logger.debug("Query Offset: {}, Query Limit: {}", queryOffset, queryLimit);
            ClassPropertiesContractV1 response = graphQLService.classPropertiesGet(bearerToken, ID, queryOffset, queryLimit, languageCode);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (HttpServerErrorException e) {
            logger.error("Error executing query for classPropertiesGet", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("Error fetching class properties", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





    // =====================================================================================================================
    // SECTION: DICTIONARY
    // =====================================================================================================================
    // ENDPOINT: /api/Dictionary/v1
    // malfunctional

    @Override
    @Tag(name = "Dictionary")
    public  ResponseEntity<DictionaryResponseContractV1> dictionaryGet(
        @Parameter(name = "URI", description = "Optional filtering, URI of a specific dictionary, e.g. <br> DATACAT: https://datacat.org/model/34mDkKGrz2FhzL8laZhy9W<br>  CAFM: <br> IBPDI: https://ibpdi.datacat.org/model/800da571-b537-4549-9237-11568678ef9a", in = ParameterIn.QUERY) @Valid @RequestParam(value = "URI", required = false) String URI,
        @Parameter(name = "IncludeTestDictionaries", description = "Should test dictionaries be included in the result? By default it is set to false.  This option is ignored if you specify a URI.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "IncludeTestDictionaries", required = false) Boolean includeTestDictionaries,
        @Parameter(name = "Offset", description = "Zero-based offset of the first item to be returned. Default is 0.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "Offset", required = false) Integer offset,
        @Parameter(name = "Limit", description = "Limit number of items to be returned. The default and maximum number of items returned is 1000. When Offset is specified, then the default limit is 100.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "Limit", required = false) Integer limit
    ) {

        String ID = null;
        if (URI != null && !URI.isEmpty()) {
            try {
                ID = IdExtractor.extractIdFromUri(URI, "/model/");
            } catch (URISyntaxException e) {
                logger.error("Invalid URI format: {}", URI, e);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } catch (IllegalArgumentException e) {
                logger.error("Failed to extract ID from URI", e);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        try {
            HttpHeaders headers = authenticationService.getAuthorizationHeaders();
            String bearerToken = headers.getFirst("Authorization").substring(7);
            boolean includeTest = includeTestDictionaries != null ? includeTestDictionaries : false; // default value is false
            int queryOffset = offset != null ? offset : 0; // default value is 0
            int queryLimit = limit != null ? limit : 1000; // default value is 1000
    
            if (ID != null) { // fetched specific dictionary
                DictionaryResponseContractV1 dictionaryResponse = graphQLService.getOneDictionary(bearerToken, ID, includeTest, queryOffset, queryLimit);
                if (dictionaryResponse != null) {
                    return new ResponseEntity<>(dictionaryResponse, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
            } else { // fetches all dictionaries
                DictionaryResponseContractV1 dictionariesResponse = graphQLService.getAllDictionaries(bearerToken, includeTest, queryOffset, queryLimit);
                if (dictionariesResponse != null && !dictionariesResponse.getDictionaries().isEmpty()) {
                    return new ResponseEntity<>(dictionariesResponse, HttpStatus.OK); // returns the object containing the list of nodes 
                } else {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
            }
    
        } catch (HttpServerErrorException e) {
            logger.error("Error executing query for dictionaryGet", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("Error fetching dictionary", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // =====================================================================================================================
    // ENDPOINT: /api/Dictionary/v1/Properties

    // =====================================================================================================================
    // ENDPOINT: /api/Dictionary/v1/Classes
    @Override
    @Tag(name = "Dictionary")
    public ResponseEntity<DictionaryClassesResponseContractV1Classes> dictionaryClassesGetWithClasses(
        @NotNull @Parameter(name = "URI", description = "URI of the dictionary, e.g.<br> DATACAT: https://datacat.org/model/2CCiym3eLCQOUDoIy7tUE_<br> CAFM: https://cafm.datacat.org/model/0bf5d7c3-5397-4255-ba9f-9607480f1ee1<br> IBPDI: https://ibpdi.datacat.org/model/800da571-b537-4549-9237-11568678ef9a", required = true, in = ParameterIn.QUERY) @Valid @RequestParam(value = "URI", required = true) String URI,
        @Parameter(name = "UseNestedClasses", deprecated = true, description = "! This option is not compatible with datacat.org or any of its instances", in = ParameterIn.QUERY) @Valid @RequestParam(value = "UseNestedClasses", required = false) @Deprecated Boolean useNestedClasses,
        @Parameter(name = "ClassType", deprecated = true, description = "! This option is not compatible with datacat.org or any of its instances", in = ParameterIn.QUERY) @Valid @RequestParam(value = "ClassType", required = false) @Deprecated String classType,
        @Parameter(name = "SearchText", deprecated = true, description = "! This option is not compatible with datacat.org or any of its instances", in = ParameterIn.QUERY) @Valid @RequestParam(value = "SearchText", required = false) @Deprecated String searchText,
        @Parameter(name = "RelatedIfcEntity", deprecated = true, description = "! This option is not compatible with datacat.org or any of its instances", in = ParameterIn.QUERY) @Valid @RequestParam(value = "RelatedIfcEntity", required = false) @Deprecated String relatedIfcEntity,
        @Parameter(name = "Offset", description = "Zero-based offset of the first item to be returned. Default is 0.<br> ! This option is not yet implemented.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "Offset", required = false) Integer offset,
        @Parameter(name = "Limit", description = "Limit number of items to be returned. The default and maximum number of items returned is 1000.<br> When Offset is specified, then the default limit is 100.<br> ! This option is not yet implemented.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "Limit", required = false) Integer limit,
        @Parameter(name = "languageCode", description = "Specify language (case sensitive).<br> For those items the text is not available in the requested language, the text will be returned in the default language of the dictionary<br> ! This option is not yet implemented.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "languageCode", required = false) String languageCode
    ) {
        String ID;
        try {
            ID = IdExtractor.extractIdFromUri(URI, "/model/");
        } catch (URISyntaxException e) {
            logger.error("Invalid URI format: {}", URI, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            logger.error("Failed to extract ID from URI", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            HttpHeaders headers = authenticationService.getAuthorizationHeaders();
            String bearerToken = headers.getFirst("Authorization").substring(7);
            int queryOffset = offset != null ? offset : 0; // default value is 0
            int queryLimit = limit != null ? limit : 1000; // default value is 1000
            DictionaryClassesResponseContractV1Classes dictionaryResponse = graphQLService.getDictionaryClasses(bearerToken, ID, queryOffset, queryLimit, languageCode); // No query passed here
            return new ResponseEntity<>(dictionaryResponse, HttpStatus.OK);
        } catch (HttpServerErrorException e) {
            logger.error("Error executing query for getDictionaryClasses", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("Error fetching dictionary classes", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // =====================================================================================================================
    // SECTION: POPULAR DICTIONARY
    // =====================================================================================================================
    // ENDPOINT: /api/Dictionary/Popular/v1




    // =====================================================================================================================
    // SECTION: PROPERTY
    // =====================================================================================================================
    // ENDPOINT: /api/Property/v4

    // =====================================================================================================================
    // ENDPOINT: /api/Property/Relations/v1

    // =====================================================================================================================
    // ENDPOINT: /api/Property/Classes/v1

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

    // =====================================================================================================================
    // ENDPOINT: /api/ReferenceDocument/v1

    // =====================================================================================================================
    // ENDPOINT: /api/Language/v1

    // =====================================================================================================================
    // ENDPOINT: /api/Country/v1




    // =====================================================================================================================
    // SECTION: DATACAT SPECIFICS
    // =====================================================================================================================
    // ENDPOINT: /api/Statistics
    @Override
    @Tag(name = "Datacat Specifics")
    public ResponseEntity<StatisticsResponseContractV1> getStatistics() {
        try {
            StatisticsResponseContractV1 statistics = graphQLService.getStatistics(); // No query passed here
            return new ResponseEntity<>(statistics, HttpStatus.OK);
        } catch (HttpServerErrorException e) {
            logger.error("Error executing query for getStatistics", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("Error fetching statistics", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}