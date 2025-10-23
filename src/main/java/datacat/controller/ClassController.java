package datacat.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.HttpServerErrorException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import datacat.models.ClassContractV1;
import datacat.models.ClassPropertiesContractV1;
import datacat.auth.AuthenticationService;
import datacat.graphql.IdExtractor;
import datacat.services.ClassService;

import java.net.URISyntaxException;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * Code-First REST Controller für Class-Endpunkte
 */
@RestController
@RequestMapping("/api")
@Validated
@Tag(name = "Class", description = "Operations about classes")
@Slf4j
public class ClassController {

    private final ClassService classService;
    private final AuthenticationService authenticationService;

    public ClassController(ClassService classService, AuthenticationService authenticationService) {
        this.classService = classService;
        this.authenticationService = authenticationService;
    }

    @Operation(
        summary = "Get class details",
        description = "Due to incompatibility the following features of this endpoint are not implemented (yet): " +
                     "<br><ul><li>include language code filter (to be implemented)</li>" +
                     "<li>various filter options</li><li>various response contents</li></ul>",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Success",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClassContractV1.class)
                )
            )
        }
    )
    @GetMapping("/Class/v1")
    public ResponseEntity<ClassContractV1> classGet(
        @NotNull @Parameter(
            name = "Uri",
            description = "URI of the class, e.g.<br> https://datacat.org/class/41e55123-7bf4-4e2f-8c04-a713f3a087c6<br>",
            required = true,
            in = ParameterIn.QUERY
        )
        @Valid @RequestParam(value = "Uri", required = true) String uri,
        
        @Parameter(
            name = "IncludeClassProperties",
            description = "Use this option to include properties of the class. By default, it is set to false.<br>",
            in = ParameterIn.QUERY
        )
        @Valid @RequestParam(value = "IncludeClassProperties", required = false) Boolean includeClassProperties,
        
        @Parameter(
            name = "IncludeChildClassReferences",
            deprecated = true,
            description = "! This option is not compatible with datacat.org or any of its instances",
            in = ParameterIn.QUERY
        )
        @Valid @RequestParam(value = "IncludeChildClassReferences", required = false) @Deprecated Boolean includeChildClassReferences,
        
        @Parameter(
            name = "IncludeClassRelations",
            deprecated = true,
            description = "! This option is not compatible with datacat.org or any of its instances",
            in = ParameterIn.QUERY
        )
        @Valid @RequestParam(value = "IncludeClassRelations", required = false) @Deprecated Boolean includeClassRelations,
        
        @Parameter(
            name = "IncludeReverseRelations",
            deprecated = true,
            description = "! This option is not compatible with datacat.org or any of its instances",
            in = ParameterIn.QUERY
        )
        @Valid @RequestParam(value = "IncludeReverseRelations", required = false) @Deprecated Boolean includeReverseRelations,
        
        @Parameter(
            name = "ReverseRelationDictionaryUris",
            deprecated = true,
            description = "! This option is not compatible with datacat.org or any of its instances",
            in = ParameterIn.QUERY
        )
        @Valid @RequestParam(value = "ReverseRelationDictionaryUris", required = false) @Deprecated List<String> reverseRelationDictionaryUris,
        
        @Parameter(
            name = "languageCode",
            deprecated = true,
            description = "Specify language (case sensitive).<br> For those items the text is not available in the requested language, the text will be returned in the default language of the dictionary<br> ! This option is not yet implemented.",
            in = ParameterIn.QUERY
        )
        @Valid @RequestParam(value = "languageCode", required = false) @Deprecated String languageCode
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
            String authHeader = headers.getFirst("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            String bearerToken = authHeader.substring(7);
            boolean includeProperties = includeClassProperties != null ? includeClassProperties : false; // default value is false
            String language = languageCode != null ? languageCode : "de"; // default value is "de" (german)

            ClassContractV1 classDetails = classService.getClassDetails(bearerToken, ID, includeProperties, language);
            return new ResponseEntity<>(classDetails, HttpStatus.OK);

        } catch (HttpServerErrorException e) {
            log.error("Error executing query for getClassDetails", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error("Error fetching class details", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
        summary = "Get class properties",
        description = "Get class properties (paginated). " +
                     "Due to incompatibility the following features of this endpoint are not implemented (yet): " +
                     "<br><ul><li>include language code filter (to be implemented)</li>" +
                     "<li>various filter options</li><li>total count is malfunctioning</li>" +
                     "<li>various response contents</li></ul>",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Success",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClassPropertiesContractV1.class)
                )
            )
        }
    )
    @GetMapping("/Class/Properties/v1")
    public ResponseEntity<ClassPropertiesContractV1> classPropertiesGet(
        @NotNull @Parameter(
            name = "ClassUri",
            description = "URI of the class, e.g.<br>https://datacat.org/class/41e55123-7bf4-4e2f-8c04-a713f3a087c6<br>",
            required = true,
            in = ParameterIn.QUERY
        )
        @Valid @RequestParam(value = "ClassUri", required = true) String classUri,
        
        @Parameter(
            name = "PropertySet",
            deprecated = true,
            description = "'Optional: Property set to filter the properties'<br> ! This option is not yet implemented.",
            in = ParameterIn.QUERY
        )
        @Valid @RequestParam(value = "PropertySet", required = false) @Deprecated String propertySet,
        
        @Parameter(
            name = "PropertyCode",
            deprecated = true,
            description = "'Optional: Property code to filter the properties'<br> ! This option is not yet implemented.",
            in = ParameterIn.QUERY
        )
        @Valid @RequestParam(value = "PropertyCode", required = false) @Deprecated String propertyCode,
        
        @Parameter(
            name = "SearchText",
            deprecated = true,
            description = "\"Optional: Search text to filter the properties.<br> Search is done in the property name, property description and property code.<br> Cannot be used together with PropertySet or PropertyCode.\"<br> ! This option is not yet implemented.",
            in = ParameterIn.QUERY
        )
        @Valid @RequestParam(value = "SearchText", required = false) @Deprecated String searchText,
        
        @Parameter(
            name = "Offset",
            description = "Zero-based offset of the first item to be returned. Default is 0.<br>",
            in = ParameterIn.QUERY
        )
        @Valid @RequestParam(value = "Offset", required = false) Integer offset,
        
        @Parameter(
            name = "Limit",
            description = "Limit number of items to be returned. The default and maximum number<br> of items returned is 1000. When Offset is specified, then the<br> default limit is 100.<br>",
            in = ParameterIn.QUERY
        )
        @Valid @RequestParam(value = "Limit", required = false) Integer limit,
        
        @Parameter(
            name = "languageCode",
            deprecated = true,
            description = "Specify language (case sensitive).<br> For those items the text is not available in the requested language, the text will be returned in the default language of the dictionary<br> ! This option is not yet implemented.",
            in = ParameterIn.QUERY
        )
        @Valid @RequestParam(value = "languageCode", required = false) @Deprecated String languageCode
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
            @SuppressWarnings("null")
            String bearerToken = headers.getFirst("Authorization").substring(7);
            int queryOffset = offset != null ? offset : 0; // default value is 0
            int queryLimit = limit != null ? limit : (queryOffset != 0 ? 100 : 1000); // default value is 1000, but 100 if offset is not 0
            int pageSize = queryOffset + queryLimit; // pageSize is the sum of offset and limit

            ClassPropertiesContractV1 response = classService.classPropertiesGet(bearerToken, ID, queryOffset, queryLimit, pageSize, languageCode);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            log.error("Invalid request parameters: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (HttpServerErrorException e) {
            log.error("Error executing query for classPropertiesGet", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error("Error fetching class properties", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
        summary = "[NOT IMPLEMENTED] Class relations", 
        description = "Get class relations or reverse relations (paginated) - not yet implemented",
        deprecated = true,
        responses = {
            @ApiResponse(responseCode = "501", description = "Not implemented")
        }
    )
    @GetMapping("/Class/Relations/v1")
    @Deprecated
    public ResponseEntity<Void> classRelationsGet() {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}
