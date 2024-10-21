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
import datacat.service.GraphQLService;
import datacat.service.UriUtils;
import datacat.auth.AuthenticationService;

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
    private final UriUtils uriUtils; // not know why it is marked as unused - it needed for the constructor below

    public ApiApiController(NativeWebRequest request, @Lazy GraphQLService graphQLService, UriUtils uriUtils, @Lazy AuthenticationService authenticationService) {
        this.request = request;
        this.graphQLService = graphQLService;
        this.uriUtils = uriUtils;
        this.authenticationService = authenticationService;
    }
    
    // =====================================================================================================================
    // class details with class properties
    // partly null return
    @Override
    @Tag(name = "Class")
    public ResponseEntity<ClassContractV1> getClassDetails(
        @NotNull @Parameter(name = "URI", description = "URI of the class, e.g. https://datacat.org/class/1Cdl$F84nFRgiJwdnIHUkg | CAFM: https://cafm.datacat.org/class/442.90 | IBPDI: https://ibpdi.datacat.org/class/c102a240-c3f2-11ec-ac20-5d24a21d559a", required = true, in = ParameterIn.QUERY) @Valid @RequestParam(value = "URI", required = true) String URI,
        @Parameter(name = "IncludeClassProperties", description = "Use this option to include properties of the class. By default, it is set to true. ! This option is not yet implemented.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "IncludeClassProperties", required = false) Boolean includeClassProperties,
        @Parameter(name = "IncludeChildClassReferences", description = "! This option is not compatible with datacat.org or any of its instances", in = ParameterIn.QUERY) @Valid @RequestParam(value = "IncludeChildClassReferences", required = false) Boolean includeChildClassReferences,
        @Parameter(name = "IncludeClassRelations", description = "Use this option to include loading relations of the class. By default, it is set to false. ! This option is not yet implemented.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "IncludeClassRelations", required = false) Boolean includeClassRelations,
        @Parameter(name = "IncludeReverseRelations", description = "Use this option to include loading reverse relations of the class, i.e. classes having a relation with this class. By default, it is set to false. ! This option is not yet implemented.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "IncludeReverseRelations", required = false) Boolean includeReverseRelations,
        @Parameter(name = "ReverseRelationDictionaryUris", description = "! This option is not compatible with datacat.org or any of its instances", in = ParameterIn.QUERY) @Valid @RequestParam(value = "ReverseRelationDictionaryUris", required = false) List<String> reverseRelationDictionaryUris,
        @Parameter(name = "languageCode", description = "Specify language (case sensitive). For those items the text is not available in the requested language, the text will be returned in the default language of the dictionary", in = ParameterIn.QUERY) @Valid @RequestParam(value = "languageCode", required = false) String languageCode
    ) {
        
            String ID;
            try {
                ID = UriUtils.extractIdFromUri(URI, "/class/");
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
                ClassContractV1 classDetails = graphQLService.getClassDetails(ID, bearerToken);
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
    // expected to work fine (!) test
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
    // // classes
    // // malfunctional
    // @Override
    // @Tag(name = "Dictionary")
    // public ResponseEntity<ClassesResponseV1> getClasses(
    //     @NotNull @Parameter(name = "URI", description = "URI of the dictionary, e.g. As-Built_Surveying https://datacat.org/model/2CCiym3eLCQOUDoIy7tUE_", required = true, in = ParameterIn.QUERY) 
    //     @Valid @RequestParam(value = "URI", required = true) String URI) {
    
    //     String dictionaryId;
    //     try {
    //         // Extract ID from the provided URI
    //         dictionaryId = UriUtils.extractIdFromUri(URI, "/model/");
    //     } catch (URISyntaxException e) {
    //         logger.error("Invalid URI format: {}", URI, e);
    //         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    //     } catch (IllegalArgumentException e) {
    //         logger.error("Failed to extract ID from URI", e);
    //         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    //     }
    
    //     try {
    //         // Get the authorization headers and validate the Bearer token
    //         HttpHeaders headers = authenticationService.getAuthorizationHeaders();
    //         String bearerToken = headers.getFirst("Authorization").substring(7);
    
    //         // Fetch classes using the service layer (assuming a method for fetching by dictionaryId)
    //         ClassesResponseV1 classesResponse = graphQLService.getClasses(dictionaryId, bearerToken);
    
    //         // Return the list of classes in the response
    //         return new ResponseEntity<>(classesResponse, HttpStatus.OK);
    //     } catch (HttpServerErrorException e) {
    //         logger.error("Error executing query for getClasses", e);
    //         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    //     } catch (Exception e) {
    //         logger.error("Error fetching classes for dictionary", e);
    //         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }

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
    // statistics
    // works fine
    @Override
    @Tag(name = "Datacat Specifics")
    public ResponseEntity<StatisticsResponseV1> getStatistics() {
        try {
            StatisticsResponseV1 statistics = graphQLService.getStatistics(); // No query passed here
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