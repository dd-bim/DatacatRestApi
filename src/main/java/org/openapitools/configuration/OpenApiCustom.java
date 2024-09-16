// package org.openapitools.configuration;

// // =====================================================================================================================
// // I M P O R T   S E C T I O N
// // =====================================================================================================================
// // Spring Boot
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springdoc.core.customizers.OpenApiCustomizer;
// import org.springdoc.core.models.GroupedOpenApi;


// // OpenAPI
// import io.swagger.v3.oas.models.PathItem;
// import io.swagger.v3.oas.models.tags.Tag;
// // import io.swagger.v3.oas.models.media.Schema;
// // import io.swagger.v3.oas.annotations.OpenAPIDefinition;

// // Java
// import java.util.List;
// import java.util.stream.Collectors;
// import java.util.Map;
// import java.util.LinkedHashMap;

// // Logging
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;


// // =====================================================================================================================
// // C O N F I G U R A T I O N  S E C T I O N
// // configurations and changes of these in this section can lead to a different appearance of the Swagger UI
// // configurations will override the default settings of Swagger UI given in 'SpringDocConfiguration.java'

// // it seems that the schema customizer bean is somehow not functional
// // =====================================================================================================================
// @Configuration

// // @OpenAPIDefinition(
 
// // )

// public class OpenApiCustom {

//     private static final Logger logger = LoggerFactory.getLogger(OpenApiCustom.class);

//     @Bean
//     public GroupedOpenApi publicApi() {
//         return GroupedOpenApi.builder()
//                 .group("public")
//                 .pathsToMatch("/**")
//                 .build();
//     }

//     // =====================================================================================================================
//     // bean for sorting tags, i.e. the categories in the Swagger UI
//     @Bean(name="org.openapitools.configuration.OpenApiCustom.sortTagsCustomizer")	
//     public OpenApiCustomizer sortTagsCustomizer() {
//         return openApi -> {
//             List<String> orderedTagNames = List.of("Class", "Dictionary", "Lookup Data"); // add new categories here
//             List<Tag> sortedTags = openApi.getTags().stream()
//                     .sorted((tag1, tag2) -> {
//                         int index1 = orderedTagNames.indexOf(tag1.getName());
//                         int index2 = orderedTagNames.indexOf(tag2.getName());
//                         if (index1 == -1) index1 = Integer.MAX_VALUE; // if the tag is not found in the list, puts it at the end
//                         if (index2 == -1) index2 = Integer.MAX_VALUE;
//                         return Integer.compare(index1, index2);
//                     })
//                     .collect(Collectors.toList());
//             openApi.setTags(sortedTags);

//             logger.debug(" X X X X X X X X X X X X X X ");
//             logger.debug("Sorted Tags: {}", sortedTags.stream().map(Tag::getName).collect(Collectors.toList()));
//         };
//     }

//     // =====================================================================================================================
//     // bean for sorting operations, i.e. the endpoints in the Swagger UI
//     @Bean(name="org.openapitools.configuration.OpenApiCustom.sortOperationsCustomizer")
//     public OpenApiCustomizer sortOperationsCustomizer() {
//         return openApi -> {
//             List<String> orderedOperations = List.of("/api/Dictionary/v1/Classes", "/api/Dictionary/v1/Properties");
//             Map<String, PathItem> sortedPaths = openApi.getPaths().entrySet().stream()
//                 .sorted((path1, path2) -> {
//                     int index1 = orderedOperations.indexOf(path1.getKey());
//                     int index2 = orderedOperations.indexOf(path2.getKey());
//                     if (index1 == -1) index1 = Integer.MAX_VALUE;
//                     if (index2 == -1) index2 = Integer.MAX_VALUE;
//                     return Integer.compare(index1, index2);
//                 })
//                 .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    
//             openApi.getPaths().clear();
//             sortedPaths.forEach((key, value) -> openApi.path(key, value));

//             logger.debug(" X X X X X X X X X X X X X X ");
//             logger.debug("Sorted Operations: {}", sortedPaths.keySet());
//         };
//     }

//     // =====================================================================================================================
//     // bean for sorting schemas, i.e. the models in the Swagger UI
//     // commented out as long as the schema handling is not reworked
//     // @Bean(name="org.openapitools.configuration.OpenApiCustom.sortSchemasCustomizer")
//     // public OpenApiCustomizer sortSchemasCustomizer() {
//     //     return openApi -> {
//     //         List<String> orderedSchemas = List.of("ClassDetailsV1", "ClassesV1", "PropertiesV1", "StatisticsV1");
//     //         Map<String, Schema<?>> sortedSchemas = openApi.getComponents().getSchemas().entrySet().stream()
//     //             .sorted((schema1, schema2) -> {
//     //                 int index1 = orderedSchemas.indexOf(schema1.getKey());
//     //                 int index2 = orderedSchemas.indexOf(schema2.getKey());
//     //                 if (index1 == -1) index1 = Integer.MAX_VALUE;
//     //                 if (index2 == -1) index2 = Integer.MAX_VALUE;
//     //                 return Integer.compare(index1, index2);
//     //             })
//     //             .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

//     //         openApi.getComponents().getSchemas().clear();
//     //         sortedSchemas.forEach((key, value) -> openApi.getComponents().addSchemas(key, value));
        
//     //         logger.debug(" X X X X X X X X X X X X X X ");
//     //         logger.debug("Sorted Schemas: {}", sortedSchemas.keySet());
//     //     };
//     // }
// }