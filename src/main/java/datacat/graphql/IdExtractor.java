package datacat.graphql;

import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

// =====================================================================================================================
// C O M P O N E N T   S E C T I O N
// this handler provides a method to extract the ID from the given URI in the Swagger UI
// =====================================================================================================================
@Component
public class IdExtractor {

    public static String extractIdFromUri(String uri, String prefix) throws URISyntaxException {
        URI parsedUri = new URI(uri);
        String path = parsedUri.getPath();
        if (!path.startsWith(prefix)) {
            throw new IllegalArgumentException("URI does not contain the expected prefix: " + prefix);
        }
        return path.substring(prefix.length());
    }
}