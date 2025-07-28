package datacat.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LanguageDto {
    
    @JsonProperty("code")
    private String code;
    
    @JsonProperty("name")
    private String name;
    
    // Konstruktoren
    public LanguageDto() {}
    
    public LanguageDto(String code, String name) {
        this.code = code;
        this.name = name;
    }
    
    // Getters und Setters
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
