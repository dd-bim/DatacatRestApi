package datacat.graphql;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

// =====================================================================================================================
// C O M P O N E N T   S E C T I O N
// this handler provides
// =====================================================================================================================
@Component
public class ResponseDeserializer {


    private static final Logger logger = LoggerFactory.getLogger(ResponseDeserializer.class);
    private final ObjectMapper objectMapper;

    public ResponseDeserializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    // =====================================================================================================================
    // deserialization for easier handling as Java object and compile-time type checking
    // generic response parsing for all endpoints
    public <T> T deserializeGetResponse(String response, String rootField, Class<T> valueType) {
        try {
            JsonNode rootNode = objectMapper.readTree(response); // parses response into generic map to skip root node
            JsonNode dataNode = rootNode.path("data").path(rootField);
            if (dataNode.isMissingNode()) { // checks if the data node is missing (e.g. when the query is invalid)
                logger.error("No '{}' field in the 'data' response", rootField);
                return null;
            }
    
            T result = objectMapper.treeToValue(dataNode, valueType); // deserializes the single object
            return result;
        } catch (Exception e) {
            logger.error("Error deserializing response", e);
            return null;
        }
    }

    // deserialize find working only for class properties endpoint
    public <T> T deserializeFindResponse(String response, String rootField, Class<T> valueType) {
        try {
            JsonNode rootNode = objectMapper.readTree(response); // parses response into generic map to skip root node
            JsonNode dataNode = rootNode.path("data").path(rootField);
            if (dataNode.isMissingNode()) { // checks if the data node is missing (e.g. when the query is invalid)
                logger.error("No '{}' field in the 'data' response", rootField);
                return null;
            }
            // Navigate to the nodes array
            JsonNode nodesNode = dataNode.path("nodes");
            if (nodesNode.isArray() && nodesNode.size() > 0) {
                // Assuming we need the first node
                JsonNode firstNode = nodesNode.get(0);
                return objectMapper.treeToValue(firstNode, valueType);
            }
            logger.error("No nodes found in the '{}' field", rootField);
            return null;
        } catch (Exception e) {
            logger.error("Error deserializing response", e);
            return null;
        }
    }


    public <T> List<T> deserializeResponseAsList(String response, String rootField, Class<T> valueType) {
        try {
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode dataNode = rootNode.path("data").path(rootField);
    
            if (dataNode.isMissingNode()) {
                logger.error("No '{}' field in the 'data' response", rootField);
                return new ArrayList<>();
            }
    
            JsonNode collectsNode = dataNode.path("collects").path("nodes"); // navigates to collects.nodes[].relatedThings based on the provided JSON structure
            List<T> resultList = new ArrayList<>();
    
            if (collectsNode.isArray()) { // loops through each node in collects.nodes and access relatedThings array within each node
                for (JsonNode node : collectsNode) {
                    JsonNode relatedThingsNode = node.path("relatedThings");
                    
                    if (relatedThingsNode.isArray()) { // deserialize each relatedThings entry into instances of valueType and add them to the resultList
                        List<T> relatedThings = objectMapper.treeToValue(
                            relatedThingsNode,
                            objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, valueType)
                        );
                        resultList.addAll(relatedThings);
                    }
                }
            }
    
            return resultList;
        } catch (Exception e) {
            logger.error("Error deserializing response", e);
            return new ArrayList<>();
        }
    }


}
