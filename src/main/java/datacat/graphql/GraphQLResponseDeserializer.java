package datacat.graphql;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

// =====================================================================================================================
// C O M P O N E N T   S E C T I O N
// this component provides deserialization logic for the JSON responses from the GraphQL API
// in this class there are three different methods to deserialize the inner fields (inside of nodes array) of the 
// JSON responses for find queries, because I haven't found a generalized solution (working for all) yet
// =====================================================================================================================
@Slf4j
@Component
public class GraphQLResponseDeserializer {

    private final ObjectMapper objectMapper;

    public GraphQLResponseDeserializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    // =====================================================================================================================
    // D E S E R I A L I Z A T I O N M E T H O D S
    // =====================================================================================================================
    // get responses
    public <T> T deserializeGetResponse(String response, String rootField, Class<T> modelType) {
        try {
            JsonNode rootNode = objectMapper.readTree(response); // represents the entire JSON response
            log.debug("Root Node: {}", rootNode);
            JsonNode dataNode = rootNode.path("data").path(rootField); // skips the 'data' node and navigates to the
                                                                       // rootField node
            log.debug("Data Node: {}", dataNode);

            if (dataNode.isMissingNode()) { // checks if the data node is missing (e.g. when the query is invalid)
                log.error("No '{}' field in the 'data' response", rootField);
                return null;
            }

            T result = objectMapper.treeToValue(dataNode, modelType); // deserializes the single object
            log.debug("Deserialized Result: {}", result);
            return result;

        } catch (Exception e) {
            log.error("Error deserializing response", e);
            return null;
        }
    }

    // =====================================================================================================================
    // outer find response
    public <T> T deserializeOuterFindResponse(String response, String rootField, Class<T> modelType) {
        try {
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode dataNode = rootNode.path("data").path(rootField);
            log.debug("Data Node: {}", dataNode);

            if (dataNode.isMissingNode()) {
                log.error("No '{}' field in the 'data' response", rootField);
                return null;
            }

            T result = objectMapper.treeToValue(dataNode, modelType);
            return result;

        } catch (Exception e) {
            log.error("Error deserializing response", e);
            return null;
        }
    }

    // =====================================================================================================================
    // generische inner find response
    public <T> List<T> deserializeInnerFindResponse(String response, String rootField, Class<T> modelType, String nestedFieldName) {
        try {
            JsonNode rootNode = objectMapper.readTree(response); // represents the entire JSON response
            JsonNode dataNode = rootNode.path("data").path(rootField); // skips the 'data' node and navigates to the
                                                                       // rootField node

            if (dataNode.isMissingNode()) { // checks if the data node is missing (e.g. when the query is invalid)
                log.error("No '{}' field in the 'data' response", rootField);
                return null;
            }

            JsonNode nodesNode = dataNode.path("nodes"); // navigates to the nodes array
            log.debug("Nodes Node: {}", nodesNode);

            List<T> result = new ArrayList<>();
            for (JsonNode node : nodesNode) {
                log.debug("Processing Node: {}", node);
                try {
                    if (nestedFieldName == null) {
                        // Direct deserialization (für Dictionary und General)
                        T item = objectMapper.treeToValue(node, modelType);
                        log.debug("Deserialized Item: {}", item);
                        result.add(item);
                    } else {
                        // Nested array deserialization (für Class Properties und Property Classes)
                        JsonNode nestedArrayNode = node.path(nestedFieldName);
                        if (nestedArrayNode.isArray()) {
                            for (JsonNode nestedNode : nestedArrayNode) {
                                T item = objectMapper.treeToValue(nestedNode, modelType);
                                log.debug("Deserialized Item: {}", item);
                                result.add(item);
                            }
                        } else {
                            log.warn("Expected an array for '{}' but found: {}", nestedFieldName, nestedArrayNode);
                        }
                    }
                } catch (Exception e) {
                    log.error("Error deserializing node: {}", node, e);
                }
            }

            if (result.isEmpty()) {
                log.error("No information found in the '{}' field", rootField);
                return null;
            }

            return result;

        } catch (Exception e) {
            log.error("Error deserializing response", e);
            return null;
        }
    }

    // =====================================================================================================================
    // Wrapper-Methoden für Backward-Kompatibilität
    
    /**
     * General inner find response - deserialisiert direkt aus den Nodes
     */
    public <T> List<T> deserializeGeneralInnerFindResponse(String response, String rootField, Class<T> modelType) {
        return deserializeInnerFindResponse(response, rootField, modelType, null);
    }

    /**
     * Dictionary inner find response - deserialisiert direkt aus den Nodes
     */
    public <T> List<T> deserializeDictionaryInnerFindResponse(String response, String rootField, Class<T> modelType) {
        return deserializeInnerFindResponse(response, rootField, modelType, null);
    }

    /**
     * Class inner find response - extrahiert aus classProperties Array
     */
    public <T> List<T> deserializeClassInnerFindResponse(String response, String rootField, Class<T> modelType) {
        return deserializeInnerFindResponse(response, rootField, modelType, "classProperties");
    }

    /**
     * Property classes inner find response - extrahiert aus propertyClasses Array
     */
    public <T> List<T> deserializePropertyClassesInnerFindResponse(String response, String rootField, Class<T> modelType) {
        return deserializeInnerFindResponse(response, rootField, modelType, "propertyClasses");
    }
    
    /**
     * Extrahiert einen URI-Wert aus der ersten Node einer find Response
     * @param response Die GraphQL Response
     * @param rootField Das root field (z.B. "findProperties", "findSubjects")
     * @param fieldName Das Feld aus dem der URI extrahiert werden soll (z.B. "propertyUri", "classUri")
     * @return Der extrahierte URI oder null
     */
    public String extractUriFromFindResponse(String response, String rootField, String fieldName) {
        try {
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode dataNode = rootNode.path("data").path(rootField);
            
            if (dataNode.isMissingNode()) {
                log.error("No '{}' field in the 'data' response", rootField);
                return null;
            }
            
            JsonNode nodesNode = dataNode.path("nodes");
            if (nodesNode.isArray() && nodesNode.size() > 0) {
                JsonNode firstNode = nodesNode.get(0);
                if (firstNode.has(fieldName)) {
                    String uri = firstNode.get(fieldName).asText();
                    log.debug("Extracted {}: {}", fieldName, uri);
                    return uri;
                }
            }
            
        } catch (Exception e) {
            log.error("Error extracting {} from response", fieldName, e);
        }
        return null;
    }
    
    /**
     * Extrahiert die propertyUri aus der findProperties Response
     * findProperties -> nodes[0] -> propertyUri
     */
    public String extractPropertyUriFromFindResponse(String response, String rootField) {
        return extractUriFromFindResponse(response, rootField, "propertyUri");
    }
    
    /**
     * Extrahiert die classUri aus der findSubjects Response
     * findSubjects -> nodes[0] -> classUri
     */
    public String extractClassUriFromFindResponse(String response, String rootField) {
        return extractUriFromFindResponse(response, rootField, "classUri");
    }
}
