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
@JsonTypeName("CountryContract.v1")
public class CountryContractV1 {

    @JsonProperty("code")
    private String code;

    @JsonProperty("name")
    private String name;


    // =====================================================================================================================
    // setting default values
    public CountryContractV1() {
        DefaultValuesHandler.ensureDefaults(this);
    }

    
    // =====================================================================================================================
    // getters and setters
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


    // =====================================================================================================================
    // business logic method
    // standard object methods equals, hashCode, and toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountryContractV1 that = (CountryContractV1) o;
        return 
            Objects.equals(code, that.code) &&
            Objects.equals(name, that.name);
    }


    @Override
    public int hashCode() {
        return Objects.hash(
            code,
            name
        );
    }

    @Override
    public String toString() {
        return 
            "CountryContractV1{" +
            "code=" + code + '\'' +
            ", name=" + name + '\'' +
            "}";
    }
}
