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
@JsonTypeName("LanguageContract.v1")
public class LanguageContractV1 {

    @JsonProperty("isoCode")
    private String isoCode;

    @JsonProperty("name")
    private String name;


    // =====================================================================================================================
    // setting default values
    public LanguageContractV1() {
        DefaultValuesHandler.ensureDefaults(this);
    }

    
    // =====================================================================================================================
    // getters and setters
    public String getIsoCode() {
        return isoCode;
    }
    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    // =====================================================================================================================
    // business logic method
    // standard object methods equals, hashCode, and toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LanguageContractV1 that = (LanguageContractV1) o;
        return 
            Objects.equals(isoCode, that.isoCode) &&
            Objects.equals(name, that.name);
    }


    @Override
    public int hashCode() {
        return Objects.hash(
            isoCode,
            name
        );
    }

    @Override
    public String toString() {
        return 
            "LanguageContractV1{" +
            "isoCode=" + isoCode + '\'' +
            ", name=" + name + '\'' +
            "}";
    }
}
