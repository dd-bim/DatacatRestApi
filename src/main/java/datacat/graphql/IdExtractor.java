package datacat.graphql;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

// =====================================================================================================================
// C O M P O N E N T   S E C T I O N
// this handler provides a method to extract the ID from the given URI in the Swagger UI
// =====================================================================================================================
@Component
public class IdExtractor {

    private static final Logger logger = LoggerFactory.getLogger(IdExtractor.class);

    public static String extractIdFromUri(String uri, String prefix) throws URISyntaxException {
        URI parsedUri = new URI(uri);
        String path = parsedUri.getPath();
        if (!path.startsWith(prefix)) {
            throw new IllegalArgumentException("URI does not contain the expected prefix: " + prefix);
        }
        return path.substring(prefix.length());
    }

    public List<String> extractGroupIdsFromResponse(String groupsResponse) {
        List<String> groupIds = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper(); // initializes the ObjectMapper
            JsonNode rootNode = objectMapper.readTree(groupsResponse); // parses the response into a JsonNode
    
            // the following should be transformed into an if-else statement according to the rootField (get or find)
            // currently only working for 'getBag'
            JsonNode relatedThingsNode = rootNode.at("/data/getBag/collects/nodes/0/relatedThings"); // navigates through the response to find the "relatedThings" node
            // JsonNode relatedThingsNode = rootNode.at("/data/findBags/nodes/0/collects/nodes/0/relatedThings"); 

            if (relatedThingsNode.isArray()) { // checks if the node is an array and extract all "internalGroupId" values
                for (JsonNode thingNode : relatedThingsNode) {
                    JsonNode groupIdNode = thingNode.get("internalGroupId"); // extracts and add the internalGroupId to the list
                    if (groupIdNode != null) {
                        groupIds.add(groupIdNode.asText());
                    }
                }
            }
        } catch (JsonProcessingException e) {
            logger.error("Error parsing JSON response for group IDs", e);
        }
        logger.debug("Extracted Group IDs: {}", groupIds);
        return groupIds;
    }

}