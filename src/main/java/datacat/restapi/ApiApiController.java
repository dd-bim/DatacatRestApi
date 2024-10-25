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
import datacat.graphql.UriHandler;
import datacat.graphql.GraphQLService;

// =====================================================================================================================
// C O N T R O L L E R   S E C T I O N
// =====================================================================================================================
@Controller
public class ApiApiController implements ApiApi {

    private static final Logger logger = LoggerFactory.getLogger(ApiApiController.class);

    private final AuthenticationService authenticationService;
    private final GraphQLService graphQLService;

    @SuppressWarnings("unused")
    private final NativeWebRequest request; // not know why it is marked as unused - it needed for the constructor below
    @SuppressWarnings("unused")
    private final UriHandler uriHandler; // not know why it is marked as unused - it needed for the constructor below

    public ApiApiController(NativeWebRequest request, @Lazy GraphQLService graphQLService, UriHandler uriHandler, @Lazy AuthenticationService authenticationService) {
        this.request = request;
        this.graphQLService = graphQLService;
        this.uriHandler = uriHandler;
        this.authenticationService = authenticationService;
    }
    
    // =====================================================================================================================
    // ENDPOINT: /api/Class/v1
    @Override
    @Tag(name = "Class")
    public ResponseEntity<ClassContractV1> classGet(
        @NotNull @Parameter(name = "URI", description = "URI of the class, e.g.<br> DATACAT: https://datacat.org/class/41e55123-7bf4-4e2f-8c04-a713f3a087c6<br> CAFM: https://cafm.datacat.org/class/442.90<br> IBPDI: https://ibpdi.datacat.org/class/c102a240-c3f2-11ec-ac20-5d24a21d559a", required = true, in = ParameterIn.QUERY) @Valid @RequestParam(value = "URI", required = true) String URI,
        @Parameter(name = "IncludeClassProperties", description = "Use this option to include properties of the class. By default, it is set to true.<br> ! This option is not yet implemented.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "IncludeClassProperties", required = false) Boolean includeClassProperties,
        @Parameter(name = "IncludeChildClassReferences", deprecated = true, description = "! This option is not compatible with datacat.org or any of its instances", in = ParameterIn.QUERY) @Valid @RequestParam(value = "IncludeChildClassReferences", required = false) @Deprecated Boolean includeChildClassReferences,
        @Parameter(name = "IncludeClassRelations", deprecated = true, description = "! This option is not compatible with datacat.org or any of its instances", in = ParameterIn.QUERY) @Valid @RequestParam(value = "IncludeClassRelations", required = false) @Deprecated Boolean includeClassRelations,
        @Parameter(name = "IncludeReverseRelations", deprecated = true, description = "! This option is not compatible with datacat.org or any of its instances", in = ParameterIn.QUERY) @Valid @RequestParam(value = "IncludeReverseRelations", required = false) @Deprecated Boolean includeReverseRelations,
        @Parameter(name = "ReverseRelationDictionaryUris", deprecated = true, description = "! This option is not compatible with datacat.org or any of its instances", in = ParameterIn.QUERY) @Valid @RequestParam(value = "ReverseRelationDictionaryUris", required = false) @Deprecated List<String> reverseRelationDictionaryUris,
        @Parameter(name = "languageCode", description = "Specify language (case sensitive).<br> For those items the text is not available in the requested language, the text will be returned in the default language of the dictionary<br> ! This option is not yet implemented.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "languageCode", required = false) String languageCode
    ) {
            String ID;
            try {
                ID = UriHandler.extractIdFromUri(URI, "/class/");
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
    // dictionaries
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
                ID = UriHandler.extractIdFromUri(URI, "/model/");
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

            DictionaryResponseContractV1 dictionaryResponse = graphQLService.getAllDictionaries(bearerToken, includeTest, queryOffset, queryLimit);
            return new ResponseEntity<>(dictionaryResponse, HttpStatus.OK);

            // if (ID != null) { //fetched specific dictionary
            //     DictionaryResponseContractV1 dictionaryResponse = graphQLService.getDictionary(bearerToken, ID, includeTest, queryOffset, queryLimit);
            //     if (dictionaryResponse != null) {
            //         return new ResponseEntity<>(dictionaryResponse, HttpStatus.OK);
            //     } else {
            //         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            //     }
            // } else { //fetches all dictionaries
            //     DictionaryResponseContractV1 dictionariesResponse = graphQLService.getAllDictionaries(bearerToken, includeTest, queryOffset, queryLimit);
            //     if (dictionariesResponse != null && !dictionariesResponse.getDictionaries().isEmpty()) {
            //         return new ResponseEntity<>(dictionariesResponse, HttpStatus.OK); // returns the object containing the list of nodes 
            //     } else {
            //         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            //     }
            // }

        } catch (HttpServerErrorException e) {
            logger.error("Error executing query for dictionaryGet", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("Error fetching dictionary", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @Override
    // @Tag(name = "Dictionary")
    // public ResponseEntity<DictionaryResponseV1> getDictionary(
    //     @Parameter(name = "URI", description = "Optional filtering, URI of a specific dictionary, e.g. https://datacat.org/model/34mDkKGrz2FhzL8laZhy9W", in = ParameterIn.QUERY) 
    //     @Valid @RequestParam(value = "URI", required = false) String URI) {
    //     String ID = null;
    //     if (URI != null && !URI.isEmpty()) {
    //         try {
    //             ID = UriUtils.extractIdFromUri(URI, "/model/");
    //         } catch (URISyntaxException e) {
    //             logger.error("Invalid URI format: {}", URI, e);
    //             return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    //         } catch (IllegalArgumentException e) {
    //             logger.error("Failed to extract ID from URI", e);
    //             return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    //         }
    //     }
    //     try {
    //         HttpHeaders headers = authenticationService.getAuthorizationHeaders();
    //         String bearerToken = headers.getFirst("Authorization").substring(7);
    //         if (ID != null) { //fetched specific dictionary
    //             DictionaryResponseV1 dictionaryResponse = graphQLService.getDictionary(ID, bearerToken);
    //             if (dictionaryResponse != null) {
    //                 return new ResponseEntity<>(dictionaryResponse, HttpStatus.OK);
    //             } else {
    //                 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    //             }
    //         } else { //fetches all dictionaries
    //             DictionaryResponseV1 dictionariesResponse = graphQLService.getAllDictionaries(bearerToken);
    //             if (dictionariesResponse != null && !dictionariesResponse.getDictionaries().isEmpty()) {
    //                 return new ResponseEntity<>(dictionariesResponse, HttpStatus.OK); // returns the object containing the list of nodes 
    //             } else {
    //                 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    //             }
    //         }
    //     } catch (HttpServerErrorException e) {
    //         logger.error("Error executing query for getDictionary", e);
    //         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    //     } catch (Exception e) {
    //         logger.error("Error fetching dictionary", e);
    //         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }

    // // =====================================================================================================================
    // // ENDPOINT: /api/Dictionary/v1/Classes
    // // malfunctional

    @Override
    @Tag(name = "Dictionary")
    public ResponseEntity<DictionaryClassesResponseContractV1Classes> dictionaryClassesGetWithClasses(
        @NotNull @Parameter(name = "URI", description = "URI of the dictionary, e.g.<br> DATACAT: https://datacat.org/model/2CCiym3eLCQOUDoIy7tUE_<br> CAFM: https://cafm.datacat.org/model/0bf5d7c3-5397-4255-ba9f-9607480f1ee1<br> IBPDI: https://ibpdi.datacat.org/model/800da571-b537-4549-9237-11568678ef9a", required = true, in = ParameterIn.QUERY) @Valid @RequestParam(value = "URI", required = true) String URI,
        @Parameter(name = "UseNestedClasses", deprecated = true, description = "Set to true to get classes in a nested structure.<br>You can't use this option if you are using pagination.<br> ! This option is not yet implemented.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "UseNestedClasses", required = false) @Deprecated Boolean useNestedClasses,
        @Parameter(name = "ClassType", deprecated = true, description = "Optional filter on class type. Possible values are *not specified yet*.<br> ! This option is not yet implemented.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "ClassType", required = false) @Deprecated String classType,
        @Parameter(name = "SearchText", deprecated = true, description = "Optional filter text.<br>Ignored when UseNestedClasses = true<br> ! This option is not yet implemented.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "SearchText", required = false) @Deprecated String searchText,
        @Parameter(name = "RelatedIfcEntity", deprecated = true, description = "Optional filter on related IFC entity. It accepts an IFC entity code (e.g. IfcWall) or uri (e.g. *URI placeholder*).<br> When a code is supplied, finding matching classes ignores the IFC version.<br> Ignored when UseNestedClasses = true<br> ! This option is not yet implemented.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "RelatedIfcEntity", required = false) @Deprecated String relatedIfcEntity,
        @Parameter(name = "Offset", description = "Zero-based offset of the first item to be returned. Default is 0.<br> ! This option is not yet implemented.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "Offset", required = false) Integer offset,
        @Parameter(name = "Limit", description = "Limit number of items to be returned. The default and maximum number of items returned is 1000.<br> When Offset is specified, then the default limit is 100.<br> ! This option is not yet implemented.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "Limit", required = false) Integer limit,
        @Parameter(name = "languageCode", description = "Specify language (case sensitive).<br> For those items the text is not available in the requested language, the text will be returned in the default language of the dictionary<br> ! This option is not yet implemented.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "languageCode", required = false) String languageCode
    ) {
        String ID;
        try {
            ID = UriHandler.extractIdFromUri(URI, "/model/");
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

    // // =====================================================================================================================
    // // properties
    // // malfunctional
    // @Override
    // @Tag(name = "Dictionary")
    // public ResponseEntity<PropertiesResponseV1> getProperties(
    //     @Parameter(name = "URI", description = "URI of the class, e.g. https://datacat.org/property", required = true, in = ParameterIn.QUERY) 
    //     @Valid @RequestParam(value = "URI", required = false) String URI) {   
    //     return ;
    // }

    // =====================================================================================================================
    // ENDPOINT: /api/Statistics
    // works fine
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