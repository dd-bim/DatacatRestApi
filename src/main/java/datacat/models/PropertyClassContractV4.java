package datacat.models;


// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
import java.util.*;
import com.fasterxml.jackson.annotation.*;
import datacat.customization.DefaultValuesHandler;

// =====================================================================================================================
// M O D E L   C L A S S   S E C T I O N
// 
// =====================================================================================================================
@JsonTypeName("PropertyClassContract.v4")
public class PropertyClassContractV4 {

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("code")
    private String code;

    @JsonProperty("name")
    private String name;

    @JsonProperty("definition")
    private String definition;

    @JsonProperty("description")
    private String description;

    @JsonProperty("propertySet")
    private String propertySet;


    // =====================================================================================================================
    // setting default values
    public PropertyClassContractV4() {
        DefaultValuesHandler.ensureDefaults(this);
    }

    
    // =====================================================================================================================
    // getters and setters
    public String getUri() {
        return uri;
    }
    public void setUri(String uri) {
        this.uri = uri;
    }

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

    public String getDefinition() {
        return definition;
    }
    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getPropertySet() {
        return propertySet;
    }
    public void setPropertySet(String propertySet) {
        this.propertySet = propertySet;
    }


    // =====================================================================================================================
    // business logic method
    public void generateUri(String serverUrl) {
        if(this.uri != null) {
            this.uri = serverUrl + "/property/" + this.uri;
        }
    }

    public void transformToLowerCase() {
        if(this.code != null) {
            this.code = this.code.toLowerCase();
        }
    }


    // standard object methods equals, hashCode, and toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyClassContractV4 that = (PropertyClassContractV4) o;
        return 
            Objects.equals(uri, that.uri) &&
            Objects.equals(code, that.code) &&
            Objects.equals(name, that.name) &&
            Objects.equals(definition, that.definition) &&
            Objects.equals(description, that.description) &&
            Objects.equals(propertySet, that.propertySet);
    }


    @Override
    public int hashCode() {
        return Objects.hash(
            uri,
            code,
            name,
            definition,
            description,
            propertySet
        );
    }

    @Override
    public String toString() {
        return 
            "PropertyClassContractV4{" +
            "uri=" + uri + '\'' +
            ", code=" + code + '\'' +
            ", name=" + name + '\'' +
            ", definition=" + definition + '\'' +
            ", description=" + description + '\'' +
            ", propertySet=" + propertySet + '\'' +
            "}";
    }
}
