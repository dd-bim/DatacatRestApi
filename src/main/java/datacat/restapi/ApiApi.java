/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.8.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package datacat.restapi;

import datacat.models.ClassContractV1;
import datacat.models.DictionaryClassesResponseContractV1Classes;
import datacat.models.DictionaryResponseContractV1;
import datacat.models.StatisticsResponseContractV1;
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

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-10-25T16:18:38.996292600+02:00[Europe/Berlin]", comments = "Generator version: 7.8.0")
@Validated
@Controller
public interface ApiApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /api/Class/Search/v1
     *
     * @return Success (status code 200)
     * @deprecated
     */
    @Deprecated
    @Operation(
        operationId = "apiClassSearchV1Get",
        deprecated = true,
        tags = { "Search" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Success")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/api/Class/Search/v1"
    )
    
    default ResponseEntity<Void> apiClassSearchV1Get(
        
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /api/Country/v1
     *
     * @return Success (status code 200)
     * @deprecated
     */
    @Deprecated
    @Operation(
        operationId = "apiCountryV1Get",
        deprecated = true,
        tags = { "Lookup Data" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Success")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/api/Country/v1"
    )
    
    default ResponseEntity<Void> apiCountryV1Get(
        
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /api/Dictionary/Popular/v1 : Get list of popular Dictionaries
     *
     * @return Success (status code 200)
     * @deprecated
     */
    @Deprecated
    @Operation(
        operationId = "apiDictionaryPopularV1Get",
        summary = "Get list of popular Dictionaries",
        deprecated = true,
        tags = { "Popular Dictionary" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Success")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/api/Dictionary/Popular/v1"
    )
    
    default ResponseEntity<Void> apiDictionaryPopularV1Get(
        
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /api/Dictionary/v1/Properties : [NOT IMPLEMETED]Get dictionary with its properties from Datacat (i.e. XtdProperty).
     *
     * @return Success (status code 200)
     * @deprecated
     */
    @Deprecated
    @Operation(
        operationId = "apiDictionaryV1PropertiesGet",
        summary = "[NOT IMPLEMETED]Get dictionary with its properties from Datacat (i.e. XtdProperty).",
        deprecated = true,
        tags = { "Dictionary" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Success")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/api/Dictionary/v1/Properties"
    )
    
    default ResponseEntity<Void> apiDictionaryV1PropertiesGet(
        
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /api/Language/v1
     *
     * @return Success (status code 200)
     * @deprecated
     */
    @Deprecated
    @Operation(
        operationId = "apiLanguageV1Get",
        deprecated = true,
        tags = { "Lookup Data" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Success")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/api/Language/v1"
    )
    
    default ResponseEntity<Void> apiLanguageV1Get(
        
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /api/Property/Classes/v1 : Get list of classes that uses the property (paginated)
     *
     * @return Success (status code 200)
     * @deprecated
     */
    @Deprecated
    @Operation(
        operationId = "apiPropertyClassesV1Get",
        summary = "Get list of classes that uses the property (paginated)",
        deprecated = true,
        tags = { "Property" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Success")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/api/Property/Classes/v1"
    )
    
    default ResponseEntity<Void> apiPropertyClassesV1Get(
        
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /api/Property/Relations/v1 : Get property relations or reverse relations (paginated)
     *
     * @return Success (status code 200)
     * @deprecated
     */
    @Deprecated
    @Operation(
        operationId = "apiPropertyRelationsV1Get",
        summary = "Get property relations or reverse relations (paginated)",
        deprecated = true,
        tags = { "Property" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Success")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/api/Property/Relations/v1"
    )
    
    default ResponseEntity<Void> apiPropertyRelationsV1Get(
        
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /api/Property/v4 : Get Property details
     *
     * @return Success (status code 200)
     * @deprecated
     */
    @Deprecated
    @Operation(
        operationId = "apiPropertyV4Get",
        summary = "Get Property details",
        deprecated = true,
        tags = { "Property" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Success")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/api/Property/v4"
    )
    
    default ResponseEntity<Void> apiPropertyV4Get(
        
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /api/PropertyValue/v2 : Get Property Value details
     *
     * @return Success (status code 200)
     * @deprecated
     */
    @Deprecated
    @Operation(
        operationId = "apiPropertyValueV2Get",
        summary = "Get Property Value details",
        deprecated = true,
        tags = { "Property" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Success")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/api/PropertyValue/v2"
    )
    
    default ResponseEntity<Void> apiPropertyValueV2Get(
        
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /api/ReferenceDocument/v1
     *
     * @return Success (status code 200)
     * @deprecated
     */
    @Deprecated
    @Operation(
        operationId = "apiReferenceDocumentV1Get",
        deprecated = true,
        tags = { "Lookup Data" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Success")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/api/ReferenceDocument/v1"
    )
    
    default ResponseEntity<Void> apiReferenceDocumentV1Get(
        
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /api/SearchInDictionary/v1
     *
     * @return Success (status code 200)
     * @deprecated
     */
    @Deprecated
    @Operation(
        operationId = "apiSearchInDictionaryV1Get",
        deprecated = true,
        tags = { "Search" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Success")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/api/SearchInDictionary/v1"
    )
    
    default ResponseEntity<Void> apiSearchInDictionaryV1Get(
        
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /api/TextSearch/v1
     *
     * @return Success (status code 200)
     * @deprecated
     */
    @Deprecated
    @Operation(
        operationId = "apiTextSearchV1Get",
        deprecated = true,
        tags = { "Search" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Success")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/api/TextSearch/v1"
    )
    
    default ResponseEntity<Void> apiTextSearchV1Get(
        
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /api/TextSearch/v2
     *
     * @return Success (status code 200)
     * @deprecated
     */
    @Deprecated
    @Operation(
        operationId = "apiTextSearchV2Get",
        deprecated = true,
        tags = { "Search" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Success")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/api/TextSearch/v2"
    )
    
    default ResponseEntity<Void> apiTextSearchV2Get(
        
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /api/Unit/v1
     *
     * @return Success (status code 200)
     * @deprecated
     */
    @Deprecated
    @Operation(
        operationId = "apiUnitV1Get",
        deprecated = true,
        tags = { "Lookup Data" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Success")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/api/Unit/v1"
    )
    
    default ResponseEntity<Void> apiUnitV1Get(
        
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /api/Class/v1 : [IN REWORK]Get class details (i.e. XtdSubject) from a specific class
     * Following features of this endpoint are not implemented yet: &lt;br&gt; &lt;ul&gt;&lt;li&gt;include language code filter&lt;/li&gt; &lt;li&gt;various response contents&lt;/li&gt;&lt;/ul&gt;
     *
     * @param URI URI of the class, e.g.&lt;br&gt; DATACAT: https://datacat.org/class/41e55123-7bf4-4e2f-8c04-a713f3a087c6&lt;br&gt; CAFM: https://cafm.datacat.org/class/442.90&lt;br&gt; IBPDI: https://ibpdi.datacat.org/class/c102a240-c3f2-11ec-ac20-5d24a21d559a (required)
     * @param includeClassProperties Use this option to include properties of the class. By default, it is set to true.&lt;br&gt; ! This option is not yet implemented. (optional)
     * @param includeChildClassReferences ! This option is not compatible with datacat.org or any of its instances (optional)
     * @param includeClassRelations ! This option is not compatible with datacat.org or any of its instances (optional)
     * @param includeReverseRelations ! This option is not compatible with datacat.org or any of its instances (optional)
     * @param reverseRelationDictionaryUris ! This option is not compatible with datacat.org or any of its instances (optional)
     * @param languageCode Specify language (case sensitive).&lt;br&gt; For those items the text is not available in the requested language, the text will be returned in the default language of the dictionary&lt;br&gt; ! This option is not yet implemented. (optional)
     * @return Success (status code 200)
     */
    @Operation(
        operationId = "classGet",
        summary = "[IN REWORK]Get class details (i.e. XtdSubject) from a specific class",
        description = "Following features of this endpoint are not implemented yet: <br> <ul><li>include language code filter</li> <li>various response contents</li></ul>",
        tags = { "Class" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ClassContractV1.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/api/Class/v1",
        produces = { "application/json" }
    )
    
    default ResponseEntity<ClassContractV1> classGet(
        @NotNull @Parameter(name = "URI", description = "URI of the class, e.g.<br> DATACAT: https://datacat.org/class/41e55123-7bf4-4e2f-8c04-a713f3a087c6<br> CAFM: https://cafm.datacat.org/class/442.90<br> IBPDI: https://ibpdi.datacat.org/class/c102a240-c3f2-11ec-ac20-5d24a21d559a", required = true, in = ParameterIn.QUERY) @Valid @RequestParam(value = "URI", required = true) String URI,
        @Parameter(name = "IncludeClassProperties", description = "Use this option to include properties of the class. By default, it is set to true.<br> ! This option is not yet implemented.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "IncludeClassProperties", required = false) Boolean includeClassProperties,
        @Parameter(name = "IncludeChildClassReferences", deprecated = true, description = "! This option is not compatible with datacat.org or any of its instances", in = ParameterIn.QUERY) @Valid @RequestParam(value = "IncludeChildClassReferences", required = false) @Deprecated Boolean includeChildClassReferences,
        @Parameter(name = "IncludeClassRelations", deprecated = true, description = "! This option is not compatible with datacat.org or any of its instances", in = ParameterIn.QUERY) @Valid @RequestParam(value = "IncludeClassRelations", required = false) @Deprecated Boolean includeClassRelations,
        @Parameter(name = "IncludeReverseRelations", deprecated = true, description = "! This option is not compatible with datacat.org or any of its instances", in = ParameterIn.QUERY) @Valid @RequestParam(value = "IncludeReverseRelations", required = false) @Deprecated Boolean includeReverseRelations,
        @Parameter(name = "ReverseRelationDictionaryUris", deprecated = true, description = "! This option is not compatible with datacat.org or any of its instances", in = ParameterIn.QUERY) @Valid @RequestParam(value = "ReverseRelationDictionaryUris", required = false) @Deprecated List<String> reverseRelationDictionaryUris,
        @Parameter(name = "languageCode", description = "Specify language (case sensitive).<br> For those items the text is not available in the requested language, the text will be returned in the default language of the dictionary<br> ! This option is not yet implemented.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "languageCode", required = false) String languageCode
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"code\" : \"code\", \"revisionNumber\" : 0, \"subdivisionsOfUse\" : [ \"subdivisionsOfUse\", \"subdivisionsOfUse\" ], \"description\" : \"description\", \"deprecationExplanation\" : \"deprecationExplanation\", \"versionDateUtc\" : \"2000-01-23T04:56:07.000+00:00\", \"uid\" : \"uid\", \"replacedObjectCodes\" : [ \"replacedObjectCodes\", \"replacedObjectCodes\" ], \"replacingObjectCodes\" : [ \"replacingObjectCodes\", \"replacingObjectCodes\" ], \"creatorLanguageCode\" : \"creatorLanguageCode\", \"definition\" : \"definition\", \"dictionaryUri\" : \"dictionaryUri\", \"countriesOfUse\" : [ \"countriesOfUse\", \"countriesOfUse\" ], \"visualRepresentationUri\" : \"visualRepresentationUri\", \"synonyms\" : [ \"synonyms\", \"synonyms\" ], \"parentClassReference\" : { \"name\" : \"name\", \"uri\" : \"uri\" }, \"activationDateUtc\" : \"2000-01-23T04:56:07.000+00:00\", \"uri\" : \"uri\", \"versionNumber\" : 6, \"classProperties\" : [ { \"dataType\" : \"dataType\", \"name\" : \"name\", \"description\" : \"description\", \"propertyUri\" : \"propertyUri\", \"uri\" : \"uri\" }, { \"dataType\" : \"dataType\", \"name\" : \"name\", \"description\" : \"description\", \"propertyUri\" : \"propertyUri\", \"uri\" : \"uri\" } ], \"deActivationDateUtc\" : \"2000-01-23T04:56:07.000+00:00\", \"name\" : \"name\", \"documentReference\" : \"documentReference\", \"countryOfOrigin\" : \"countryOfOrigin\", \"revisionDateUtc\" : \"2000-01-23T04:56:07.000+00:00\", \"referenceCode\" : \"referenceCode\", \"relatedIfcEntityNames\" : [ \"relatedIfcEntityNames\", \"relatedIfcEntityNames\" ], \"classType\" : \"classType\", \"status\" : \"status\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /api/Class/Properties/v1 : [NOT IMPLEMENTED]Get class properties (paginated)
     *
     * @return Success (status code 200)
     * @deprecated
     */
    @Deprecated
    @Operation(
        operationId = "classPropertiesGet",
        summary = "[NOT IMPLEMENTED]Get class properties (paginated)",
        deprecated = true,
        tags = { "Class" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Success")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/api/Class/Properties/v1"
    )
    
    default ResponseEntity<Void> classPropertiesGet(
        
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /api/Class/Relations/v1 : [NOT IMPLEMENTED]Get class relations or reverse relations (paginated)
     *
     * @return Success (status code 200)
     * @deprecated
     */
    @Deprecated
    @Operation(
        operationId = "classRelationsGet",
        summary = "[NOT IMPLEMENTED]Get class relations or reverse relations (paginated)",
        deprecated = true,
        tags = { "Class" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Success")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/api/Class/Relations/v1"
    )
    
    default ResponseEntity<Void> classRelationsGet(
        
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /api/Dictionary/v1/Classes : [IN REWORK]Get dictionary with tree of classes from Datacat (i.e. XtdSubject).
     * Following features of this endpoint are not implemented yet:&lt;br&gt; &lt;ul&gt;&lt;li&gt;usage of nested classes&lt;/li&gt; &lt;li&gt;filter for class type&lt;/li&gt; &lt;li&gt;filter for text&lt;/li&gt; &lt;li&gt;filter for IFC entity&lt;/li&gt; &lt;li&gt;consideration of limit and offset&lt;/li&gt; &lt;li&gt;filter for language code&lt;/li&gt;&lt;/ul&gt;
     *
     * @param URI URI of the dictionary, e.g.&lt;br&gt; DATACAT: https://datacat.org/model/2CCiym3eLCQOUDoIy7tUE_&lt;br&gt; CAFM: https://cafm.datacat.org/model/0bf5d7c3-5397-4255-ba9f-9607480f1ee1&lt;br&gt; IBPDI: https://ibpdi.datacat.org/model/800da571-b537-4549-9237-11568678ef9a (required)
     * @param useNestedClasses Set to true to get classes in a nested structure.&lt;br&gt;You can&#39;t use this option if you are using pagination.&lt;br&gt; ! This option is not yet implemented. (optional)
     * @param classType Optional filter on class type. Possible values are *not specified yet*.&lt;br&gt; ! This option is not yet implemented. (optional)
     * @param searchText Optional filter text.&lt;br&gt;Ignored when UseNestedClasses &#x3D; true&lt;br&gt; ! This option is not yet implemented. (optional)
     * @param relatedIfcEntity Optional filter on related IFC entity. It accepts an IFC entity code (e.g. IfcWall) or uri (e.g. *URI placeholder*).&lt;br&gt; When a code is supplied, finding matching classes ignores the IFC version.&lt;br&gt; Ignored when UseNestedClasses &#x3D; true&lt;br&gt; ! This option is not yet implemented. (optional)
     * @param offset Zero-based offset of the first item to be returned. Default is 0.&lt;br&gt; ! This option is not yet implemented. (optional)
     * @param limit Limit number of items to be returned. The default and maximum number of items returned is 1000.&lt;br&gt; When Offset is specified, then the default limit is 100.&lt;br&gt; ! This option is not yet implemented. (optional)
     * @param languageCode Specify language (case sensitive).&lt;br&gt; For those items the text is not available in the requested language, the text will be returned in the default language of the dictionary&lt;br&gt; ! This option is not yet implemented. (optional)
     * @return Success (status code 200)
     */
    @Operation(
        operationId = "dictionaryClassesGetWithClasses",
        summary = "[IN REWORK]Get dictionary with tree of classes from Datacat (i.e. XtdSubject).",
        description = "Following features of this endpoint are not implemented yet:<br> <ul><li>usage of nested classes</li> <li>filter for class type</li> <li>filter for text</li> <li>filter for IFC entity</li> <li>consideration of limit and offset</li> <li>filter for language code</li></ul>",
        tags = { "Dictionary" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = DictionaryClassesResponseContractV1Classes.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/api/Dictionary/v1/Classes",
        produces = { "application/json" }
    )
    
    default ResponseEntity<DictionaryClassesResponseContractV1Classes> dictionaryClassesGetWithClasses(
        @NotNull @Parameter(name = "URI", description = "URI of the dictionary, e.g.<br> DATACAT: https://datacat.org/model/2CCiym3eLCQOUDoIy7tUE_<br> CAFM: https://cafm.datacat.org/model/0bf5d7c3-5397-4255-ba9f-9607480f1ee1<br> IBPDI: https://ibpdi.datacat.org/model/800da571-b537-4549-9237-11568678ef9a", required = true, in = ParameterIn.QUERY) @Valid @RequestParam(value = "URI", required = true) String URI,
        @Parameter(name = "UseNestedClasses", deprecated = true, description = "Set to true to get classes in a nested structure.<br>You can't use this option if you are using pagination.<br> ! This option is not yet implemented.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "UseNestedClasses", required = false) @Deprecated Boolean useNestedClasses,
        @Parameter(name = "ClassType", deprecated = true, description = "Optional filter on class type. Possible values are *not specified yet*.<br> ! This option is not yet implemented.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "ClassType", required = false) @Deprecated String classType,
        @Parameter(name = "SearchText", deprecated = true, description = "Optional filter text.<br>Ignored when UseNestedClasses = true<br> ! This option is not yet implemented.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "SearchText", required = false) @Deprecated String searchText,
        @Parameter(name = "RelatedIfcEntity", deprecated = true, description = "Optional filter on related IFC entity. It accepts an IFC entity code (e.g. IfcWall) or uri (e.g. *URI placeholder*).<br> When a code is supplied, finding matching classes ignores the IFC version.<br> Ignored when UseNestedClasses = true<br> ! This option is not yet implemented.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "RelatedIfcEntity", required = false) @Deprecated String relatedIfcEntity,
        @Parameter(name = "Offset", description = "Zero-based offset of the first item to be returned. Default is 0.<br> ! This option is not yet implemented.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "Offset", required = false) Integer offset,
        @Parameter(name = "Limit", description = "Limit number of items to be returned. The default and maximum number of items returned is 1000.<br> When Offset is specified, then the default limit is 100.<br> ! This option is not yet implemented.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "Limit", required = false) Integer limit,
        @Parameter(name = "languageCode", description = "Specify language (case sensitive).<br> For those items the text is not available in the requested language, the text will be returned in the default language of the dictionary<br> ! This option is not yet implemented.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "languageCode", required = false) String languageCode
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"organizationNameOwner\" : \"organizationNameOwner\", \"licenseUrl\" : \"licenseUrl\", \"code\" : \"code\", \"isLatestVersion\" : true, \"isVerified\" : true, \"releaseDate\" : \"2000-01-23T04:56:07.000+00:00\", \"lastUpdatedUtc\" : \"2000-01-23T04:56:07.000+00:00\", \"classes\" : [ { \"parentClassCode\" : \"parentClassCode\", \"code\" : \"code\", \"children\" : [ null, null ], \"name\" : \"name\", \"referenceCode\" : \"referenceCode\", \"uri\" : \"uri\", \"classType\" : \"classType\", \"descriptionPart\" : \"descriptionPart\" }, { \"parentClassCode\" : \"parentClassCode\", \"code\" : \"code\", \"children\" : [ null, null ], \"name\" : \"name\", \"referenceCode\" : \"referenceCode\", \"uri\" : \"uri\", \"classType\" : \"classType\", \"descriptionPart\" : \"descriptionPart\" } ], \"uri\" : \"uri\", \"version\" : \"version\", \"organizationCodeOwner\" : \"organizationCodeOwner\", \"license\" : \"license\", \"qualityAssuranceProcedure\" : \"qualityAssuranceProcedure\", \"classesTotalCount\" : 0, \"classesCount\" : 1, \"name\" : \"name\", \"moreInfoUrl\" : \"moreInfoUrl\", \"classesOffset\" : 6, \"defaultLanguageCode\" : \"defaultLanguageCode\", \"qualityAssuranceProcedureUrl\" : \"qualityAssuranceProcedureUrl\", \"status\" : \"status\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /api/Dictionary/v1 : [IN REWORK]Get list of available dictionaries (i.e. XtdBag) with optional filtering.
     * Following features of this endpoint are not implemented yet:
     *
     * @param URI Optional filtering, URI of a specific dictionary, e.g. &lt;br&gt; DATACAT: https://datacat.org/model/34mDkKGrz2FhzL8laZhy9W&lt;br&gt;  CAFM: &lt;br&gt; IBPDI: https://ibpdi.datacat.org/model/800da571-b537-4549-9237-11568678ef9a (optional)
     * @param includeTestDictionaries Should test dictionaries be included in the result? By default it is set to false.  This option is ignored if you specify a URI. (optional)
     * @param offset Zero-based offset of the first item to be returned. Default is 0. (optional)
     * @param limit Limit number of items to be returned. The default and maximum number of items returned is 1000. When Offset is specified, then the default limit is 100. (optional)
     * @return Success (status code 200)
     */
    @Operation(
        operationId = "dictionaryGet",
        summary = "[IN REWORK]Get list of available dictionaries (i.e. XtdBag) with optional filtering.",
        description = "Following features of this endpoint are not implemented yet:",
        tags = { "Dictionary" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = DictionaryResponseContractV1.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/api/Dictionary/v1",
        produces = { "application/json" }
    )
    
    default ResponseEntity<DictionaryResponseContractV1> dictionaryGet(
        @Parameter(name = "URI", description = "Optional filtering, URI of a specific dictionary, e.g. <br> DATACAT: https://datacat.org/model/34mDkKGrz2FhzL8laZhy9W<br>  CAFM: <br> IBPDI: https://ibpdi.datacat.org/model/800da571-b537-4549-9237-11568678ef9a", in = ParameterIn.QUERY) @Valid @RequestParam(value = "URI", required = false) String URI,
        @Parameter(name = "IncludeTestDictionaries", description = "Should test dictionaries be included in the result? By default it is set to false.  This option is ignored if you specify a URI.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "IncludeTestDictionaries", required = false) Boolean includeTestDictionaries,
        @Parameter(name = "Offset", description = "Zero-based offset of the first item to be returned. Default is 0.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "Offset", required = false) Integer offset,
        @Parameter(name = "Limit", description = "Limit number of items to be returned. The default and maximum number of items returned is 1000. When Offset is specified, then the default limit is 100.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "Limit", required = false) Integer limit
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"offset\" : 6, \"count\" : 1, \"totalCount\" : 0, \"dictionaries\" : [ { \"name\" : \"name\", \"uri\" : \"uri\", \"version\" : \"version\" }, { \"name\" : \"name\", \"uri\" : \"uri\", \"version\" : \"version\" } ] }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /api/Statistics : Get statistics about Datacat entries here.
     * With this endpoint the statistics, i.e. the items name and its content count (referring to EXPRESS specification from DIN EN ISO 12006-3), can be requested with GET.
     *
     * @return Success (status code 200)
     */
    @Operation(
        operationId = "getStatistics",
        summary = "Get statistics about Datacat entries here.",
        description = "With this endpoint the statistics, i.e. the items name and its content count (referring to EXPRESS specification from DIN EN ISO 12006-3), can be requested with GET.",
        tags = { "Datacat Specifics" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = StatisticsResponseContractV1.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/api/Statistics",
        produces = { "application/json" }
    )
    
    default ResponseEntity<StatisticsResponseContractV1> getStatistics(
        
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
