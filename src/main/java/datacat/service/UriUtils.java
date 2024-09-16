package datacat.service;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
// Spring Boot
import org.springframework.stereotype.Component;

// Java
import java.net.URI;
import java.net.URISyntaxException;

// =====================================================================================================================
// C O M P O N E N T   S E C T I O N
// 
// =====================================================================================================================
@Component
public class UriUtils {

    public static String extractIdFromUri(String uri, String prefix) throws URISyntaxException {
        
        URI parsedUri = new URI(uri); // parses the URI
        
        String path = parsedUri.getPath(); // gets the path from the URI
        
        if (!path.startsWith(prefix)) { // checks if the path starts with the expected prefix
            throw new IllegalArgumentException("URI does not contain the expected prefix: " + prefix);
        }
        
        return path.substring(prefix.length()); // extracts the ID by removing the prefix
    }
}