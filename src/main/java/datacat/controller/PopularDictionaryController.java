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
 * Code-First REST Controller für Popular Dictionary Endpunkte
 */
@RestController
@RequestMapping("/api")
@Validated
@Tag(name = "Popular Dictionary", description = "Operations for popular dictionaries (not implemented)")
@Slf4j
public class PopularDictionaryController {

    /**
     * GET /Dictionary/Popular/v1 : [NOT IMPLEMENTED]Get list of popular Dictionaries
     *
     * @return Success (status code 200)
     * @deprecated
     */
    @Deprecated
    @Operation(
        operationId = "apiDictionaryPopularV1Get",
        summary = "[NOT IMPLEMENTED]Get list of popular Dictionaries",
        deprecated = true,
        responses = {
            @ApiResponse(responseCode = "200", description = "Success")
        }
    )
    @GetMapping("/Dictionary/Popular/v1")
    public ResponseEntity<Void> apiDictionaryPopularV1Get() {
        log.warn("Dictionary Popular v1 endpoint called but not implemented");
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}
