package org.openapitools.configuration;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.tags.Tag;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SpringDocConfiguration {

    @Bean(name = "org.openapitools.configuration.SpringDocConfiguration.apiInfo")
    OpenAPI apiInfo() {
        return new OpenAPI()
            .info(
                new Info()
                    .title("Datacat REST API")
                    .description("<p>OpenAPI-based API to access the Datacat catalogue system.</br></p> <h3>Upcoming changes:</h3> <ul><li>v1.2.X \"major endpoint overhaul\"<ul>     <li>step-by-step inclusion of missing endpoints</li> </ul><li></ul> <h3>Latest changes:</h3> <ul><li>v1.2.1 \"major endpoint overhaul\"<ul>     <li>putting /api/Class/Properties/v1 under rework</li>     <li>putting /api/Dictionary/v1 under rework</li>     <li>minor improvements for v1.1</li>     <li>temporarily removed server drop down menu</li> </ul><li>v1.1.1 \"ekkodale minimal requirements\"<ul>     <li>general rework of /api/Class/v1</li>     <li>general rework of /api/Dictionary/v1/Classes</li>     <li>schema adjustment (including non-implemented attributes)</li>     <li>backend logic and query rework</li>     <li>minor UI adjustments</li>     <li>improved accessibility of specification</li> </ul></li></ul>")
                    .contact(
                        new Contact()
                            .name("Support/Contact")
                            .url("https://github.com/dd-bim")
                            .email("lennart.hildebrandt@htw-dresden.de")
                    )
                    .license(
                            new License()
                                    .name("* licence name placeholder *")
                                    .url("http://unlicense.org")
                    )
                    .version("1.2.2")
    )
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

}