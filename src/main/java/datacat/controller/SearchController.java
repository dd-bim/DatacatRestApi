package datacat.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.extern.slf4j.Slf4j;

/**
 * Code-First REST Controller für Search Endpunkte
 */
@RestController
@RequestMapping("/api")
@Validated
@Tag(name = "Search", description = "Search operations (not implemented)")
@Slf4j
public class SearchController {

    /**
     * GET /Class/Search/v1 : [NOT IMPLEMENTED]
     *
     * @return Success (status code 200)
     * @deprecated
     */
    @Deprecated
    @Operation(
        operationId = "apiClassSearchV1Get",
        summary = "[NOT IMPLEMENTED]",
        deprecated = true,
        responses = {
            @ApiResponse(responseCode = "200", description = "Success")
        }
    )
    @GetMapping("/Class/Search/v1")
    public ResponseEntity<Void> apiClassSearchV1Get() {
        log.warn("Class Search v1 endpoint called but not implemented");
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    /**
     * GET /SearchInDictionary/v1 : [NOT IMPLEMENTED]
     *
     * @return Success (status code 200)
     * @deprecated
     */
    @Deprecated
    @Operation(
        operationId = "apiSearchInDictionaryV1Get",
        summary = "[NOT IMPLEMENTED]",
        deprecated = true,
        responses = {
            @ApiResponse(responseCode = "200", description = "Success")
        }
    )
    @GetMapping("/SearchInDictionary/v1")
    public ResponseEntity<Void> apiSearchInDictionaryV1Get() {
        log.warn("SearchInDictionary v1 endpoint called but not implemented");
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    /**
     * GET /TextSearch/v2 : [NOT IMPLEMENTED]
     *
     * @return Success (status code 200)
     * @deprecated
     */
    @Deprecated
    @Operation(
        operationId = "apiTextSearchV2Get",
        summary = "[NOT IMPLEMENTED]",
        deprecated = true,
        responses = {
            @ApiResponse(responseCode = "200", description = "Success")
        }
    )
    @GetMapping("/TextSearch/v2")
    public ResponseEntity<Void> apiTextSearchV2Get() {
        log.warn("TextSearch v2 endpoint called but not implemented");
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}
