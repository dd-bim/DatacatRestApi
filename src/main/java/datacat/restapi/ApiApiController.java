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

import java.net.URISyntaxException;

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
    public ResponseEntity<ClassDetailsResponseV1> getClassDetails(
        @NotNull @Parameter(name = "URI", description = "URI of the class, e.g. https://datacat.org/Class/1Cdl$F84nFRgiJwdnIHUkg | CAFM: https://cafm.datacat.org/Class/442.90 | IBPDI: https://ibpdi.datacat.org/Class/c102a240-c3f2-11ec-ac20-5d24a21d559a", required = true, in = ParameterIn.QUERY) 
        @Valid @RequestParam(value = "URI", required = true) String URI,
        @Parameter(name = "IncludeClassProperties", description = "Use this option to include properties of the class. By default it is set to false. ! This option is not yet implemented.", in = ParameterIn.QUERY) 
        @Valid @RequestParam(value = "IncludeClassProperties", required = false) Boolean includeClassProperties) {
        
            String ID;
            try {
                ID = UriUtils.extractIdFromUri(URI, "/Class/");
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
                ClassDetailsResponseV1 classDetails = graphQLService.getClassDetails(ID, bearerToken);
                return new ResponseEntity<>(classDetails, HttpStatus.OK);
            } catch (HttpServerErrorException e) {
                logger.error("Error executing query for getClassDetails", e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (Exception e) {
                logger.error("Error fetching class details", e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }


        // return UriUtils.handleRequest(URI, includeClassProperties != null && includeClassProperties, authenticationService, graphQLService, ClassDetailsResponseV1.class);
    }

    // =====================================================================================================================
    // dictionaries
    // malfunctional
    @Override
    @Tag(name = "Dictionary")
    public ResponseEntity<DictionaryResponseV1> getDictionary(
        @Parameter(name = "URI", description = "Optional filtering, URI of a specific dictionary, e.g. https://datacat.org/model/34mDkKGrz2FhzL8laZhy9W", in = ParameterIn.QUERY) 
        @Valid @RequestParam(value = "URI", required = false) String URI) {
    
        String ID = null;
        if (URI != null && !URI.isEmpty()) {
            try {
                ID = UriUtils.extractIdFromUri(URI, "/model/");
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
    
            if (ID != null) {
                // Fetch specific dictionary
                DictionaryResponseV1 dictionaryResponse = graphQLService.getDictionary(ID, bearerToken);
                if (dictionaryResponse != null) {
                    return new ResponseEntity<>(dictionaryResponse, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
            } else {
                // Fetch all dictionaries
                DictionaryResponseV1 dictionariesResponse = graphQLService.getAllDictionaries(bearerToken);
                if (dictionariesResponse != null && !dictionariesResponse.getDictionaries().isEmpty()) {
                    // Return the DictionaryResponseV1 object containing the list of nodes
                    return new ResponseEntity<>(dictionariesResponse, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
            }
        } catch (HttpServerErrorException e) {
            logger.error("Error executing query for getDictionary", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("Error fetching dictionary", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // // =====================================================================================================================
    // // classes
    // // malfunctional
    // @Override
    // @Tag(name = "Dictionary")
    // public ResponseEntity<ClassesResponseV1> getClasses(
    //     @Parameter(name = "URI", description = "URI of the class, e.g. https://datacat.org/class", required = true, in = ParameterIn.QUERY) 
    //     @Valid @RequestParam(value = "URI", required = false) String URI) {
        
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
    @Tag(name = "Lookup Data")
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