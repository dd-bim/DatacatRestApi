package datacat.auth;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
// Spring Boot
import org.springframework.http.*;
import org.springframework.context.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

// Java
import javax.annotation.PostConstruct;
import java.time.*;

// Logging
import org.slf4j.*;

// GSON
import com.google.gson.*;

// Internal
import datacat.service.CustomProperties;

// =====================================================================================================================
// S E R V I C E   S E C T I O N
// service that handles authentication and token management with refreshing mechanism for uninterrupted API usage
// ! long term token refreshing mechanism not tested !
// =====================================================================================================================
@Service
public class AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
    private final CustomProperties customProperties;
    private final RestTemplate authRestTemplate;  // only for initial authentication
    private String bearerToken;
    private Instant tokenIssueTime;
    private static final Duration TOKEN_LIFETIME = Duration.ofMinutes(1); // token lifespan

    @Autowired
    public AuthenticationService(CustomProperties customProperties,@Lazy RestTemplate authRestTemplate) {
        this.customProperties = customProperties;
        this.authRestTemplate = authRestTemplate;
    }

    public String authenticate() {
        String url = customProperties.getServerUrl() + customProperties.getBasePath();
        String username = customProperties.getUsername();
        String password = customProperties.getPassword();

        String query = "mutation Login ($username: ID!, $password: String!) { login(input: {username: $username, password: $password}) }";
        String variables = "{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String body = "{\"query\": \"" + query + "\", \"variables\": " + variables + "}";
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = authRestTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                bearerToken = extractTokenFromResponse(response.getBody());
                tokenIssueTime = Instant.now();
                logger.info("Authentication successful. Token obtained.");
                logger.info("Token expires in {} minutes", TOKEN_LIFETIME.toMinutes());
                return bearerToken;
            } else {
                logger.error("Failed to authenticate: {}", response.getStatusCode());
                throw new RuntimeException("Failed to authenticate: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            logger.error("Client error during authentication: {}", e.getStatusCode());
            throw new RuntimeException("Client error during authentication: " + e.getMessage(), e);
        } catch (HttpServerErrorException e) {
            logger.error("Server error during authentication: {}", e.getStatusCode());
            throw new RuntimeException("Server error during authentication: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Error during authentication", e);
            throw new RuntimeException("Error during authentication: " + e.getMessage(), e);
        }
    }

    private String extractTokenFromResponse(String responseBody) {
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        return jsonObject.getAsJsonObject("data").get("login").getAsString();
    }

    public String getBearerToken() {
        return bearerToken;
    }

    // =====================================================================================================================
    // scheduled authentication methods 
    @PostConstruct
    public void authenticateOnStartup() {
        authenticate();  // authenticates at application startup
        // logger.info("Token expires in {} minutes", TOKEN_LIFETIME.toMinutes());
    }

    public boolean isTokenExpired() {
        if (tokenIssueTime == null || bearerToken == null) {
            return true;
        }
        Duration tokenAge = Duration.between(tokenIssueTime, Instant.now());
        return tokenAge.compareTo(TOKEN_LIFETIME) >= 0;
    }

    public HttpHeaders getAuthorizationHeaders() {
        if (isTokenExpired()) {
            authenticate();  // re-authenticates and refreshes the token if needed
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + bearerToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}