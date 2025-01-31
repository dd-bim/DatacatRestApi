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
@JsonTypeName("ClassRelationContract.v1")
public class ClassRelationContractV1 {

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("relationType")
    private String relationType;

    @JsonProperty("relatedClassUri")
    private String relatedClassUri;

    @JsonProperty("relatedClassName")
    private String relatedClassName;

    @JsonProperty("fraction")
    private Double fraction;


    // =====================================================================================================================
    // setting default values
    public ClassRelationContractV1() {
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

    public String getRelatedClassUri() {
        return relatedClassUri;
    }
    public void setRelatedClassUri(String relatedClassUri) {
        this.relatedClassUri = relatedClassUri;
    }

    public String getRelatedClassName() {
        return relatedClassName;
    }
    public void setRelatedClassName(String relatedClassName) {
        this.relatedClassName = relatedClassName;
    }

    public Double getFraction() {
        return fraction;
    }
    public void setFraction(Double fraction) {
        this.fraction = fraction;
    }


    // =====================================================================================================================
    // business logic method
    public void generateUri(String serverUrl) {
        if(this.uri != null) {
            this.uri = serverUrl + "/relations/" + this.uri; // pretty sure, the base path is wrong
            this.relatedClassUri = serverUrl + "/class/" + this.relatedClassUri;
        }
    }


    // standard object methods equals, hashCode, and toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassRelationContractV1 that = (ClassRelationContractV1) o;
        return 
            Objects.equals(uri, that.uri) &&
            Objects.equals(relationType, that.relationType) &&
            Objects.equals(relatedClassUri, that.relatedClassUri) &&
            Objects.equals(relatedClassName, that.relatedClassName) &&
            Objects.equals(fraction, that.fraction);
    }


    @Override
    public int hashCode() {
        return Objects.hash(
            uri,
            relationType,
            relatedClassUri,
            relatedClassName,
            fraction
        );
    }

    @Override
    public String toString() {
        return 
            "ClassRelationContractV1{" +
            "uri=" + uri + '\'' +
            ", relationType=" + relationType + '\'' +
            ", relatedClassUri=" + relatedClassUri + '\'' +
            ", relatedClassName=" + relatedClassName + '\'' +
            ", fraction=" + fraction + '\'' +
            "}";
    }
}
