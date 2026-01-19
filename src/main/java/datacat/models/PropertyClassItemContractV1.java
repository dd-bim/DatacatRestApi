package datacat.models;


// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
import java.util.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import datacat.customization.DefaultValuesHandler;
import datacat.models.deserializers.PropertyClassItemContractV1Deserializer;

// =====================================================================================================================
// M O D E L   C L A S S   S E C T I O N
// 
// =====================================================================================================================
@JsonTypeName("PropertyClassItemContract.v1")
@JsonDeserialize(using = PropertyClassItemContractV1Deserializer.class)
public class PropertyClassItemContractV1 {

    @JsonProperty("name")
    private String name;

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("description")
    private String description;

    @JsonProperty("dictionaryUri")
    private String dictionaryUri;

    @JsonProperty("propertySet")
    private String propertySet;


    // =====================================================================================================================
    // setting default values
    public PropertyClassItemContractV1() {
        DefaultValuesHandler.ensureDefaults(this);
    }

    
    // =====================================================================================================================
    // getters and setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }
    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDictionaryUri() {
        return dictionaryUri;
    }
    public void setDictionaryUri(String dictionaryUri) {
        this.dictionaryUri = dictionaryUri;
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
            this.uri = serverUrl + "/class/" + this.uri;
        }
        if (this.dictionaryUri != null) {
            this.dictionaryUri = serverUrl + "/model/" + this.dictionaryUri;
        }
    }


    // standard object methods equals, hashCode, and toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyClassItemContractV1 that = (PropertyClassItemContractV1) o;
        return 
            Objects.equals(name, that.name) &&
            Objects.equals(uri, that.uri) &&
            Objects.equals(description, that.description) &&
            Objects.equals(dictionaryUri, that.dictionaryUri) &&
            Objects.equals(propertySet, that.propertySet);
    }


    @Override
    public int hashCode() {
        return Objects.hash(
            name,
            uri,
            description,
            dictionaryUri,
            propertySet
        );
    }

    @Override
    public String toString() {
        return 
            "PropertyClassItemContractV1{" +
            "name=" + name + '\'' +
            ", uri=" + uri + '\'' +
            ", description=" + description + '\'' +
            ", dictionaryUri=" + dictionaryUri + '\'' +
            ", propertySet=" + propertySet + '\'' +
            "}";
    }
}
