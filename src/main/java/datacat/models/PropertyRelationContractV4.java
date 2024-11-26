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
@JsonTypeName("PropertyRelationContract.v4")
public class PropertyRelationContractV4 {

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("relationType")
    private String relationType;

    @JsonProperty("relatedPropertyUri")
    private String relatedPropertyUri;

    @JsonProperty("relatedPropertyName")
    private String relatedPropertyName;


    // =====================================================================================================================
    // setting default values
    public PropertyRelationContractV4() {
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

    public String getRelationType() {
        return relationType;
    }
    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    public String getRelatedPropertyUri() {
        return relatedPropertyUri;
    }
    public void setRelatedPropertyUri(String relatedPropertyUri) {
        this.relatedPropertyUri = relatedPropertyUri;
    }

    public String getRelatedPropertyName() {
        return relatedPropertyName;
    }
    public void setRelatedPropertyName(String relatedPropertyName) {
        this.relatedPropertyName = relatedPropertyName;
    }


    // =====================================================================================================================
    // business logic method
    public void generateUri(String serverUrl) {
        if(this.uri != null) {
            this.uri = serverUrl + "/BASEPATH/" + this.uri;
            this.relatedPropertyUri = serverUrl + "property" + this.relatedPropertyUri;
        }
    }

    // standard object methods equals, hashCode, and toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyRelationContractV4 that = (PropertyRelationContractV4) o;
        return 
            Objects.equals(uri, that.uri) &&
            Objects.equals(relationType, that.relationType) &&
            Objects.equals(relatedPropertyUri, that.relatedPropertyUri) &&
            Objects.equals(relatedPropertyName, that.relatedPropertyName);
    }


    @Override
    public int hashCode() {
        return Objects.hash(
            uri,
            relationType,
            relatedPropertyUri,
            relatedPropertyName
        );
    }

    @Override
    public String toString() {
        return 
            "PropertyRelationContractV4{" +
            "uri=" + uri + '\'' +
            ", relationType=" + relationType + '\'' +
            ", relatedPropertyUri=" + relatedPropertyUri + '\'' +
            ", relatedPropertyName=" + relatedPropertyName + '\'' +
            "}";
    }
}
