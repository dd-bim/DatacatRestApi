/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.8.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package datacat.restapi;

import datacat.models.ClassDetailsResponseV1;
import datacat.models.ClassesResponseV1;
import datacat.models.DictionaryResponseV1;
import datacat.models.PropertiesResponseV1;
import datacat.models.StatisticsResponseV1;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-09-16T08:48:14.277332200+02:00[Europe/Berlin]", comments = "Generator version: 7.8.0")
@Validated
@Controller
public interface ApiApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /api/Class/v1 : Get class detailes
     * Following features of this endpoint are not implemented yet:  - get by URI - include properties
     *
     * @param URI URI of the class, e.g. https://datacat.org/Class/1Cdl$F84nFRgiJwdnIHUkg (required)
     * @param includeClassProperties Use this option to include properties of the class. By default it is set to false. (optional)
     * @return Success (status code 200)
     */
    @Operation(
        operationId = "getClassDetails",
        summary = "Get class detailes",
        description = "Following features of this endpoint are not implemented yet:  - get by URI - include properties",
        tags = { "Class" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ClassDetailsResponseV1.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/api/Class/v1",
        produces = { "application/json" }
    )
    
    default ResponseEntity<ClassDetailsResponseV1> getClassDetails(
        @NotNull @Parameter(name = "URI", description = "URI of the class, e.g. https://datacat.org/Class/1Cdl$F84nFRgiJwdnIHUkg", required = true, in = ParameterIn.QUERY) @Valid @RequestParam(value = "URI", required = true) String URI,
        @Parameter(name = "IncludeClassProperties", description = "Use this option to include properties of the class. By default it is set to false.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "IncludeClassProperties", required = false) Boolean includeClassProperties
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"uid\" : \"uid\", \"classProperties\" : [ { \"propertySet\" : \"propertySet\", \"name\" : \"name\", \"description\" : \"description\", \"propertyUri\" : \"propertyUri\", \"uri\" : \"uri\" }, { \"propertySet\" : \"propertySet\", \"name\" : \"name\", \"description\" : \"description\", \"propertyUri\" : \"propertyUri\", \"uri\" : \"uri\" } ], \"name\" : \"name\", \"definition\" : \"definition\", \"dictionaryUri\" : \"dictionaryUri\", \"uri\" : \"uri\", \"versionDateUtc\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /api/Dictionary/v1/Classes : Get Dictionary with tree of classes from Datacat.
     * Following features of this endpoint are not implemented yet:  - get by URI
     *
     * @param URI URI of the class, e.g. https://datacat.org/class required: true (optional)
     * @return Success (status code 200)
     */
    @Operation(
        operationId = "getClasses",
        summary = "Get Dictionary with tree of classes from Datacat.",
        description = "Following features of this endpoint are not implemented yet:  - get by URI",
        tags = { "Dictionary" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ClassesResponseV1.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/api/Dictionary/v1/Classes",
        produces = { "application/json" }
    )
    
    default ResponseEntity<ClassesResponseV1> getClasses(
        @Parameter(name = "URI", description = "URI of the class, e.g. https://datacat.org/class required: true", in = ParameterIn.QUERY) @Valid @RequestParam(value = "URI", required = false) String URI
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"name\" : \"name\", \"uri\" : \"uri\", \"version\" : \"\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /api/Dictiorany/v1 : Get list of available Dictionaries, i.e. XtdBag
     * Following features of this endpoint are not implemented yet:  - filtering by URI
     *
     * @param URI URI of the class, e.g. https://datacat.org/model (optional)
     * @return Success (status code 200)
     */
    @Operation(
        operationId = "getDictionary",
        summary = "Get list of available Dictionaries, i.e. XtdBag",
        description = "Following features of this endpoint are not implemented yet:  - filtering by URI",
        tags = { "Dictionary" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = DictionaryResponseV1.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/api/Dictiorany/v1",
        produces = { "application/json" }
    )
    
    default ResponseEntity<DictionaryResponseV1> getDictionary(
        @Parameter(name = "URI", description = "URI of the class, e.g. https://datacat.org/model", in = ParameterIn.QUERY) @Valid @RequestParam(value = "URI", required = false) String URI
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"name\" : \"name\", \"uri\" : \"uri\", \"version\" : \"version\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /api/Dictionary/v1/Properties : Get Dictionary with its properties from Datacat.
     * Following features of this endpoint are not implemented yet:  - get by URI
     *
     * @param URI URI of the class, e.g. https://datacat.org/property required: true (optional)
     * @return Success (status code 200)
     */
    @Operation(
        operationId = "getProperties",
        summary = "Get Dictionary with its properties from Datacat.",
        description = "Following features of this endpoint are not implemented yet:  - get by URI",
        tags = { "Dictionary" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = PropertiesResponseV1.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/api/Dictionary/v1/Properties",
        produces = { "application/json" }
    )
    
    default ResponseEntity<PropertiesResponseV1> getProperties(
        @Parameter(name = "URI", description = "URI of the class, e.g. https://datacat.org/property required: true", in = ParameterIn.QUERY) @Valid @RequestParam(value = "URI", required = false) String URI
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"name\" : \"name\", \"uri\" : \"uri\", \"version\" : \"\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /api/Statistics : Get statistics about Datacat entries here.
     * With this endpoint the statistics, i.e. the items name and its content count, can be requested with GET.
     *
     * @return Success (status code 200)
     */
    @Operation(
        operationId = "getStatistics",
        summary = "Get statistics about Datacat entries here.",
        description = "With this endpoint the statistics, i.e. the items name and its content count, can be requested with GET.",
        tags = { "Lookup Data" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = StatisticsResponseV1.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/api/Statistics",
        produces = { "application/json" }
    )
    
    default ResponseEntity<StatisticsResponseV1> getStatistics(
        
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"catalogueItemId\" : \"catalogueItemId\", \"itemsCount\" : \"itemsCount\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
