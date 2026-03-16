package datacat.models.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import datacat.models.ClassPropertyContractV1;
import datacat.models.ClassPropertyValueContractV1;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ClassPropertyContractV1Deserializer extends JsonDeserializer<ClassPropertyContractV1> {
    
    @Override
    public ClassPropertyContractV1 deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.readValueAsTree();
        ClassPropertyContractV1 property = new ClassPropertyContractV1();
        
        // Einfache String-Felder
        if (node.has("name")) {
            property.setName(node.get("name").asText());
        }
        
        if (node.has("uri")) {
            property.setUri(node.get("uri").asText());
        }
        
        if (node.has("uid")) {
            // Map uid to uri if uri is not present
            if (property.getUri() == null) {
                property.setUri(node.get("uid").asText());
            }
        }
        
        if (node.has("description")) {
            property.setDescription(node.get("description").asText());
        }
        
        if (node.has("propertyCode")) {
            property.setPropertyCode(node.get("propertyCode").asText());
        }
        
        // Units aus einfacher units Struktur extrahieren
        if (node.has("units")) {
            List<String> units = extractUnitsFromSimpleStructure(node.get("units"));
            property.setUnits(units);
        }
        
        // Allowed Values aus allowedValues Struktur extrahieren
        if (node.has("allowedValues")) {
            List<ClassPropertyValueContractV1> allowedValues = extractAllowedValuesFromStructure(node.get("allowedValues"));
            property.setAllowedValues(allowedValues);
        }
        
        // DataType - Präfix "XTD_" entfernen und formatieren (z.B. XTD_STRING -> String)
        if (node.has("dataType")) {
            String dataType = node.get("dataType").asText();
            if (dataType != null && dataType.startsWith("XTD_")) {
                dataType = dataType.substring(4); // "XTD_" entfernen
                if (!dataType.isEmpty()) {
                    dataType = dataType.substring(0, 1).toUpperCase() + dataType.substring(1).toLowerCase();
                }
            }
            property.setDataType(dataType);
        }
        
        // Definition aus def:definition Struktur extrahieren
        if (node.has("def")) {
            String definition = extractTextFromStructure(node.get("def"), "definition");
            property.setDefinition(definition);
        }
        
        // Example aus examples Struktur extrahieren
        if (node.has("examples")) {
            String example = extractTextFromStructure(node.get("examples"), "example");
            property.setExample(example);
        }
        
        // Dictionary URI aus dictionary Struktur extrahieren
        if (node.has("dictionary")) {
            String propertyDictionaryUri = extractPropertyDictionaryUri(node.get("dictionary"));
            property.setPropertyDictionaryUri(propertyDictionaryUri);
        }

        // propertySet: pSetPropName (aus subjects) mit pSetsClassName (aus connectedSubjects) vergleichen
        List<String> pSetPropNames = extractPSetPropNames(node);
        List<String> pSetsClassNames = extractPSetsClassNames(node);
        String resolvedPropertySet = resolvePropertySet(pSetPropNames, pSetsClassNames,
                property.getPropertyCode());
            property.setPropertySet(resolvedPropertySet != null ? resolvedPropertySet : "undefined_set");

        return property;
    }
    
    /**
     * Extrahiert Units aus der units Array Struktur: "units":[] oder "units":[{"name":"meter"}]
     */
    private List<String> extractUnitsFromSimpleStructure(JsonNode unitsNode) {
        List<String> units = new ArrayList<>();
        try {
            if (unitsNode.isArray()) {
                for (JsonNode unitNode : unitsNode) {
                    if (unitNode.has("name")) {
                        String unitName = unitNode.get("name").asText();
                        if (unitName != null && !unitName.isEmpty()) {
                            units.add(unitName);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error extracting units from simple structure: " + e.getMessage());
        }
        return units;
    }
    
    /**
     * Extrahiert Allowed Values aus der neuen allowedValues Struktur:
     * allowedValues:[{"values": { "nodes":[{"sortNumber":9,"orderedValue":{"uri":"bb762202-79a1-42ea-9739-0411cde0f77e","value":"ungebunden 1","code":"ungebunden_1"}}]}}]
     */
    private List<ClassPropertyValueContractV1> extractAllowedValuesFromStructure(JsonNode allowedValuesNode) {
        List<ClassPropertyValueContractV1> allowedValues = new ArrayList<>();
        try {
            // allowedValues ist ein Array von Objekten
            if (allowedValuesNode.isArray()) {
                for (JsonNode allowedValueGroup : allowedValuesNode) {
                    JsonNode valuesNode = allowedValueGroup.path("values");
                    JsonNode nodesNode = valuesNode.path("nodes");
                    if (nodesNode.isArray()) {
                        for (JsonNode valueNode : nodesNode) {
                            ClassPropertyValueContractV1 allowedValue = new ClassPropertyValueContractV1();
                            
                            // SortNumber extrahieren (auf derselben Ebene wie orderedValue)
                            if (valueNode.has("sortNumber")) {
                                allowedValue.setSortNumber(valueNode.get("sortNumber").asInt());
                            }
                            
                            // orderedValue extrahieren
                            JsonNode orderedValueNode = valueNode.path("orderedValue");
                            if (orderedValueNode != null && !orderedValueNode.isMissingNode()) {
                                if (orderedValueNode.has("uri")) {
                                    allowedValue.setUri(orderedValueNode.get("uri").asText());
                                }
                                
                                if (orderedValueNode.has("value")) {
                                    allowedValue.setValue(orderedValueNode.get("value").asText());
                                }
                                
                                // Code extrahieren und in Kleinbuchstaben umwandeln
                                if (orderedValueNode.has("code")) {
                                    String code = orderedValueNode.get("code").asText();
                                    if (code != null && !code.isEmpty()) {
                                        allowedValue.setCode(code.toLowerCase());
                                    }
                                }
                            }
                            
                            allowedValues.add(allowedValue);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error extracting allowed values from structure: " + e.getMessage());
        }
        return allowedValues;
    }
    
    /**
     * Extrahiert Text aus einer Struktur mit texts Array:
     * "def":{"texts":[{"definition":"..."}]} oder "ex":{"texts":[{"example":"..."}]}
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
     * Extrahiert pSetPropName-Werte aus subjects[].
     * Nur Subjects, bei denen mindestens ein Tag den tagName "Merkmalsgruppe" trägt,
     * werden für den Vergleich berücksichtigt.
     */
    private static final String REQUIRED_TAG = "Merkmalsgruppe";

    private List<String> extractPSetPropNames(JsonNode propertyNode) {
        List<String> names = new ArrayList<>();
        try {
            JsonNode subjectsNode = propertyNode.path("subjects");
            if (subjectsNode.isArray()) {
                for (JsonNode n : subjectsNode) {
                    if (hasTag(n, REQUIRED_TAG) && n.has("pSetPropName") && !n.get("pSetPropName").isNull()) {
                        String name = n.get("pSetPropName").asText();
                        if (name != null && !name.isEmpty()) {
                            names.add(name);
                        }
                    }
                }
            } else if (!subjectsNode.isMissingNode() && hasTag(subjectsNode, REQUIRED_TAG)
                    && subjectsNode.has("pSetPropName")) {
                String name = subjectsNode.get("pSetPropName").asText();
                if (name != null && !name.isEmpty()) {
                    names.add(name);
                }
            }
        } catch (Exception e) {
            log.warn("Error extracting pSetPropNames: {}", e.getMessage());
        }
        return names;
    }

    /**
     * Prüft, ob ein subjects-Knoten mindestens ein Tag mit dem gegebenen tagName besitzt.
     * Struktur: { tags: [{ tagName: "..." }, ...] }
     */
    private boolean hasTag(JsonNode subjectNode, String requiredTagName) {
        JsonNode tagsNode = subjectNode.path("tags");
        if (tagsNode.isArray()) {
            for (JsonNode tag : tagsNode) {
                if (tag.has("tagName") && requiredTagName.equals(tag.get("tagName").asText())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Liest die pSetsClassNames aus dem injizierten _pSetsClassNames-Feld im Property-Node.
     * Dieses Feld wird vom Deserializer auf Subject-/Node-Ebene vor der Deserialisierung injiziert.
     */
    private List<String> extractPSetsClassNames(JsonNode propertyNode) {
        List<String> names = new ArrayList<>();
        try {
            JsonNode injected = propertyNode.path("_pSetsClassNames");
            if (injected.isArray()) {
                for (JsonNode n : injected) {
                    String name = n.asText();
                    if (name != null && !name.isEmpty()) {
                        names.add(name);
                    }
                }
            }
        } catch (Exception e) {
            log.warn("Error extracting _pSetsClassNames: {}", e.getMessage());
        }
        return names;
    }

    /**
     * Vergleicht pSetPropNames und pSetsClassNames und gibt den ersten gemeinsamen Namen zurück.
     * Wenn mehrere gemeinsame Namen existieren, wird eine Warnung geloggt.
     */
    private String resolvePropertySet(List<String> pSetPropNames, List<String> pSetsClassNames,
            String propertyCode) {
        if (pSetPropNames.isEmpty() || pSetsClassNames.isEmpty()) {
            return null;
        }
        List<String> matches = pSetPropNames.stream()
                .filter(pSetsClassNames::contains)
                .collect(Collectors.toList());
        if (matches.isEmpty()) {
            return null;
        }
        if (matches.size() > 1) {
            log.warn("Multiple matching propertySet names found for property '{}': {}. Using first: '{}'",
                    propertyCode, matches, matches.get(0));
        }
        log.debug("Resolved propertySet '{}' for property '{}'", matches.get(0), propertyCode);
        return matches.get(0);
    }

    /**
     * Extrahiert die Property Dictionary URI aus der dictionary Struktur:
     * "dictionary":{"propertyDictionaryUri":"..."}
     */
    private String extractPropertyDictionaryUri(JsonNode dictionaryNode) {
        try {
            if (dictionaryNode != null && dictionaryNode.has("propertyDictionaryUri")) {
                return dictionaryNode.get("propertyDictionaryUri").asText();
            }
        } catch (Exception e) {
            System.err.println("Error extracting property dictionary URI: " + e.getMessage());
        }
        return null;
    }
}
