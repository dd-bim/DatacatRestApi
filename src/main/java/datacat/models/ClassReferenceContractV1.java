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
@JsonTypeName("ClassReferenceContract.v1")
public class ClassReferenceContractV1 {

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("name")
    private String name;

    @JsonProperty("code")
    private String code;


    // =====================================================================================================================
    // setting default values
    public ClassReferenceContractV1() {
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

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }


    // =====================================================================================================================
    // business logic method
    public void generateUri(String serverUrl) {
        if(this.uri != null) {
            this.uri = serverUrl + "/class/" + this.uri;
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
        ClassReferenceContractV1 that = (ClassReferenceContractV1) o;
        return 
            Objects.equals(uri, that.uri) &&
            Objects.equals(name, that.name) &&
            Objects.equals(code, that.code);
    }


    @Override
    public int hashCode() {
        return Objects.hash(
            uri,
            name,
            code
        );
    }

    @Override
    public String toString() {
        return 
            "ClassReferenceContractV1{" +
            "uri=" + uri + '\'' +
            ", name=" + name + '\'' +
            ", code=" + code + '\'' +
            "}";
    }
}
