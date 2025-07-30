package datacat.configuration;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.tags.Tag;

@Configuration
public class SpringDocConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Datacat REST API")
                        .description(
                                "<p>OpenAPI-based API to access the Datacat catalogue system.</br></p> <h3>Upcoming changes:</h3> <ul><li>v2.X.X \"major endpoint overhaul\"<ul>     <li>step-by-step inclusion of missing endpoints</li></ul></li></ul> <h3>Latest changes:</h3> <ul> <li>v2.0.0 \"major backend changes\"<ul><li>Updated endpoints for datacat 2.0</li></ul></li> </ul>")
                        .contact(new Contact()
                                .name("Support/Contact")
                                .url("https://github.com/dd-bim")
                                .email("sebastian.schilling@htw-dresden.de"))
                        .license(new License()
                                .name("GPLv3 Lizenz")
                                .url("https://www.gnu.org/licenses/gpl-3.0.html"))
                        .version("2.0.0"))
                .tags(List.of(
                        new Tag().name("Class").description("Operations about classes"),
                        new Tag().name("Dictionary").description("Operations about dictionaries"),
                        new Tag().name("Popular Dictionary").description("Operations related to popular dictionaries"),
                        new Tag().name("Property").description("Operations about properties"),
                        new Tag().name("Search").description("Operations related to search"),
                        new Tag().name("Lookup Data").description("Operations related to lookup data"),
                        new Tag().name("Datacat Specifics").description("Datacat specific operations")));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/**")
                .addOpenApiCustomizer(openApi -> {
                    // Info komplett überschreiben
                    openApi.setInfo(customOpenAPI().getInfo());
                    // Tags in der gewünschten Reihenfolge erzwingen
                    openApi.setTags(customOpenAPI().getTags());
                })
                .build();
    }
}
