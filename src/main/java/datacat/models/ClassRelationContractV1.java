package datacat.models;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

// internal
import datacat.customization.DefaultValuesHandler;

// =====================================================================================================================
// M O D E L   C L A S S   S E C T I O N
// 
// =====================================================================================================================
@JsonTypeName("ClassRelationsContract.v1")
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
    // non-argument constructor
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
    // public void generateUri(String serverUrl) {
    //     if(this.uri != null) {
    //         this.uri = serverUrl + "/BASEPATH/" + this.uri;
    //     }
    // }

    //public void transformToLowerCase() {
    //    if(this.code != null) {
    //        this.code = this.code.toLowerCase();
    //    }
    //}


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
        return "ClassRelationsContractV1{" +
                "uri=" + uri + 
                "relationType=" + relationType + 
                "relatedClassUri=" + relatedClassUri + 
                "relatedClassName=" + relatedClassName + 
                "fraction=" + fraction + 
                "}";
    }
}

