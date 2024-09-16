package datacat.restapi;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
// Spring Boot
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

// OpenAPI
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;

// Logging
import java.net.URISyntaxException;
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
    // null return
    @Override
    public ResponseEntity<ClassDetailsResponseV1> getClassDetails(
        @NotNull @Parameter(name = "URI", description = "URI of the class, e.g. https://datacat.org/Class/1Cdl$F84nFRgiJwdnIHUkg", required = true, in = ParameterIn.QUERY) 
        @Valid @RequestParam(value = "URI", required = true) String URI,
        @Parameter(name = "IncludeClassProperties", description = "Use this option to include properties of the class. By default it is set to false.", in = ParameterIn.QUERY)
        @Valid @RequestParam(value = "IncludeClassProperties", required = false) Boolean includeClassProperties) {
        
        
        String ID;
        try { // extract ID from the URI using UriUtils
            ID = UriUtils.extractIdFromUri(URI, "/Class/"); // assuming the prefix for the URI is "/Class/"
        } catch (URISyntaxException e) {
            logger.error("Invalid URI format: {}", URI, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            logger.error("Failed to extract ID from URI", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try { // Retrieve headers with the authorization token from AuthenticationService
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
    }

    // =====================================================================================================================
    // dictionaries
    // malfunctional
    @Override
    public ResponseEntity<DictionaryResponseV1> getDictionary(
        @Parameter(name = "URI", description = "URI of the class, e.g. https://datacat.org/model", in = ParameterIn.QUERY) 
        @Valid @RequestParam(value = "URI", required = false) String URI) {
    
        String ID;
        try { // extract ID from the URI using UriUtils
            ID = UriUtils.extractIdFromUri(URI, "/Dictionary/");
        } catch (URISyntaxException e) {
            logger.error("Invalid URI format: {}", URI, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            logger.error("Failed to extract ID from URI", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    
        try { // Retrieve headers with the authorization token from AuthenticationService
            HttpHeaders headers = authenticationService.getAuthorizationHeaders();
            String bearerToken = headers.getFirst("Authorization").substring(7);
            DictionaryResponseV1 dictionary = graphQLService.getDictionary(ID, bearerToken);
            return new ResponseEntity<>(dictionary, HttpStatus.OK);
        } catch (HttpServerErrorException e) {
            logger.error("Error executing query for getDictionary", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("Error fetching dictionary details", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // =====================================================================================================================
    // classes
    // malfunctional
    public ResponseEntity<ClassesResponseV1> getClasses(
        @Parameter(name = "URI", description = "URI of the class, e.g. https://datacat.org/class required: true", in = ParameterIn.QUERY) 
        @Valid @RequestParam(value = "URI", required = false) String URI) {
    
        String ID;
        try { // extract ID from the URI using UriUtils
            ID = UriUtils.extractIdFromUri(URI, "/Class/");
        } catch (URISyntaxException e) {
            logger.error("Invalid URI format: {}", URI, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            logger.error("Failed to extract ID from URI", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    
        try { // Retrieve headers with the authorization token from AuthenticationService
            HttpHeaders headers = authenticationService.getAuthorizationHeaders();
            String bearerToken = headers.getFirst("Authorization").substring(7);
            ClassesResponseV1 classes = graphQLService.getClasses(ID, bearerToken);
            return new ResponseEntity<>(classes, HttpStatus.OK);
        } catch (HttpServerErrorException e) {
            logger.error("Error executing query for getClasses", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("Error fetching class details", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // =====================================================================================================================
    // properties
    // malfunctional
    @Override
    public ResponseEntity<PropertiesResponseV1> getProperties(
        @Parameter(name = "URI", description = "URI of the class, e.g. https://datacat.org/property required: true", in = ParameterIn.QUERY) 
        @Valid @RequestParam(value = "URI", required = false) String URI) {
    
        String ID;
        try { // extract ID from the URI using UriUtils
            ID = UriUtils.extractIdFromUri(URI, "/Property/");
        } catch (URISyntaxException e) {
            logger.error("Invalid URI format: {}", URI, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            logger.error("Failed to extract ID from URI", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    
        try { // Retrieve headers with the authorization token from AuthenticationService
            HttpHeaders headers = authenticationService.getAuthorizationHeaders();
            String bearerToken = headers.getFirst("Authorization").substring(7);
            PropertiesResponseV1 properties = graphQLService.getProperties(ID, bearerToken);
            return new ResponseEntity<>(properties, HttpStatus.OK);
        } catch (HttpServerErrorException e) {
            logger.error("Error executing query for getProperties", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("Error fetching property details", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // =====================================================================================================================
    // statistics
    // works fine
    @Override
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