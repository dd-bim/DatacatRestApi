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
@JsonTypeName("UnitContract.v1")
public class UnitContractV1 {

    @JsonProperty("code")
    private String code;

    @JsonProperty("name")
    private String name;

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("qudtUri")
    private String qudtUri;


    // =====================================================================================================================
    // setting default values
    public UnitContractV1() {
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

    public String getSymbol() {
        return symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getQudtUri() {
        return qudtUri;
    }
    public void setQudtUri(String qudtUri) {
        this.qudtUri = qudtUri;
    }


    // =====================================================================================================================
    // business logic method
    public void generateUri(String serverUrl) {
        if(this.qudtUri != null) {
            this.qudtUri = serverUrl + "/unit/" + this.qudtUri;
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
        UnitContractV1 that = (UnitContractV1) o;
        return 
            Objects.equals(code, that.code) &&
            Objects.equals(name, that.name) &&
            Objects.equals(symbol, that.symbol) &&
            Objects.equals(qudtUri, that.qudtUri);
    }


    @Override
    public int hashCode() {
        return Objects.hash(
            code,
            name,
            symbol,
            qudtUri
        );
    }

    @Override
    public String toString() {
        return 
            "UnitContractV1{" +
            "code=" + code + '\'' +
            ", name=" + name + '\'' +
            ", symbol=" + symbol + '\'' +
            ", qudtUri=" + qudtUri + '\'' +
            "}";
    }
}
