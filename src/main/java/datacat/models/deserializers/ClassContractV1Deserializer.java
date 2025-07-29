package datacat.models.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import datacat.models.ClassContractV1;
import datacat.models.ClassPropertyContractV1;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ClassContractV1Deserializer extends JsonDeserializer<ClassContractV1> {
    
    @Override
    public ClassContractV1 deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.readValueAsTree();
        ClassContractV1 classContract = new ClassContractV1();
        
        // Einfache String-Felder
        if (node.has("uid")) {
            classContract.setUid(node.get("uid").asText());
        }
        
        if (node.has("uri")) {
            classContract.setUri(node.get("uri").asText());
        }
        
        if (node.has("code")) {
            classContract.setCode(node.get("code").asText());
        }
        
        if (node.has("name")) {
            classContract.setName(node.get("name").asText());
        }
        
        if (node.has("description")) {
            classContract.setDescription(node.get("description").asText());
        }
        
        if (node.has("classType")) {
            classContract.setClassType(node.get("classType").asText());
        }
        
        // Country of Origin aus con:countryOfOrigin Struktur extrahieren
        if (node.has("con")) {
            String countryOfOrigin = extractCountryOfOrigin(node.get("con"));
            classContract.setCountryOfOrigin(countryOfOrigin);
        }
        
        // Version-Felder (majorVersion und minorVersion zu versionNumber kombinieren)
        if (node.has("majorVersion") && node.has("minorVersion")) {
            int majorVersion = node.get("majorVersion").asInt(0);
            int minorVersion = node.get("minorVersion").asInt(0);
            // Kombiniere zu einer Versionsnummer (z.B. Major.Minor als Integer: 1.2 -> 12)
            int combinedVersion = majorVersion * 10 + minorVersion;
            classContract.setVersionNumber(combinedVersion);
        } else if (node.has("majorVersion")) {
            classContract.setVersionNumber(node.get("majorVersion").asInt());
        }
        
        // Dictionary URI aus einfacher dictionary Struktur extrahieren
        if (node.has("dictionary")) {
            String dictionaryUri = extractDictionaryUri(node.get("dictionary"));
            classContract.setDictionaryUri(dictionaryUri);
        }
        
        // Document Reference aus einfacher referenceDocuments Struktur extrahieren
        if (node.has("referenceDocuments")) {
            String documentReference = extractDocumentReference(node.get("referenceDocuments"));
            classContract.setDocumentReference(documentReference);
        }
        
        // Definition aus def:definition Struktur extrahieren
        if (node.has("def")) {
            String definition = extractTextFromStructure(node.get("def"), "definition");
            classContract.setDefinition(definition);
        }
        
        // Deprecation Explanation aus dep:deprecationExplanation Struktur extrahieren
        if (node.has("dep")) {
            String deprecationExplanation = extractTextFromStructure(node.get("dep"), "deprecationExplanation");
            classContract.setDeprecationExplanation(deprecationExplanation);
        }
        
        // Properties explizit verarbeiten
        if (node.has("classProperties")) {
            List<ClassPropertyContractV1> properties = extractClassProperties(node.get("classProperties"));
            classContract.setClassProperties(properties);
        }
        
        // Datumsfelder verarbeiten (da Custom Deserializer @JsonFormat überschreibt)
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        
        try {
            if (node.has("activationDateUtc")) {
                String activationDateStr = node.get("activationDateUtc").asText();
                if (activationDateStr != null && !activationDateStr.isEmpty() && !activationDateStr.equals("null")) {
                    classContract.setActivationDateUtc(dateFormat.parse(activationDateStr));
                }
            }
            
            if (node.has("revisionDateUtc")) {
                String revisionDateStr = node.get("revisionDateUtc").asText();
                if (revisionDateStr != null && !revisionDateStr.isEmpty() && !revisionDateStr.equals("null")) {
                    classContract.setRevisionDateUtc(dateFormat.parse(revisionDateStr));
                }
            }
            
            if (node.has("versionDateUtc")) {
                String versionDateStr = node.get("versionDateUtc").asText();
                if (versionDateStr != null && !versionDateStr.isEmpty() && !versionDateStr.equals("null")) {
                    classContract.setVersionDateUtc(dateFormat.parse(versionDateStr));
                }
            }
        } catch (Exception e) {
            System.err.println("Error parsing date: " + e.getMessage());
        }
        
        return classContract;
    }
    
    /**
     * Extrahiert die Dictionary URI aus der einfachen dictionary Struktur
     */
    private String extractDictionaryUri(JsonNode dictionaryNode) {
        try {
            return dictionaryNode.path("dictionaryUri").asText(null);
        } catch (Exception e) {
            System.err.println("Error extracting dictionary URI: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Extrahiert die Document Reference aus der referenceDocuments Array Struktur:
     * "referenceDocuments":[{"documentReference":"BIM-Klassen der Verkehrswege - 1.01"}]
     */
    private String extractDocumentReference(JsonNode referenceDocumentsNode) {
        try {
            if (referenceDocumentsNode.isArray() && referenceDocumentsNode.size() > 0) {
                // Nimm das erste Element aus dem Array
                JsonNode firstRefDoc = referenceDocumentsNode.get(0);
                return firstRefDoc.path("documentReference").asText(null);
            }
        } catch (Exception e) {
            System.err.println("Error extracting document reference: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Extrahiert die Class Properties und verwendet den ClassPropertyContractV1Deserializer
     */
    private List<ClassPropertyContractV1> extractClassProperties(JsonNode classPropertiesNode) {
        List<ClassPropertyContractV1> properties = new ArrayList<>();
        try {
            if (classPropertiesNode.isArray()) {
                ObjectMapper mapper = new ObjectMapper();
                ClassPropertyContractV1Deserializer propertyDeserializer = new ClassPropertyContractV1Deserializer();
                
                for (JsonNode propertyNode : classPropertiesNode) {
                    ClassPropertyContractV1 property = propertyDeserializer.deserialize(
                        propertyNode.traverse(mapper), 
                        null
                    );
                    if (property != null) {
                        properties.add(property);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error extracting class properties: " + e.getMessage());
        }
        return properties;
    }
    
    /**
     * Extrahiert Text aus einer Struktur mit texts Array:
     * "def":{"texts":[{"definition":"..."}]}
     */
    private String extractTextFromStructure(JsonNode structureNode, String textType) {
        try {
            if (structureNode != null && structureNode.has("texts")) {
                JsonNode textsNode = structureNode.get("texts");
                if (textsNode.isArray() && textsNode.size() > 0) {
                    JsonNode firstText = textsNode.get(0);
                    if (firstText.has(textType)) {
                        return firstText.get(textType).asText();
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error extracting text from structure: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Extrahiert Country of Origin aus der con:countryOfOrigin Struktur:
     * "con":{"countryOfOrigin":"DE"}
     */
    private String extractCountryOfOrigin(JsonNode countryStructureNode) {
        try {
            if (countryStructureNode != null && countryStructureNode.has("countryOfOrigin")) {
                return countryStructureNode.get("countryOfOrigin").asText();
            }
        } catch (Exception e) {
            System.err.println("Error extracting country of origin: " + e.getMessage());
        }
        return null;
    }
}
