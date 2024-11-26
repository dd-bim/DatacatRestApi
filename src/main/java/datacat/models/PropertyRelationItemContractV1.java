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
@JsonTypeName("PropertyRelationItemContract.v1")
public class PropertyRelationItemContractV1 {

    @JsonProperty("relationType")
    private String relationType;

    @JsonProperty("propertyUri")
    private String propertyUri;

    @JsonProperty("propertyName")
    private String propertyName;

    @JsonProperty("dictionaryUri")
    private String dictionaryUri;


    // =====================================================================================================================
    // setting default values
    public PropertyRelationItemContractV1() {
        DefaultValuesHandler.ensureDefaults(this);
    }

    
    // =====================================================================================================================
    // getters and setters
    public String getRelationType() {
        return relationType;
    }
    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    public String getPropertyUri() {
        return propertyUri;
    }
    public void setPropertyUri(String propertyUri) {
        this.propertyUri = propertyUri;
    }

    public String getPropertyName() {
        return propertyName;
    }
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getDictionaryUri() {
        return dictionaryUri;
    }
    public void setDictionaryUri(String dictionaryUri) {
        this.dictionaryUri = dictionaryUri;
    }


    // =====================================================================================================================
    // business logic method
    public void generateUri(String serverUrl) {
        if (this.propertyUri != null) {
            this.propertyUri = serverUrl + "/property/" + this.propertyUri;
        }
        if (this.dictionaryUri != null) {
            this.dictionaryUri = serverUrl + "/dictionary/" + this.dictionaryUri;
        }
    }


    // standard object methods equals, hashCode, and toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyRelationItemContractV1 that = (PropertyRelationItemContractV1) o;
        return 
            Objects.equals(relationType, that.relationType) &&
            Objects.equals(propertyUri, that.propertyUri) &&
            Objects.equals(propertyName, that.propertyName) &&
            Objects.equals(dictionaryUri, that.dictionaryUri);
    }


    @Override
    public int hashCode() {
        return Objects.hash(
            relationType,
            propertyUri,
            propertyName,
            dictionaryUri
        );
    }

    @Override
    public String toString() {
        return 
            "PropertyRelationItemContractV1{" +
            "relationType=" + relationType + '\'' +
            ", propertyUri=" + propertyUri + '\'' +
            ", propertyName=" + propertyName + '\'' +
            ", dictionaryUri=" + dictionaryUri + '\'' +
            "}";
    }
}
