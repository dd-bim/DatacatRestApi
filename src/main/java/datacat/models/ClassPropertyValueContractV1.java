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
@JsonTypeName("ClassPropertyValueContract.v1")
public class ClassPropertyValueContractV1 {

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("code")
    private String code;

    @JsonProperty("value")
    private String value;

    @JsonProperty("description")
    private String description;

    @JsonProperty("sortNumber")
    private Integer sortNumber;


    // =====================================================================================================================
    // setting default values
    public ClassPropertyValueContractV1() {
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

    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSortNumber() {
        return sortNumber;
    }
    public void setSortNumber(Integer sortNumber) {
        this.sortNumber = sortNumber;
    }


    // =====================================================================================================================
    // business logic method
    public void generateUri(String serverUrl) {
        if(this.uri != null) {
            this.uri = serverUrl + "/value/" + this.uri;
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
        ClassPropertyValueContractV1 that = (ClassPropertyValueContractV1) o;
        return 
            Objects.equals(uri, that.uri) &&
            Objects.equals(code, that.code) &&
            Objects.equals(value, that.value) &&
            Objects.equals(description, that.description) &&
            Objects.equals(sortNumber, that.sortNumber);
    }


    @Override
    public int hashCode() {
        return Objects.hash(
            uri,
            code,
            value,
            description,
            sortNumber
        );
    }

    @Override
    public String toString() {
        return 
            "ClassPropertyValueContractV1{" +
            "uri=" + uri + '\'' +
            ", code=" + code + '\'' +
            ", value=" + value + '\'' +
            ", description=" + description + '\'' +
            ", sortNumber=" + sortNumber + '\'' +
            "}";
    }
}
