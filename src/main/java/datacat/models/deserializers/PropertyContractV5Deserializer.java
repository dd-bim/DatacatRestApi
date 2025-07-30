package datacat.models.deserializers;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import datacat.models.PropertyContractV5;
import datacat.models.PropertyValueContractV4;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

// =====================================================================================================================
// D E S E R I A L I Z E R   C L A S S
// =====================================================================================================================
@Slf4j
public class PropertyContractV5Deserializer extends JsonDeserializer<PropertyContractV5> {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    @Override
    public PropertyContractV5 deserialize(JsonParser parser, DeserializationContext context) {
        PropertyContractV5 property = new PropertyContractV5();

        try {
            JsonNode node = parser.getCodec().readTree(parser);

            // Einfache String-Felder
            if (node.has("description")) {
                property.setDescription(node.get("description").asText());
            }
            if (node.has("name")) {
                property.setName(node.get("name").asText());
            }
            if (node.has("uid")) {
                property.setUid(node.get("uid").asText());
            }
            if (node.has("uri")) {
                property.setUri(node.get("uri").asText());
            }
            if (node.has("status")) {
                property.setStatus(node.get("status").asText());
            }
            if (node.has("dataType")) {
                property.setDataType(node.get("dataType").asText());
            }
            if (node.has("code")) {
                String code = node.get("code").asText();
                if (code != null && !code.isEmpty()) {
                    property.setCode(code.toLowerCase());
                }
            }

            // Version-Felder (majorVersion und minorVersion zu versionNumber kombinieren)
            if (node.has("majorVersion") && node.has("minorVersion")) {
                int majorVersion = node.get("majorVersion").asInt(0);
                int minorVersion = node.get("minorVersion").asInt(0);
                // Kombiniere zu einer Versionsnummer (z.B. Major.Minor als Integer: 1.2 -> 12)
                int combinedVersion = majorVersion * 10 + minorVersion;
                property.setVersionNumber(combinedVersion);
            } else if (node.has("majorVersion")) {
                property.setVersionNumber(node.get("majorVersion").asInt());
            }

            // Country of Origin aus con:countryOfOrigin Struktur extrahieren
            if (node.has("con")) {
                String countryOfOrigin = extractCountryOfOrigin(node.get("con"));
                property.setCountryOfOrigin(countryOfOrigin);
            }

            // Definition aus def:definition Struktur extrahieren
            if (node.has("def")) {
                String definition = extractTextFromStructure(node.get("def"), "definition");
                property.setDefinition(definition);
            }

            // Deprecation Explanation aus dep:deprecationExplanation Struktur extrahieren
            if (node.has("dep")) {
                String deprecationExplanation = extractTextFromStructure(node.get("dep"), "deprecationExplanation");
                property.setDeprecationExplanation(deprecationExplanation);
            }

            // Example aus examples Struktur extrahieren
            if (node.has("examples")) {
                String example = extractTextFromStructure(node.get("examples"), "example");
                property.setExample(example);
            }

            // Dictionary URI aus dictionary Struktur extrahieren
            if (node.has("dictionary")) {
                String dictionaryUri = extractDictionaryUri(node.get("dictionary"));
                property.setDictionaryUri(dictionaryUri);
            }

            // Units aus units Array extrahieren
            if (node.has("units")) {
                List<String> units = extractUnitsFromStructure(node.get("units"));
                property.setUnits(units);
            }

            // Allowed Values aus allowedValues Struktur extrahieren
            if (node.has("allowedValues")) {
                List<PropertyValueContractV4> allowedValues = extractAllowedValuesFromStructure(
                        node.get("allowedValues"));
                property.setAllowedValues(allowedValues);
            }

            // Datum-Felder
            try {
                if (node.has("activationDateUtc")) {
                    String activationDateStr = node.get("activationDateUtc").asText();
                    if (activationDateStr != null && !activationDateStr.isEmpty()
                            && !activationDateStr.equals("null")) {
                        property.setActivationDateUtc(dateFormat.parse(activationDateStr));
                    }
                }

                if (node.has("revisionDateUtc")) {
                    String revisionDateStr = node.get("revisionDateUtc").asText();
                    if (revisionDateStr != null && !revisionDateStr.isEmpty() && !revisionDateStr.equals("null")) {
                        property.setRevisionDateUtc(dateFormat.parse(revisionDateStr));
                    }
                }

                if (node.has("versionDateUtc")) {
                    String versionDateStr = node.get("versionDateUtc").asText();
                    if (versionDateStr != null && !versionDateStr.isEmpty() && !versionDateStr.equals("null")) {
                        property.setVersionDateUtc(dateFormat.parse(versionDateStr));
                    }
                }
            } catch (Exception e) {
                System.err.println("Error parsing date: " + e.getMessage());
            }

        } catch (Exception e) {
            log.error("Error deserializing PropertyContractV5", e);
        }

        return property;
    }

    /**
     * Extrahiert die Dictionary URI aus der dictionary Struktur:
     * "dictionary":{"dictionaryUri":"..."}
     */
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

    /**
     * Extrahiert Text aus einer Struktur mit texts Array:
     * "def":{"texts":[{"definition":"..."}]} oder
     * "examples":{"texts":[{"example":"..."}]}
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
     * Extrahiert Units aus der units Array Struktur: "units":[] oder
     * "units":[{"name":"meter"}]
     */
    private List<String> extractUnitsFromStructure(JsonNode unitsNode) {
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
            System.err.println("Error extracting units from structure: " + e.getMessage());
        }
        return units;
    }

    /**
     * Extrahiert Allowed Values aus der allowedValues Struktur:
     * allowedValues:[{"values":[{"sortNumber":9,"orderedValue":{"uri":"...","value":"...","code":"..."}}]}]
     */
    private List<PropertyValueContractV4> extractAllowedValuesFromStructure(JsonNode allowedValuesNode) {
        List<PropertyValueContractV4> allowedValues = new ArrayList<>();
        try {
            if (allowedValuesNode.isArray()) {
                for (JsonNode allowedValueGroup : allowedValuesNode) {
                    JsonNode valuesNode = allowedValueGroup.path("values");
                    if (valuesNode.isArray()) {
                        for (JsonNode valueNode : valuesNode) {
                            PropertyValueContractV4 allowedValue = new PropertyValueContractV4();

                            // SortNumber extrahieren
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
}
