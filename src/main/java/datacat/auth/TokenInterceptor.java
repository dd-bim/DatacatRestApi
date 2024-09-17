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
import org.springframework.beans.factory.annotation.Autowired;

// Logging
import org.slf4j.*;
import java.io.IOException;

// =====================================================================================================================
// C O M P O N E N T   S E C T I O N
// this component intercepts the HTTP requests, checks the validity of the token and adds it to the request headers
// is being used within the 'CustomRestTemplate' class
// =====================================================================================================================
@Component
public class TokenInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);
    private final AuthenticationService authenticationService;

    @Autowired
    public TokenInterceptor(@Lazy AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        logger.debug("Intercepting request to: {}", request.getURI());

        if (authenticationService.isTokenExpired()) { // checks if the token is expired and refreshes if necessary
            logger.info("Token is expired. Refreshing token...");
            authenticationService.authenticate();
        }

        HttpHeaders headers = request.getHeaders();
        headers.set("Authorization", "Bearer " + authenticationService.getBearerToken()); // injects token into the header

        return execution.execute(request, body);
    }
}