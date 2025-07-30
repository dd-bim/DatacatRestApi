package datacat.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.HttpServerErrorException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import datacat.models.ReferenceDocumentContractV1;
import datacat.models.LanguageContractV1;
import datacat.models.CountryContractV1;
import datacat.models.UnitContractV1;
import datacat.auth.AuthenticationService;
import datacat.services.LookupDataService;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * Code-First REST Controller für Lookup Data Endpunkte
 */
@RestController
@RequestMapping("/api")
@Validated
@Tag(name = "Lookup Data", description = "Operations for reference data like languages, countries, and reference documents")
@Slf4j
public class LookupDataController {

    private final LookupDataService lookupDataService;
    private final AuthenticationService authenticationService;

    public LookupDataController(LookupDataService lookupDataService, AuthenticationService authenticationService) {
        this.lookupDataService = lookupDataService;
        this.authenticationService = authenticationService;
    }

    @Operation(
        summary = "Get reference documents",
        description = "Retrieve all available reference documents",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful operation",
                content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ReferenceDocumentContractV1.class))
                )
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    @GetMapping("/ReferenceDocument/v1")
    public ResponseEntity<List<ReferenceDocumentContractV1>> referenceDocumentGet() { 
        try {
            HttpHeaders headers = authenticationService.getAuthorizationHeaders();
            String authHeader = headers.getFirst("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            String bearerToken = authHeader.substring(7);

            List<ReferenceDocumentContractV1> referenceDocuments = lookupDataService.getReferenceDocuments(bearerToken);
            return new ResponseEntity<>(referenceDocuments, HttpStatus.OK);

        } catch (HttpServerErrorException e) {
            log.error("Error executing query for getReferenceDocuments", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error("Error fetching reference documents", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
        summary = "Get languages",
        description = "Retrieve all available languages",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful operation",
                content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = LanguageContractV1.class))
                )
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    @GetMapping("/Language/v1")
    public ResponseEntity<List<LanguageContractV1>> languageGet() { 
        try {
            HttpHeaders headers = authenticationService.getAuthorizationHeaders();
            String authHeader = headers.getFirst("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            String bearerToken = authHeader.substring(7);

            List<LanguageContractV1> languages = lookupDataService.getLanguages(bearerToken);
            return new ResponseEntity<>(languages, HttpStatus.OK);

        } catch (HttpServerErrorException e) {
            log.error("Error executing query for getLanguages", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error("Error fetching languages", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
        summary = "Get countries",
        description = "Retrieve all available countries",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful operation",
                content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = CountryContractV1.class))
                )
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    @GetMapping("/Country/v1")
    public ResponseEntity<List<CountryContractV1>> countryGet() { 
        try {
            HttpHeaders headers = authenticationService.getAuthorizationHeaders();
            String authHeader = headers.getFirst("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            String bearerToken = authHeader.substring(7);

            List<CountryContractV1> countries = lookupDataService.getCountries(bearerToken);
            return new ResponseEntity<>(countries, HttpStatus.OK);

        } catch (HttpServerErrorException e) {
            log.error("Error executing query for getCountries", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error("Unexpected error in countryGet", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * GET /Unit/v1 : [NOT IMPLEMENTED]Get list of all units
     *
     * @return Success (status code 200)
     * @deprecated
     */
    @Deprecated
    @Operation(
        operationId = "unitGet",
        summary = "[NOT IMPLEMENTED]Get list of all units",
        deprecated = true,
        responses = {
            @ApiResponse(responseCode = "200", description = "Success", 
                content = @Content(mediaType = "application/json", 
                    array = @ArraySchema(schema = @Schema(implementation = UnitContractV1.class))
                )
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    @GetMapping("/Unit/v1")
    public ResponseEntity<List<UnitContractV1>> unitGet() {
        log.warn("Unit endpoint called but not implemented");
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}
