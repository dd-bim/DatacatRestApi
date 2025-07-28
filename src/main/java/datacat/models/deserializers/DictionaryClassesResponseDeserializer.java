package datacat.models.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import datacat.models.DictionaryClassesResponseContractV1Classes;
import datacat.models.ClassListItemContractV1Classes;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DictionaryClassesResponseDeserializer extends JsonDeserializer<DictionaryClassesResponseContractV1Classes> {
    
    @Override
    public DictionaryClassesResponseContractV1Classes deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.readValueAsTree();
        DictionaryClassesResponseContractV1Classes response = new DictionaryClassesResponseContractV1Classes();
        
        // URI setzen
        if (node.has("uri")) {
            response.setUri(node.get("uri").asText());
        }
        
        // Name aus verschachtelter Struktur extrahieren
        if (node.has("name") && node.get("name").has("texts")) {
            JsonNode texts = node.get("name").get("texts");
            if (texts.isArray() && texts.size() > 0) {
                // Ersten Namen nehmen
                JsonNode firstText = texts.get(0);
                if (firstText.has("text")) {
                    response.setName(firstText.get("text").asText());
                }
            }
        }
        
        // Concepts (Klassen) verarbeiten
        if (node.has("classes")) {
            JsonNode classes = node.get("classes");
            List<ClassListItemContractV1Classes> classList = new ArrayList<>();
            
            if (classes.isArray()) {
                for (JsonNode concept : classes) {
                    // Prüfen ob das Objekt mindestens ein nicht-leeres Feld hat
                    if (isValidClassObject(concept)) {
                        ClassListItemContractV1Classes classItem = new ClassListItemContractV1Classes();
                        
                        // URI aus concept extrahieren
                        if (concept.has("uri")) {
                            classItem.setUri(concept.get("uri").asText());
                        }
                        
                        // Name aus concept extrahieren
                        if (concept.has("name")) {
                            classItem.setName(concept.get("name").asText());
                        }
                        
                        // Code aus concept extrahieren
                        if (concept.has("code")) {
                            classItem.setCode(concept.get("code").asText());
                        }
                        
                        // ClassType aus concept extrahieren
                        if (concept.has("classType")) {
                            classItem.setClassType(concept.get("classType").asText());
                        }
                        
                        // DescriptionPart aus concept extrahieren
                        if (concept.has("descriptionPart")) {
                            classItem.setDescriptionPart(concept.get("descriptionPart").asText());
                        }
                        
                        classList.add(classItem);
                    }
                }
            }
            
            response.setClasses(classList);
        }
        
        // Datumsfelder verarbeiten (da Custom Deserializer @JsonFormat überschreibt)
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        
        try {
            if (node.has("releaseDate")) {
                String releaseDateStr = node.get("releaseDate").asText();
                if (releaseDateStr != null && !releaseDateStr.isEmpty() && !releaseDateStr.equals("null")) {
                    response.setReleaseDate(dateFormat.parse(releaseDateStr));
                }
            }
            
            if (node.has("lastUpdatedUtc")) {
                String lastUpdatedStr = node.get("lastUpdatedUtc").asText();
                if (lastUpdatedStr != null && !lastUpdatedStr.isEmpty() && !lastUpdatedStr.equals("null")) {
                    response.setLastUpdatedUtc(dateFormat.parse(lastUpdatedStr));
                }
            }
        } catch (Exception e) {
            System.err.println("Error parsing date: " + e.getMessage());
        }

        return response;
    }
    
    /**
     * Prüft ob ein JSON-Objekt mindestens ein nicht-leeres, nicht-null Feld enthält
     */
    private boolean isValidClassObject(JsonNode concept) {
        // Prüfen ob das Objekt leer ist oder nur leere/null Werte enthält
        if (concept == null || concept.isEmpty()) {
            return false;
        }
        
        // Prüfen ob mindestens eines der wichtigen Felder einen gültigen Wert hat
        return hasValidField(concept, "uri") || 
               hasValidField(concept, "name") || 
               hasValidField(concept, "code") || 
               hasValidField(concept, "classType");
    }
    
    /**
     * Prüft ob ein Feld existiert und einen nicht-leeren, nicht-null Wert hat
     */
    private boolean hasValidField(JsonNode node, String fieldName) {
        if (!node.has(fieldName)) {
            return false;
        }
        
        JsonNode field = node.get(fieldName);
        return field != null && 
               !field.isNull() && 
               (!field.isTextual() || !field.asText().trim().isEmpty());
    }
}
