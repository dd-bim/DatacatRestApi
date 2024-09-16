package datacat.service;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
// Spring Boot
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

// =====================================================================================================================
// C O M P O N E N T   S E C T I O N
// newly added custom properties from 'application.properties' should be added here as:
// private String and handled by getters and setters
// =====================================================================================================================
@Component
@ConfigurationProperties(prefix = "custom") // prefix 'custom' for properties in 'application.properties'
@Primary // with '@Primary' annotation this bean is preferred over others
public class CustomProperties {

    private String serverUrl;
    private String basePath;

    private String username;
    private String password;

    // add new properties here

    // =====================================================================================================================
    // getters and setters
    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // add new getters and setters here

}