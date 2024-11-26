package datacat.graphql;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;
import java.util.List;

// =====================================================================================================================
// C O M P O N E N T   S E C T I O N
// this component provides deserialization logic for the JSON responses from the GraphQL API
// in this class there are three different methods to deserialize the inner fields (inside of nodes array) of the 
// JSON responses for find queries, because I haven't found a generalized solution (working for all) yet
// =====================================================================================================================
@Component
public class ResponseDeserializer {

    private static final Logger logger = LoggerFactory.getLogger(ResponseDeserializer.class);
    
    private final ObjectMapper objectMapper;

    public ResponseDeserializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    // =====================================================================================================================
    // D E S E R I A L I Z A T I O N   M E T H O D S
    // =====================================================================================================================
    // get responses
    public <T> T deserializeGetResponse(String response, String rootField, Class<T> modelType) {
        try {
            JsonNode rootNode = objectMapper.readTree(response); // represents the entire JSON response
            logger.debug("Root Node: {}", rootNode);
            JsonNode dataNode = rootNode.path("data").path(rootField); // skips the 'data' node and navigates to the rootField node
            logger.debug("Data Node: {}", dataNode);

            if (dataNode.isMissingNode()) { // checks if the data node is missing (e.g. when the query is invalid)
                logger.error("No '{}' field in the 'data' response", rootField);
                return null;
            }
    
            T result = objectMapper.treeToValue(dataNode, modelType); // deserializes the single object
            logger.debug("Deserialized Result: {}", result);
            return result;
            
        } catch (Exception e) {
            logger.error("Error deserializing response", e);
            return null;
        }
    }


    // =====================================================================================================================
    // outer find response
    public <T> T deserializeOuterFindResponse(String response, String rootField, Class<T> modelType) {
        try {
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode dataNode = rootNode.path("data").path(rootField);
            logger.debug("Data Node: {}", dataNode);

            if (dataNode.isMissingNode()) {
                logger.error("No '{}' field in the 'data' response", rootField);
                return null;
            }

            T result = objectMapper.treeToValue(dataNode, modelType);
            return result;

        } catch (Exception e) {
            logger.error("Error deserializing response", e);
            return null;
        }
    }


    // =====================================================================================================================
    // general inner find response (former find response)
    public <T> List<T> deserializeGeneralInnerFindResponse(String response, String rootField, Class<T> modelType) {
        try {
            JsonNode rootNode = objectMapper.readTree(response); // represents the entire JSON response
            JsonNode dataNode = rootNode.path("data").path(rootField); // skips the 'data' node and navigates to the rootField node

            if (dataNode.isMissingNode()) { // checks if the data node is missing (e.g. when the query is invalid)
                logger.error("No '{}' field in the 'data' response", rootField);
                return null;
            }
			
            JsonNode nodesNode = dataNode.path("nodes"); // navigates to the nodes array
            logger.debug("Nodes Node: {}", nodesNode);

            List<T> result = new ArrayList<>();
            for (JsonNode node : nodesNode) {
                logger.debug("Processing Node: {}", node);
                T item = objectMapper.treeToValue(node, modelType);
                logger.debug("Deserialized Item: {}", item);
                result.add(item);
            }

            if (result.isEmpty()) {
                logger.error("No information found in the '{}' field", rootField);
                return null;
            }

            return result;

        } catch (Exception e) {
            logger.error("Error deserializing response", e);
            return null;
        }
    }


    // =====================================================================================================================
    // inner find response
    public <T> List<T> deserializeClassInnerFindResponse(String response, String rootField, Class<T> modelType) {
        try {
            JsonNode rootNode = objectMapper.readTree(response); // represents the entire JSON response
            JsonNode dataNode = rootNode.path("data").path(rootField); // skips the 'data' node and navigates to the rootField node

            if (dataNode.isMissingNode()) { // checks if the data node is missing (e.g. when the query is invalid)
                logger.error("No '{}' field in the 'data' response", rootField);
                return null;
            }
			
            JsonNode nodesNode = dataNode.path("nodes"); // navigates to the nodes array
            logger.debug("Nodes Node: {}", nodesNode);

            List<T> result = new ArrayList<>();
            for (JsonNode node : nodesNode) {
                logger.debug("Processing Node: {}", node);
                try {
                    // Extract the nested classProperties array
                    JsonNode classPropertiesNode = node.path("classProperties");
                    if (classPropertiesNode.isArray()) {
                        for (JsonNode classPropertyNode : classPropertiesNode) {
                            T item = objectMapper.treeToValue(classPropertyNode, modelType);
                            logger.debug("Deserialized Item: {}", item);
                            result.add(item);
                        }
                    } else {
                        logger.warn("Expected an array for 'classProperties' but found: {}", classPropertiesNode);
                    }
                } catch (Exception e) {
                    logger.error("Error deserializing node: {}", node, e);
                }
            }

            if (result.isEmpty()) {
                logger.error("No information found in the '{}' field", rootField);
                return null;
            }

            return result;

        } catch (Exception e) {
            logger.error("Error deserializing response", e);
            return null;
        }
    }

    // =====================================================================================================================
    // inner find response
    public <T> List<T> deserializeDictionaryInnerFindResponse(String response, String rootField, Class<T> modelType) {
        try {
            JsonNode rootNode = objectMapper.readTree(response); // represents the entire JSON response
            JsonNode dataNode = rootNode.path("data").path(rootField); // skips the 'data' node and navigates to the rootField node

            if (dataNode.isMissingNode()) { // checks if the data node is missing (e.g. when the query is invalid)
                logger.error("No '{}' field in the 'data' response", rootField);
                return null;
            }
			
            JsonNode nodesNode = dataNode.path("nodes"); // navigates to the nodes array
            logger.debug("Nodes Node: {}", nodesNode);

            // working for dictionary
            // List<T> result = new ArrayList<>();
            // for (JsonNode node : nodesNode) {
            //     logger.debug("Processing Node: {}", node);
            //     T item = objectMapper.treeToValue(node, modelType);
            //     logger.debug("Deserialized Item: {}", item);
            //     result.add(item);
            // }

            // working for dictionary
            List<T> result = new ArrayList<>();
            for (JsonNode node : nodesNode) {
                logger.debug("Processing Node: {}", node);
                try {
                    T item = objectMapper.treeToValue(node, modelType);
                    logger.debug("Deserialized Item: {}", item);
                    result.add(item);
                } catch (Exception e) {
                    logger.error("Error deserializing node: {}", node, e);
                }
            }

            if (result.isEmpty()) {
                logger.error("No information found in the '{}' field", rootField);
                return null;
            }

            return result;

        } catch (Exception e) {
            logger.error("Error deserializing response", e);
            return null;
        }
    }


    // =====================================================================================================================
    // as list response
    public <T> List<T> deserializeResponseAsList(String response, String rootField, Class<T> modelType) {
        try {
            JsonNode rootNode = objectMapper.readTree(response); // represents the entire JSON response
            JsonNode dataNode = rootNode.path("data").path(rootField); // skips the 'data' node and navigates to the rootField node
    
            if (dataNode.isMissingNode()) { // checks if the data node is missing (e.g. when the query is invalid)
                logger.error("No '{}' field in the 'data' response", rootField);
                return new ArrayList<>();
            }
    
            JsonNode collectsNode = dataNode.path("collects").path("nodes"); // navigates to collects.nodes[].relatedThings based on the provided JSON structure
            List<T> resultList = new ArrayList<>();
    
            if (collectsNode.isArray()) { // loops through each node in collects.nodes and access relatedThings array within each node
                for (JsonNode node : collectsNode) {
                    JsonNode relatedThingsNode = node.path("relatedThings");
                    
                    if (relatedThingsNode.isArray()) { // deserialize each relatedThings entry into instances of modelType and add them to the resultList
                        List<T> relatedThings = objectMapper.treeToValue(
                            relatedThingsNode,
                            objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, modelType)
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