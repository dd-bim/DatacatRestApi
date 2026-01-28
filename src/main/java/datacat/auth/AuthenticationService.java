package datacat.auth;

import org.springframework.http.*;
import org.springframework.context.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;
import java.time.*;
import com.google.gson.*;
import datacat.customization.CustomProperties;
import lombok.extern.slf4j.Slf4j;

// =====================================================================================================================
// S E R V I C E   S E C T I O N
// service that handles authentication and token management with refreshing mechanism for uninterrupted API usage
// ! long term token refreshing mechanism not tested !
// =====================================================================================================================
@Slf4j
@Service
public class AuthenticationService {

    private final CustomProperties customProperties;
    private final RestTemplate authRestTemplate;  // only for initial authentication
    private String bearerToken;
    private Instant tokenIssueTime;
    private static final Duration TOKEN_LIFETIME = Duration.ofMinutes(20); // token lifespan

    public AuthenticationService(CustomProperties customProperties, @Lazy RestTemplate authRestTemplate) {
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
                log.info("Authentication successful. Token obtained.");
                log.info("Token expires in {} minutes", TOKEN_LIFETIME.toMinutes());
                return bearerToken;
            } else {
                log.error("Failed to authenticate: {}", response.getStatusCode());
                throw new RuntimeException("Failed to authenticate: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            log.error("Client error during authentication: {}", e.getStatusCode());
            throw new RuntimeException("Client error during authentication: " + e.getMessage(), e);
        } catch (HttpServerErrorException e) {
            log.error("Server error during authentication: {}", e.getStatusCode());
            throw new RuntimeException("Server error during authentication: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("Error during authentication", e);
            throw new RuntimeException("Error during authentication: " + e.getMessage(), e);
        }
    }

    private String extractTokenFromResponse(String responseBody) {
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        JsonObject dataObject = jsonObject.getAsJsonObject("data");
        if (dataObject == null || !dataObject.has("login")) {
            throw new RuntimeException("Invalid authentication response: missing 'data.login' field");
        }
        JsonElement loginElement = dataObject.get("login");
        if (loginElement == null || loginElement.isJsonNull()) {
            throw new RuntimeException("Invalid authentication response: 'login' field is null");
        }
        return loginElement.getAsString();
    }

    public String getBearerToken() {
        return bearerToken;
    }

    // =====================================================================================================================
    public void authenticateOnStartup() {
        authenticate();
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