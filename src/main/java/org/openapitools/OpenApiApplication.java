package org.openapitools;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
// Spring Boot
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.CommandLineRunner;

// Jackson
import com.fasterxml.jackson.databind.Module;
import org.openapitools.jackson.nullable.JsonNullableModule;

// Logging
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Internal
import datacat.customization.CustomProperties;


// =====================================================================================================================
// A N N O T A T I O N   S E C T I O N
// =====================================================================================================================
@SpringBootApplication(
    nameGenerator = FullyQualifiedAnnotationBeanNameGenerator.class
)
@ComponentScan( // component scan gathers all the components in the specified packages, not declared packages won't be seen as part of the application
    basePackages = {"org.openapitools", "org.openapitools.configuration", "datacat.restapi", "datacat.models", "datacat.auth", "datacat.customization", "datacat.graphql"},
    nameGenerator = FullyQualifiedAnnotationBeanNameGenerator.class
)
@EnableConfigurationProperties(CustomProperties.class) // enables the use of custom properties
@EnableScheduling

// =====================================================================================================================
// M A I N   C L A S S   S E C T I O N
// =====================================================================================================================
public class OpenApiApplication { // former and auto generated: OpenApiGeneratorApplication.java

    private static final Logger logger = LoggerFactory.getLogger(OpenApiApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(OpenApiApplication.class, args);
    }

    @Bean(name = "org.openapitools.OpenApiApplication.jsonNullableModule")
    public Module jsonNullableModule() {
        return new JsonNullableModule();
    }

    @Bean(name = "org.openapitools.OpenApiApplication.loggingBasePackages") // used to log the scanned components as base packages
    public CommandLineRunner loggingBasePackages() {
        return args -> {
            String[] basePackages = { 
                "org.openapitools", // where the main class is located
                "org.openapitools.configuration", // where the configuration classes are located
                "datacat.restapi", // where the REST API classes are located
                "datacat.models", // where the model classes are located
                "datacat.auth",  // where the authentication classes are located
                "datacat.customization", // where the service classes are located
                "datacat.graphql" // where the GraphQL classes are located
            };
            logger.debug("Base packages scanned: {}", (Object) basePackages); // returns the actual scanned base packages
        };
    }

    @Bean(name = "org.openapitools.OpenApiApplication.onAppStart")
    public CommandLineRunner onAppStart() {
        return args -> {
            logger.info("**** A P P L I C A T I O N   R E A D Y   T O   S T A R T ****");
        };
    }
}