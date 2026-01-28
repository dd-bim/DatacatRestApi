package datacat;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
// Spring Boot
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.CommandLineRunner;
import lombok.extern.slf4j.Slf4j;

// Internal
import datacat.customization.CustomProperties;

// =====================================================================================================================
// A N N O T A T I O N   S E C T I O N
// =====================================================================================================================
@SpringBootApplication
@ComponentScan( // component scan gathers all the components in the specified packages
    basePackages = {"datacat.restapi", "datacat.models", "datacat.auth", "datacat.customization", "datacat.graphql", "datacat.configuration", "datacat.services", "datacat.controller"}
)
@EnableConfigurationProperties(CustomProperties.class) // enables the use of custom properties
@EnableScheduling
@EnableRetry

// =====================================================================================================================
// M A I N   C L A S S   S E C T I O N
// =====================================================================================================================
@Slf4j
public class DatacatApplication { // former and auto generated: OpenApiGeneratorApplication.java

    public static void main(String[] args) {
        SpringApplication.run(DatacatApplication.class, args);
    }

    @Bean(name = "datacat.DatacatApplication.loggingBasePackages") // used to log the scanned components as base packages
    public CommandLineRunner loggingBasePackages() {
        return args -> {
            String[] basePackages = { 
                "datacat.restapi", // where the REST API classes are located
                "datacat.models", // where the model classes are located
                "datacat.auth",  // where the authentication classes are located
                "datacat.customization", // where the service classes are located
                "datacat.graphql", // where the GraphQL classes are located
                "datacat.configuration" // where the configuration classes are located
            };
            log.debug("Base packages scanned: {}", (Object) basePackages); // returns the actual scanned base packages
        };
    }

    @Bean(name = "datacat.DatacatApplication.onAppStart")
    public CommandLineRunner onAppStart() {
        return args -> {
            log.info("**** A P P L I C A T I O N   R E A D Y   T O   S T A R T ****");
        };
   
    }
}
