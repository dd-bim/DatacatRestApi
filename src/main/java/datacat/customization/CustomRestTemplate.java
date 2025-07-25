package datacat.customization;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
// Spring Boot
import org.springframework.context.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;

// Java
import java.util.List;

// Internal
import datacat.auth.TokenInterceptor;

// =====================================================================================================================
// C O N F I G U R A T I O N   S E C T I O N
// this class works out an instance of a customized reusable 'RestTemplate' with integrating 'TokenInterceptor'
// =====================================================================================================================
@Configuration
public class CustomRestTemplate {

    private final TokenInterceptor tokenInterceptor;

    public CustomRestTemplate(TokenInterceptor tokenInterceptor) {
        this.tokenInterceptor = tokenInterceptor;
    }

    // RestTemplate without interceptor (for authentication)
    @Bean("authRestTemplate")
    public RestTemplate authRestTemplate(RestTemplateBuilder builder) {
        return builder
                .build();
    }

    // RestTemplate with interceptor (for other requests)
    @Bean("securedRestTemplate")
    public RestTemplate securedRestTemplate(RestTemplateBuilder builder) {
        return builder
                .interceptors(List.of(tokenInterceptor))
                .build();
    }
}