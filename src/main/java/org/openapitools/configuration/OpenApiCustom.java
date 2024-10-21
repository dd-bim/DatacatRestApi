package org.openapitools.configuration;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
// Spring Boot
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// OpenAPI
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
// import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;

// Java
import java.util.List;

// Logging
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// =====================================================================================================================
// C O N F I G U R A T I O N  S E C T I O N
// configurations and changes of these in this section can lead to a different appearance of the Swagger UI
// configurations will override the default settings of Swagger UI given in 'SpringDocConfiguration.java'
// =====================================================================================================================
@Configuration
public class OpenApiCustom {

    private static final Logger logger = LoggerFactory.getLogger(OpenApiCustom.class);

    @Bean(name="org.openapitools.configuration.OpenApiCustom.apiInfo")
    OpenAPI apiInfo() {
        logger.info("OpenAPI configuration loaded.");
        return new OpenAPI()
            .info( 
                new Info()
                    .title("Datacat REST API v1.0.5")
                    .description("<p>Web facade to access the Datacat catalogue system.</br></p> <h3>Latest changes:</h3> <ul><li>v1.0.X \"major endpoint overhaul\"<ul>     <li>general rework of api/Class/v1</li>     <li>general rework of api/Dictionary/v1/Classes (upcoming)</li>     <li>including filter options (missing business logic)</li>     <li>schema adjustment (including non-implemented attributes)</li>     <li>backend logic and query rework</li>     <li>minor UI adjustments</li>     <li>improved accessibility of specification</li> </ul><li>v1.0.0 \"bSDD-like prototype\"<ul>     <li>adjustment of existing endpoints to match bSDD</li>     <li>including URI usage (partly missing business logic)</li>     <li>schema rework (renaming, parameter changes, etc.)</li>     <li>removed porxy settings</li>     <li>published under CAFM and IBPDI demain</li>     <li>minor changes to the UI</li> </ul></ul></li></ul>")
                    .contact(
                        new Contact()
                            .name("Support/Contact")
                            .url("https://github.com/dd-bim")
                            .email("christian.clemen@htw-dresden.de")
                    )
                    .license(
                        new License()
                            .name("* licence name placeholder *")
                            .url("http://unlicense.org")
                    )
                    .version("1.0.5")
            )
            // .servers(List.of(
            //     new Server().url("https://localhost:3001").description("Localhost"),
            //     new Server().url("https://datacat.org/api/e1").description("DATACAT"),
            //     new Server().url("https://ibpdi.datcat.org/api/e1").description("IBPDI"),
            //     new Server().url("https://cafm.datacat.org/api/e1").description("CAFM")
            // ))
            .tags(List.of(
                new Tag().name("Class").description("Operations about classes"),
                new Tag().name("Dictionary").description("Operations about dictionaries"),
                new Tag().name("Popular Dictionary").description("Operations related to popular dictionaries"),
                new Tag().name("Property").description("Operations about properties"),
                new Tag().name("Search").description("Operations related to search"),
                new Tag().name("Lookup Data").description("Operations related to lookup data"),
                new Tag().name("Datacat Specifics").description("Datacat specific operations")
            ));  
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/**")
                .build();
    }

    // =====================================================================================================================
    // bean for sorting operations, i.e. the endpoints in the Swagger UI
    // @Bean(name="org.openapitools.configuration.OpenApiCustom.sortOperationsCustomizer")
    // @Order(Ordered.HIGHEST_PRECEDENCE)
    // public OpenApiCustomizer sortOperationsCustomizer() {
    //     return openApi -> {
    //         List<String> sortedOperations = List.of("/api/Dictionary/v1/", "/api/Dictionary/v1/Classes", "/api/Dictionary/v1/Properties", "/api/Class/v1", "/api/Statistics");
    //         Map<String, PathItem> sortedPaths = openApi.getPaths().entrySet().stream()
    //             .sorted((path1, path2) -> {
    //                 int index1 = sortedOperations.indexOf(path1.getKey());
    //                 int index2 = sortedOperations.indexOf(path2.getKey());
    //                 if (index1 == -1) index1 = Integer.MAX_VALUE;
    //                 if (index2 == -1) index2 = Integer.MAX_VALUE;
    //                 return Integer.compare(index1, index2);
    //             })
    //             .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    
    //         openApi.getPaths().clear();
    //         sortedPaths.forEach(openApi::path);

    //         logger.debug(" X X X X X X X X X X X X X X ");
    //         logger.debug("Sorted Operations: {}", sortedPaths.keySet());
    //     };
    // }


}