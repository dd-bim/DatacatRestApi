package org.openapitools;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
// Jackson
import com.fasterxml.jackson.databind.Module;

import datacat.service.CustomProperties;

// OpenAPI
import org.openapitools.jackson.nullable.JsonNullableModule;

// Spring Boot
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FullyQualifiedAnnotationBeanNameGenerator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.EnableScheduling;

// Logging
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// =====================================================================================================================
// A N N O T A T I O N   S E C T I O N
// =====================================================================================================================
@SpringBootApplication(
    nameGenerator = FullyQualifiedAnnotationBeanNameGenerator.class
)
@ComponentScan( // component scan gathers all the components in the specified packages, not declared packages won't be seen as part of the application
    basePackages = {"org.openapitools", "org.openapitools.configuration", "datacat.restapi", "datacat.models", "datacat.auth", "datacat.service"},
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
    public CommandLineRunner logBasePackages() {
        return args -> {
            String[] basePackages = { 
                "org.openapitools", 
                "org.openapitools.configuration", 
                "datacat.restapi", 
                "datacat.models", 
                "datacat.auth", 
                "datacat.service"
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