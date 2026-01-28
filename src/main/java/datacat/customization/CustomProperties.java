package datacat.customization;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
// Spring Boot
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

// =====================================================================================================================
// C O M P O N E N T   S E C T I O N
// this component is used to include and handle custom properties from 'application.properties'
// newly added custom properties from 'application.properties' should be added here as:
// 'private String ...' and considered by getters and setters
// =====================================================================================================================
@Component
@ConfigurationProperties(prefix = "custom")
@Data
public class CustomProperties {

    private String serverUrl;
    private String basePath;

    private String username;
    private String password;

    // HTTP Client Configuration
    private Http http = new Http();

    @Data
    public static class Http {
        private int connectionTimeout = 5000;
        private int readTimeout = 30000;
        private Pool pool = new Pool();

        @Data
        public static class Pool {
            private int maxTotal = 50;
            private int maxPerRoute = 20;
        }
    }

    // private String dictTag;
}