package datacat.models;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

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
    // non-argument constructor
    public ClassReferenceContractV1() {
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
    // standard object methods equals, hashCode, and toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassReferenceContractV1 that = (ClassReferenceContractV1) o;
        return Objects.equals(uri, that.uri) &&
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
        return "{" +
                "uri='" + uri + '\'' +
                ", name='" + name + '\'' +
                ", code=" + code +
                "}";
    }

}