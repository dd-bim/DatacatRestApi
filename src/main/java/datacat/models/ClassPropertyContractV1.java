package datacat.models;


// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
import java.util.*;
import jakarta.validation.Valid;
import lombok.Data;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import datacat.customization.DefaultValuesHandler;
import datacat.models.deserializers.ClassPropertyContractV1Deserializer;

// =====================================================================================================================
// M O D E L   C L A S S   S E C T I O N
// 
// =====================================================================================================================
@JsonTypeName("ClassPropertyContract.v1")
@JsonDeserialize(using = ClassPropertyContractV1Deserializer.class)
@Data
public class ClassPropertyContractV1 {

    @JsonProperty("name")
    private String name;

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("description")
    private String description;

    @JsonProperty("definition")
    private String definition;

    @JsonProperty("dataType")
    private String dataType;

    @JsonProperty("dimension")
    private String dimension;

    @JsonProperty("dimensionLength")
    private Integer dimensionLength;

    @JsonProperty("dimensionMass")
    private Integer dimensionMass;

    @JsonProperty("dimensionTime")
    private Integer dimensionTime;

    @JsonProperty("dimensionElectricCurrent")
    private Integer dimensionElectricCurrent;

    @JsonProperty("dimensionThermodynamicTemperature")
    private Integer dimensionThermodynamicTemperature;

    @JsonProperty("dimensionAmountOfSubstance")
    private Integer dimensionAmountOfSubstance;

    @JsonProperty("dimensionLuminousIntensity")
    private Integer dimensionLuminousIntensity;

    @JsonProperty("dynamicParameterPropertyCodes")
    private List<String> dynamicParameterPropertyCodes = new ArrayList<>();

    @JsonProperty("example")
    private String example;

    @JsonProperty("isDynamic")
    private Boolean isDynamic;

    @JsonProperty("isRequired")
    private Boolean isRequired;

    @JsonProperty("isWritable")
    private Boolean isWritable;

    @JsonProperty("maxExclusive")
    private Double maxExclusive;

    @JsonProperty("maxInclusive")
    private Double maxInclusive;

    @JsonProperty("minExclusive")
    private Double minExclusive;

    @JsonProperty("minInclusive")
    private Double minInclusive;

    @JsonProperty("pattern")
    private String pattern;

    @JsonProperty("physicalQuantity")
    private String physicalQuantity;

    @JsonProperty("allowedValues")
    private List<@Valid ClassPropertyValueContractV1> allowedValues = new ArrayList<>();

    @JsonProperty("predefinedValue")
    private String predefinedValue;

    @JsonProperty("propertyCode")
    private String propertyCode;

    @JsonProperty("propertyDictionaryName")
    private String propertyDictionaryName;

    @JsonProperty("propertyDictionaryUri")
    private String propertyDictionaryUri;
    
    @JsonProperty("propertyUri")
    private String propertyUri;

    @JsonProperty("propertySet")
    private String propertySet;

    @JsonProperty("propertyStatus")
    private String propertyStatus;

    @JsonProperty("propertyValueKind")
    private String propertyValueKind;

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("units")
    private List<String> units = new ArrayList<>();

    @JsonProperty("qudtCodes")
    private List<String> qudtCodes = new ArrayList<>();


    // =====================================================================================================================
    // setting default values
    public ClassPropertyContractV1() {
        DefaultValuesHandler.ensureDefaults(this);
    }

    // =====================================================================================================================
    // business logic method
    public void generateUri(String serverUrl) {
        if (this.uri != null) {
            this.uri = serverUrl + "/property/" + this.uri;
            this.propertyUri = this.uri;
        }
        // Generate URIs for all allowedValues
        if(this.allowedValues != null && !this.allowedValues.isEmpty()) {
            for(ClassPropertyValueContractV1 allowedValue : this.allowedValues) {
                if(allowedValue != null) {
                    allowedValue.generateUri(serverUrl);
                }
            }
        }
    }

    public void transformToLowerCase() {
        if(this.propertyCode != null) {
            this.propertyCode = this.propertyCode.toLowerCase();
        }
    }
}
