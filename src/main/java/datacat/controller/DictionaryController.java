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

import datacat.models.DictionaryResponseContractV1;
import datacat.models.DictionaryClassesResponseContractV1Classes;
import datacat.auth.AuthenticationService;
import datacat.graphql.IdExtractor;
import datacat.services.DictionaryService;

import java.net.URISyntaxException;

import lombok.extern.slf4j.Slf4j;

/**
 * Code-First REST Controller für Dictionary-Endpunkte
 */
@RestController
@RequestMapping("/api")
@Validated
@Tag(name = "Dictionary", description = "Operations about dictionaries")
@Slf4j
public class DictionaryController {

    private final DictionaryService dictionaryService;
    private final AuthenticationService authenticationService;

    public DictionaryController(DictionaryService dictionaryService, AuthenticationService authenticationService) {
        this.dictionaryService = dictionaryService;
        this.authenticationService = authenticationService;
    }

    @Operation(
        summary = "Get dictionaries",
        description = "Get dictionaries with optional filtering. " +
                     "Due to incompatibility the following features of this endpoint are not implemented (yet): " +
                     "<br><li>various filter options</li><li>various response contents</li></ul>",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Success",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DictionaryResponseContractV1.class)
                )
            )
        }
    )
    @GetMapping("/Dictionary/v1")
    public ResponseEntity<DictionaryResponseContractV1> dictionaryGet(
        @Parameter(
            name = "Uri",
            description = "Optional filtering, URI of a specific dictionary, e.g. <br>https://datacat.org/model/34mDkKGrz2FhzL8laZhy9W<br>",
            in = ParameterIn.QUERY
        )
        @Valid @RequestParam(value = "Uri", required = false) String uri,
        
        @Parameter(
            name = "IncludeTestDictionaries",
            deprecated = true,
            description = "! This option is not compatible with datacat.org or any of its instances",
            in = ParameterIn.QUERY
        )
        @Valid @RequestParam(value = "IncludeTestDictionaries", required = false) @Deprecated Boolean includeTestDictionaries,
        
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
        @Valid @RequestParam(value = "Limit", required = false) Integer limit
    ) {

        try {
            HttpHeaders headers = authenticationService.getAuthorizationHeaders();
            @SuppressWarnings("null")
            String bearerToken = headers.getFirst("Authorization").substring(7);
            int queryOffset = offset != null ? offset : 0; // default value is 0
            int queryLimit = limit != null ? limit : (queryOffset != 0 ? 100 : 1000); // default value is 1000, but 100 if offset is not 0
            // int pageSize = queryOffset + queryLimit; // pageSize is the sum of offset and limit
    
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
                // pageSize = queryOffset + queryLimit;
    
                DictionaryResponseContractV1 dictionaryResponse = dictionaryService.getDictionaryById(bearerToken, ID, queryOffset, queryLimit);
                return new ResponseEntity<>(dictionaryResponse, HttpStatus.OK);
            } else {
                DictionaryResponseContractV1 dictionaryResponse = dictionaryService.getAllDictionaries(bearerToken, queryOffset, queryLimit);
                return new ResponseEntity<>(dictionaryResponse, HttpStatus.OK);
            }
        } catch (IllegalArgumentException e) {
            log.error("Invalid request parameters: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (HttpServerErrorException e) {
            log.error("Error executing query for dictionaryGet", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error("Error fetching dictionary", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
        summary = "Get dictionary classes",
        description = "Get classes from a specific dictionary",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Success",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DictionaryClassesResponseContractV1Classes.class)
                )
            )
        }
    )
    @GetMapping("/Dictionary/v1/Classes")
    public ResponseEntity<DictionaryClassesResponseContractV1Classes> dictionaryClassesGetWithClasses(
        @NotNull @Parameter(
            name = "Uri",
            description = "URI of the dictionary, e.g.<br>https://datacat.org/model/2CCiym3eLCQOUDoIy7tUE_<br>",
            required = true,
            in = ParameterIn.QUERY
        )
        @Valid @RequestParam(value = "Uri", required = true) String uri,
        
        @Parameter(
            name = "UseNestedClasses",
            deprecated = true,
            description = "! This option is not compatible with datacat.org or any of its instances",
            in = ParameterIn.QUERY
        )
        @Valid @RequestParam(value = "UseNestedClasses", required = false) @Deprecated Boolean useNestedClasses,
        
        @Parameter(
            name = "ClassType",
            deprecated = true,
            description = "! This option is not compatible with datacat.org or any of its instances",
            in = ParameterIn.QUERY
        )
        @Valid @RequestParam(value = "ClassType", required = false) @Deprecated String classType,
        
        @Parameter(
            name = "SearchText",
            deprecated = true,
            description = "! This option is not compatible with datacat.org or any of its instances",
            in = ParameterIn.QUERY
        )
        @Valid @RequestParam(value = "SearchText", required = false) @Deprecated String searchText,
        
        @Parameter(
            name = "RelatedIfcEntity",
            deprecated = true,
            description = "! This option is not compatible with datacat.org or any of its instances",
            in = ParameterIn.QUERY
        )
        @Valid @RequestParam(value = "RelatedIfcEntity", required = false) @Deprecated String relatedIfcEntity,
        
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
        @Valid @RequestParam(required = false) @Deprecated String languageCode
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
            @SuppressWarnings("null")
            String bearerToken = headers.getFirst("Authorization").substring(7);
            int queryOffset = offset != null ? offset : 0; // default value is 0
            int queryLimit = limit != null ? limit : (queryOffset != 0 ? 100 : 1000); // default value is 1000, but 100 if offset is not 0
            int pageSize = queryOffset + queryLimit; // pageSize is the sum of offset and limit

            DictionaryClassesResponseContractV1Classes dictionaryResponse = dictionaryService.getDictionaryClasses(bearerToken, ID, queryOffset, queryLimit, pageSize, languageCode); // No query passed here
            return new ResponseEntity<>(dictionaryResponse, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            log.error("Invalid request parameters: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (HttpServerErrorException e) {
            log.error("Error executing query for getDictionaryClasses", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error("Error fetching dictionary classes", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
        summary = "[NOT IMPLEMENTED] Dictionary properties",
        description = "Get dictionary with its properties from Datacat (i.e. XtdProperty) - not yet implemented", 
        deprecated = true,
        responses = {
            @ApiResponse(responseCode = "501", description = "Not implemented")
        }
    )
    @GetMapping("/Dictionary/v1/Properties")
    @Deprecated
    public ResponseEntity<Void> dictionaryV1PropertiesGet() {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}
