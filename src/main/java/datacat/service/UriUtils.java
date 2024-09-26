package datacat.service;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
// Spring Boot
import org.springframework.stereotype.Component;

// Java
import java.net.URI;
import java.net.URISyntaxException;
// import org.slf4j.*;
// import org.springframework.http.*;
// import org.springframework.web.client.HttpServerErrorException;
// import datacat.models.*;
// import datacat.auth.AuthenticationService;

// =====================================================================================================================
// C O M P O N E N T   S E C T I O N
// 
// =====================================================================================================================
@Component
public class UriUtils {

    // private static final Logger logger = LoggerFactory.getLogger(UriUtils.class);

    public static String extractIdFromUri(String uri, String prefix) throws URISyntaxException {
        URI parsedUri = new URI(uri);
        String path = parsedUri.getPath();
        if (!path.startsWith(prefix)) {
            throw new IllegalArgumentException("URI does not contain the expected prefix: " + prefix);
        }
        return path.substring(prefix.length());
    }
}