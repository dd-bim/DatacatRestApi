package org.openapitools.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SpringDocConfiguration {

    @Bean(name = "org.openapitools.configuration.SpringDocConfiguration.apiInfo")
    OpenAPI apiInfo() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Datacat REST API v1.0")
                                .description("<p>Web facade to access the Datacat catalogue system.</br></p> <h3>Latest changes:</h3> <ul><li>v1.0 \"bSDD-like prototype\"<ul><li>major endpoint overhaul (bSDD)</li>     <li>including URI usage (missing business logic)</li>     <li>schema rework (renaming, parameter changes, etc.)</li>     <li>removed porxy settings</li></ul></li></ul>")
                                .contact(
                                        new Contact()
                                                .name("Support/Contact")
                                                .url("https://github.com/dd-bim")
                                                .email("lennart.hildebrandt@htw-dresden.de")
                                )
                                .license(
                                        new License()
                                                .name("Lizenzname")
                                                .url("http://unlicense.org")
                                )
                                .version("1.0")
                )
        ;
    }
}