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

import datacat.models.PropertyContractV5;
import datacat.models.PropertyClassesContractV1;
import datacat.auth.AuthenticationService;
import datacat.graphql.IdExtractor;
import datacat.services.PropertyService;

import java.net.URISyntaxException;

import lombok.extern.slf4j.Slf4j;

/**
 * Code-First REST Controller für Property-Endpunkte
 */
@RestController
@RequestMapping("/api")
@Validated
@Tag(name = "Property", description = "Operations about properties")
@Slf4j
public class PropertyController {

    private final PropertyService propertyService;
    private final AuthenticationService authenticationService;

    public PropertyController(PropertyService propertyService, AuthenticationService authenticationService) {
        this.propertyService = propertyService;
        this.authenticationService = authenticationService;
    }

    @Operation(
        summary = "Get property details",
        description = "Get property details from a specific property. " +
                     "Due to incompatibility the following features of this endpoint are not implemented (yet): " +
                     "<br><ul><li>include language code filter (to be implemented)</li>" +
                     "<li>include classes (to be implemented)</li><li>various response contents</li></ul>",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Success",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PropertyContractV5.class)
                )
            )
        }
    )
    @GetMapping("/Property/v5")
    public ResponseEntity<PropertyContractV5> propertyGet(
        @NotNull @Parameter(
            name = "Uri",
            description = "URI of the class, e.g.<br>https://datacat.org/property/6<br>",
            required = true,
            in = ParameterIn.QUERY
        )
        @Valid @RequestParam(value = "Uri", required = true) String uri,
        
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
            @SuppressWarnings("null")
            String bearerToken = headers.getFirst("Authorization").substring(7);
            String language = languageCode != null ? languageCode : "de"; // default value is "de" (german)

            PropertyContractV5 propertyDetails = propertyService.getPropertyDetails(bearerToken, ID, language);
            return new ResponseEntity<>(propertyDetails, HttpStatus.OK);

        } catch (HttpServerErrorException e) {
            log.error("Error executing query for getPropertyDetails", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error("Error fetching property details", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
        summary = "Get list of classes that uses the property",
        description = "Get list of classes that uses the property (paginated). " +
                     "Due to incompatibility the following features of this endpoint are not implemented (yet): " +
                     "<br><ul><li>include language code filter (to be implemented)</li>" +
                     "<li>include classes (to be implemented)</li><li>various response contents</li></ul>",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Success",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PropertyClassesContractV1.class)
                )
            )
        }
    )
    @GetMapping("/Property/Classes/v1")
    public ResponseEntity<PropertyClassesContractV1> propertyClassesGet(
        @NotNull @Parameter(
            name = "PropertyUri",
            description = "URI of the class, e.g.<br>https://datacat.org/property/6<br>",
            required = true,
            in = ParameterIn.QUERY
        )
        @Valid @RequestParam(value = "PropertyUri", required = true) String propertyUri,
        
        @Parameter(
            name = "SearchText",
            description = "Search text to filter classes<br> ! This option is not yet implemented.",
            in = ParameterIn.QUERY
        )
        @Valid @RequestParam(value = "SearchText", required = false) String searchText,
        
        @Parameter(
            name = "Offset",
            deprecated = true,
            description = "Zero-based offset of the first item to be returned. Default is 0.<br> ! This option is not yet implemented.",
            in = ParameterIn.QUERY
        )
        @Valid @RequestParam(value = "Offset", required = false) @Deprecated Integer offset,
        
        @Parameter(
            name = "Limit",
            deprecated = true,
            description = "Limit number of items to be returned. The default and maximum number<br> of items returned is 1000. When Offset is specified, then the<br> default limit is 100.<br> ! This option is not yet implemented.",
            in = ParameterIn.QUERY
        )
        @Valid @RequestParam(value = "Limit", required = false) @Deprecated Integer limit,
        
        @Parameter(
            name = "languageCode",
            description = "Specify language (case sensitive).<br> For those items the text is not available in the requested language, the text will be returned in the default language of the dictionary<br> ! This option is not yet implemented.",
            in = ParameterIn.QUERY
        )
        @Valid @RequestParam(value = "languageCode", required = false) String languageCode
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
            @SuppressWarnings("null")
            String bearerToken = headers.getFirst("Authorization").substring(7);
            int queryOffset = offset != null ? offset : 0; // default value is 0
            int queryLimit = limit != null ? limit : 1000; // default value is 1000

            PropertyClassesContractV1 response = propertyService.getPropertyClasses(bearerToken, ID, queryOffset, queryLimit, languageCode);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (HttpServerErrorException e) {
            log.error("Error executing query for propertyClassesGet", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error("Error fetching property classes", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
        summary = "[NOT IMPLEMENTED] Property relations",
        description = "Get property relations or reverse relations (paginated) - not yet implemented",
        deprecated = true,
        responses = {
            @ApiResponse(responseCode = "501", description = "Not implemented")
        }
    )
    @GetMapping("/Property/Relations/v1")
    @Deprecated
    public ResponseEntity<Void> propertyRelationsV1Get() {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Operation(
        summary = "[NOT IMPLEMENTED] Property value details",
        description = "Get Property Value details - not yet implemented",
        deprecated = true,
        responses = {
            @ApiResponse(responseCode = "501", description = "Not implemented")
        }
    )
    @GetMapping("/PropertyValue/v2")
    @Deprecated
    public ResponseEntity<Void> propertyValueV2Get() {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}
