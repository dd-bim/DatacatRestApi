package datacat.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.HttpServerErrorException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import datacat.models.StatisticsResponseContractV1;
import datacat.services.DatacatSpecificsService;

import lombok.extern.slf4j.Slf4j;

/**
 * Code-First REST Controller für Datacat-spezifische Endpunkte
 */
@RestController
@RequestMapping("/api")
@Validated
@Tag(name = "Datacat Specifics", description = "Operations specific to Datacat platform")
@Slf4j
public class DatacatSpecificsController {

    private final DatacatSpecificsService datacatSpecificsService;

    public DatacatSpecificsController(DatacatSpecificsService datacatSpecificsService) {
        this.datacatSpecificsService = datacatSpecificsService;
    }

    @Operation(
        summary = "Get statistics",
        description = "Retrieve platform statistics and metrics",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful operation",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StatisticsResponseContractV1.class)
                )
            ),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    @GetMapping("/Statistics/v1")
    public ResponseEntity<StatisticsResponseContractV1> getStatistics() { 
        try {
            StatisticsResponseContractV1 statistics = datacatSpecificsService.getStatistics();
            return new ResponseEntity<>(statistics, HttpStatus.OK);

        } catch (HttpServerErrorException e) {
            log.error("Error executing query for getStatistics", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error("Error fetching statistics", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
