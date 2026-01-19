package datacat.models.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import datacat.models.PropertyClassItemContractV1;

import java.io.IOException;

public class PropertyClassItemContractV1Deserializer extends JsonDeserializer<PropertyClassItemContractV1> {
    
    @Override
    public PropertyClassItemContractV1 deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.readValueAsTree();
        PropertyClassItemContractV1 item = new PropertyClassItemContractV1();
        
        if (node.has("name")) {
            item.setName(node.get("name").asText());
        }
        
        if (node.has("uri")) {
            item.setUri(node.get("uri").asText());
        }
        
        if (node.has("description")) {
            item.setDescription(node.get("description").asText());
        }
        
        if (node.has("propertySet")) {
            item.setPropertySet(node.get("propertySet").asText());
        }
        
        // Dictionary URI aus verschachtelter Struktur extrahieren
        if (node.has("dictionary")) {
            String dictionaryUri = extractDictionaryUri(node.get("dictionary"));
            item.setDictionaryUri(dictionaryUri);
        }
        
        return item;
    }
    
    private String extractDictionaryUri(JsonNode dictionaryNode) {
        try {
            if (dictionaryNode != null && dictionaryNode.has("dictionaryUri")) {
                return dictionaryNode.get("dictionaryUri").asText();
            }
        } catch (Exception e) {
            System.err.println("Error extracting dictionary URI: " + e.getMessage());
        }
        return null;
    }
}