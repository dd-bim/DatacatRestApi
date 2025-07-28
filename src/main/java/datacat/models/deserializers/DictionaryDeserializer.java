package datacat.models.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import datacat.models.DictionaryContractV1;
import datacat.models.dto.LanguageDto;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DictionaryDeserializer extends JsonDeserializer<DictionaryContractV1> {
    
    @Override
    public DictionaryContractV1 deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.readValueAsTree();
        DictionaryContractV1 dictionary = new DictionaryContractV1();
        
        // URI setzen
        if (node.has("uri")) {
            dictionary.setUri(node.get("uri").asText());
        }
        
        // Name und Sprachen aus verschachtelter Struktur extrahieren
        if (node.has("name") && node.get("name").has("texts")) {
            JsonNode texts = node.get("name").get("texts");
            List<LanguageDto> languageObjects = new ArrayList<>();
            
            if (texts.isArray() && texts.size() > 0) {
                // Ersten Namen nehmen
                JsonNode firstText = texts.get(0);
                if (firstText.has("text")) {
                    dictionary.setName(firstText.get("text").asText());
                }
                
                // Alle Sprachen sammeln
                for (JsonNode text : texts) {
                    if (text.has("language")) {
                        JsonNode languageNode = text.get("language");
                        
                        String code = languageNode.has("code") ? languageNode.get("code").asText() : "";
                        String englishName = languageNode.has("englishName") ? languageNode.get("englishName").asText() : "";
                        
                        if (!code.isEmpty()) {
                            // Language-Objekt für availableLanguages
                            LanguageDto languageDto = new LanguageDto(code, englishName);
                            languageObjects.add(languageDto);
                        }
                    }
                }
            }
            
            // Language-Objekte setzen
            dictionary.setAvailableLanguages(languageObjects);
        }
        
        // Datumsfelder verarbeiten (da Custom Deserializer @JsonFormat überschreibt)
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        
        try {
            if (node.has("releaseDate")) {
                String releaseDateStr = node.get("releaseDate").asText();
                if (releaseDateStr != null && !releaseDateStr.isEmpty() && !releaseDateStr.equals("null")) {
                    dictionary.setReleaseDate(dateFormat.parse(releaseDateStr));
                }
            }
            
            if (node.has("lastUpdatedUtc")) {
                String lastUpdatedStr = node.get("lastUpdatedUtc").asText();
                if (lastUpdatedStr != null && !lastUpdatedStr.isEmpty() && !lastUpdatedStr.equals("null")) {
                    dictionary.setLastUpdatedUtc(dateFormat.parse(lastUpdatedStr));
                }
            }
        } catch (Exception e) {
            System.err.println("Error parsing date: " + e.getMessage());
        }

        return dictionary;
    }
}
