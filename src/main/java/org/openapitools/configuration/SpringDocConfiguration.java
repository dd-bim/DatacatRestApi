// package org.openapitools.configuration;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// import io.swagger.v3.oas.models.OpenAPI;
// import io.swagger.v3.oas.models.info.Info;
// import io.swagger.v3.oas.models.info.Contact;
// import io.swagger.v3.oas.models.info.License;
// import io.swagger.v3.oas.models.Components;
// import io.swagger.v3.oas.models.security.SecurityScheme;

// @Configuration
// public class SpringDocConfiguration {

//     @Bean(name = "org.openapitools.configuration.SpringDocConfiguration.apiInfo")
//     OpenAPI apiInfo() {
//         return new OpenAPI()
//                 .info(
//                         new Info()
//                                 .title("Datacat REST API v1.0.5")
//                                 .description("<p>Web facade to access the Datacat catalogue system.</br></p> <h3>Latest changes:</h3> <ul><li>v1.0.X \"major endpoint overhaul\"<ul>     <li>general rework of api/Class/v1</li>     <li>general rework of api/Dictionary/v1/Classes (upcoming)</li>     <li>including filter options (missing business logic)</li>     <li>schema adjustment (including non-implemented attributes)</li>     <li>backend logic and query rework</li>     <li>minor UI adjustments</li>     <li>improved accessibility of specification</li> </ul><li>v1.0.0 \"bSDD-like prototype\"<ul>     <li>adjustment of existing endpoints to match bSDD</li>     <li>including URI usage (partly missing business logic)</li>     <li>schema rework (renaming, parameter changes, etc.)</li>     <li>removed porxy settings</li>     <li>published under CAFM and IBPDI demain</li>     <li>minor changes to the UI</li> </ul></ul></li></ul>")
//                                 .contact(
//                                         new Contact()
//                                                 .name("Support/Contact")
//                                                 .url("https://github.com/dd-bim")
//                                                 .email("lennart.hildebrandt@htw-dresden.de")
//                                 )
//                                 .license(
//                                         new License()
//                                                 .name("* licence name placeholder *")
//                                                 .url("http://unlicense.org")
//                                 )
//                                 .version("1.0.5")
//                 )
//         ;
//     }
// }