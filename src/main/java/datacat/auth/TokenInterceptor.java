package datacat.auth;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
// Spring Boot
import org.springframework.http.client.*;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.lang.NonNull;
import lombok.extern.slf4j.Slf4j;
// Logging
import java.io.IOException;

// =====================================================================================================================
// C O M P O N E N T   S E C T I O N
// this component intercepts the HTTP requests, checks the validity of the token and adds it to the request headers
// is being used within the 'CustomRestTemplate' class
// =====================================================================================================================
@Slf4j
@Component
public class TokenInterceptor implements ClientHttpRequestInterceptor {

    private final AuthenticationService authenticationService;

    public TokenInterceptor(@Lazy AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    @NonNull
    public ClientHttpResponse intercept(@NonNull HttpRequest request, @NonNull byte[] body, @NonNull ClientHttpRequestExecution execution) throws IOException {
        log.debug("Intercepting request to: {}", request.getURI());

        try {
            // Use the existing method that handles token expiration and refresh automatically
            HttpHeaders authHeaders = authenticationService.getAuthorizationHeaders();
            String authorizationValue = authHeaders.getFirst("Authorization");
            
            if (authorizationValue == null || authorizationValue.trim().isEmpty()) {
                log.error("No valid authorization header available for request to: {}", request.getURI());
                throw new IOException("Authentication failed: No valid authorization header available");
            }

            HttpHeaders headers = request.getHeaders();
            headers.set("Authorization", authorizationValue); // injects token into the header

            return execution.execute(request, body);
        } catch (RuntimeException e) {
            log.error("Authentication error during request to: {}", request.getURI(), e);
            throw new IOException("Authentication failed: " + e.getMessage(), e);
        }
    }
}