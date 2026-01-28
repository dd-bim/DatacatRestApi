package datacat.customization;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
// Spring Boot
import org.springframework.context.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

// Apache HttpClient 5.x
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;

// Java
import java.util.List;

// Logging
import lombok.extern.slf4j.Slf4j;

// Internal
import datacat.auth.TokenInterceptor;

// =====================================================================================================================
// C O N F I G U R A T I O N   S E C T I O N
// this class works out an instance of a customized reusable 'RestTemplate' with integrating 'TokenInterceptor'
// and Connection Pooling for efficient HTTP connection management
// =====================================================================================================================
@Configuration
@Slf4j
public class CustomRestTemplate {

    private final TokenInterceptor tokenInterceptor;
    private final CustomProperties customProperties;

    public CustomRestTemplate(TokenInterceptor tokenInterceptor, CustomProperties customProperties) {
        this.tokenInterceptor = tokenInterceptor;
        this.customProperties = customProperties;
    }

    /**
     * Creates a pooling connection manager with configured limits.
     * This prevents connection pool exhaustion by reusing connections.
     */
    @Bean
    public PoolingHttpClientConnectionManager poolingConnectionManager() {
        PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager();
        poolingConnectionManager.setMaxTotal(customProperties.getHttp().getPool().getMaxTotal());
        poolingConnectionManager.setDefaultMaxPerRoute(customProperties.getHttp().getPool().getMaxPerRoute());
        
        log.info("Connection Pool initialized - MaxTotal: {}, MaxPerRoute: {}", 
                customProperties.getHttp().getPool().getMaxTotal(),
                customProperties.getHttp().getPool().getMaxPerRoute());
        
        return poolingConnectionManager;
    }

    /**
     * Creates an Apache HttpClient with connection pooling.
     */
    @Bean
    public CloseableHttpClient httpClient(PoolingHttpClientConnectionManager poolingConnectionManager) {
        return HttpClients.custom()
                .setConnectionManager(poolingConnectionManager)
                .build();
    }

    /**
     * Creates a request factory with configured timeouts.
     */
    @Bean
    public HttpComponentsClientHttpRequestFactory clientHttpRequestFactory(CloseableHttpClient httpClient) {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setHttpClient(httpClient);
        factory.setConnectionRequestTimeout(customProperties.getHttp().getConnectionTimeout());
        
        log.info("HTTP Request Factory configured - ConnectionTimeout: {}ms, ReadTimeout: {}ms",
                customProperties.getHttp().getConnectionTimeout(),
                customProperties.getHttp().getReadTimeout());
        
        return factory;
    }

    /**
     * RestTemplate without interceptor (for authentication).
     * Uses connection pooling and configured timeouts.
     */
    @Bean("authRestTemplate")
    public RestTemplate authRestTemplate(HttpComponentsClientHttpRequestFactory factory) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(factory);
        return restTemplate;
    }

    /**
     * RestTemplate with interceptor (for other requests).
     * Uses connection pooling, configured timeouts, and token interceptor.
     */
    @Bean("securedRestTemplate")
    public RestTemplate securedRestTemplate(HttpComponentsClientHttpRequestFactory factory) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(factory);
        restTemplate.setInterceptors(List.of(tokenInterceptor));
        return restTemplate;
    }
}