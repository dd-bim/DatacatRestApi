package datacat.models;


// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
import java.util.*;
import jakarta.validation.Valid;
import com.fasterxml.jackson.annotation.*;
import datacat.customization.DefaultValuesHandler;

// =====================================================================================================================
// M O D E L   C L A S S   S E C T I O N
// 
// =====================================================================================================================
@JsonTypeName("ClassPropertyContract.v1")
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
    // getters and setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }
    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefinition() {
        return definition;
    }
    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getDataType() {
        return dataType;
    }
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDimension() {
        return dimension;
    }
    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public Integer getDimensionLength() {
        return dimensionLength;
    }
    public void setDimensionLength(Integer dimensionLength) {
        this.dimensionLength = dimensionLength;
    }

    public Integer getDimensionMass() {
        return dimensionMass;
    }
    public void setDimensionMass(Integer dimensionMass) {
        this.dimensionMass = dimensionMass;
    }

    public Integer getDimensionTime() {
        return dimensionTime;
    }
    public void setDimensionTime(Integer dimensionTime) {
        this.dimensionTime = dimensionTime;
    }

    public Integer getDimensionElectricCurrent() {
        return dimensionElectricCurrent;
    }
    public void setDimensionElectricCurrent(Integer dimensionElectricCurrent) {
        this.dimensionElectricCurrent = dimensionElectricCurrent;
    }

    public Integer getDimensionThermodynamicTemperature() {
        return dimensionThermodynamicTemperature;
    }
    public void setDimensionThermodynamicTemperature(Integer dimensionThermodynamicTemperature) {
        this.dimensionThermodynamicTemperature = dimensionThermodynamicTemperature;
    }

    public Integer getDimensionAmountOfSubstance() {
        return dimensionAmountOfSubstance;
    }
    public void setDimensionAmountOfSubstance(Integer dimensionAmountOfSubstance) {
        this.dimensionAmountOfSubstance = dimensionAmountOfSubstance;
    }

    public Integer getDimensionLuminousIntensity() {
        return dimensionLuminousIntensity;
    }
    public void setDimensionLuminousIntensity(Integer dimensionLuminousIntensity) {
        this.dimensionLuminousIntensity = dimensionLuminousIntensity;
    }

    public List<String> getDynamicParameterPropertyCodes() {
        return dynamicParameterPropertyCodes;
    }
    public void setDynamicParameterPropertyCodes(List<String> dynamicParameterPropertyCodes) {
        this.dynamicParameterPropertyCodes = dynamicParameterPropertyCodes;
    }

    public String getExample() {
        return example;
    }
    public void setExample(String example) {
        this.example = example;
    }

    public Boolean getIsDynamic() {
        return isDynamic;
    }
    public void setIsDynamic(Boolean isDynamic) {
        this.isDynamic = isDynamic;
    }

    public Boolean getIsRequired() {
        return isRequired;
    }
    public void setIsRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }

    public Boolean getIsWritable() {
        return isWritable;
    }
    public void setIsWritable(Boolean isWritable) {
        this.isWritable = isWritable;
    }

    public Double getMaxExclusive() {
        return maxExclusive;
    }
    public void setMaxExclusive(Double maxExclusive) {
        this.maxExclusive = maxExclusive;
    }

    public Double getMaxInclusive() {
        return maxInclusive;
    }
    public void setMaxInclusive(Double maxInclusive) {
        this.maxInclusive = maxInclusive;
    }

    public Double getMinExclusive() {
        return minExclusive;
    }
    public void setMinExclusive(Double minExclusive) {
        this.minExclusive = minExclusive;
    }

    public Double getMinInclusive() {
        return minInclusive;
    }
    public void setMinInclusive(Double minInclusive) {
        this.minInclusive = minInclusive;
    }

    public String getPattern() {
        return pattern;
    }
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getPhysicalQuantity() {
        return physicalQuantity;
    }
    public void setPhysicalQuantity(String physicalQuantity) {
        this.physicalQuantity = physicalQuantity;
    }

    public List<@Valid ClassPropertyValueContractV1> getAllowedValues() {
        return allowedValues;
    }
    public void setAllowedValues(List<@Valid ClassPropertyValueContractV1> allowedValues) {
        this.allowedValues = allowedValues;
    }

    public String getPredefinedValue() {
        return predefinedValue;
    }
    public void setPredefinedValue(String predefinedValue) {
        this.predefinedValue = predefinedValue;
    }

    public String getPropertyCode() {
        return propertyCode;
    }
    public void setPropertyCode(String propertyCode) {
        this.propertyCode = propertyCode;
    }

    public String getPropertyUri() {
        return propertyUri;
    }
    public void setPropertyUri(String propertyUri) {
        this.propertyUri = propertyUri;
    }

    public String getPropertySet() {
        return propertySet;
    }
    public void setPropertySet(String propertySet) {
        this.propertySet = propertySet;
    }

    public String getPropertyStatus() {
        return propertyStatus;
    }
    public void setPropertyStatus(String propertyStatus) {
        this.propertyStatus = propertyStatus;
    }

    public String getPropertyValueKind() {
        return propertyValueKind;
    }
    public void setPropertyValueKind(String propertyValueKind) {
        this.propertyValueKind = propertyValueKind;
    }

    public String getSymbol() {
        return symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public List<String> getUnits() {
        return units;
    }
    public void setUnits(List<String> units) {
        this.units = units;
    }

    public List<String> getQudtCodes() {
        return qudtCodes;
    }
    public void setQudtCodes(List<String> qudtCodes) {
        this.qudtCodes = qudtCodes;
    }


    // =====================================================================================================================
    // business logic method
    public void generateUri(String serverUrl) {
        if (this.uri != null) {
            this.uri = serverUrl + "/property/" + this.uri;
            this.propertyUri = this.uri;
        }
    }

    public void transformToLowerCase() {
        if(this.propertyCode != null) {
            this.propertyCode = this.propertyCode.toLowerCase();
        }
    }


    // standard object methods equals, hashCode, and toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassPropertyContractV1 that = (ClassPropertyContractV1) o;
        return 
            Objects.equals(name, that.name) &&
            Objects.equals(uri, that.uri) &&
            Objects.equals(description, that.description) &&
            Objects.equals(definition, that.definition) &&
            Objects.equals(dataType, that.dataType) &&
            Objects.equals(dimension, that.dimension) &&
            Objects.equals(dimensionLength, that.dimensionLength) &&
            Objects.equals(dimensionMass, that.dimensionMass) &&
            Objects.equals(dimensionTime, that.dimensionTime) &&
            Objects.equals(dimensionElectricCurrent, that.dimensionElectricCurrent) &&
            Objects.equals(dimensionThermodynamicTemperature, that.dimensionThermodynamicTemperature) &&
            Objects.equals(dimensionAmountOfSubstance, that.dimensionAmountOfSubstance) &&
            Objects.equals(dimensionLuminousIntensity, that.dimensionLuminousIntensity) &&
            Objects.equals(dynamicParameterPropertyCodes, that.dynamicParameterPropertyCodes) &&
            Objects.equals(example, that.example) &&
            Objects.equals(isDynamic, that.isDynamic) &&
            Objects.equals(isRequired, that.isRequired) &&
            Objects.equals(isWritable, that.isWritable) &&
            Objects.equals(maxExclusive, that.maxExclusive) &&
            Objects.equals(maxInclusive, that.maxInclusive) &&
            Objects.equals(minExclusive, that.minExclusive) &&
            Objects.equals(minInclusive, that.minInclusive) &&
            Objects.equals(pattern, that.pattern) &&
            Objects.equals(physicalQuantity, that.physicalQuantity) &&
            Objects.equals(allowedValues, that.allowedValues) &&
            Objects.equals(predefinedValue, that.predefinedValue) &&
            Objects.equals(propertyCode, that.propertyCode) &&
            Objects.equals(propertyUri, that.propertyUri) &&
            Objects.equals(propertySet, that.propertySet) &&
            Objects.equals(propertyStatus, that.propertyStatus) &&
            Objects.equals(propertyValueKind, that.propertyValueKind) &&
            Objects.equals(symbol, that.symbol) &&
            Objects.equals(units, that.units) &&
            Objects.equals(qudtCodes, that.qudtCodes);
    }


    @Override
    public int hashCode() {
        return Objects.hash(
            name,
            uri,
            description,
            definition,
            dataType,
            dimension,
            dimensionLength,
            dimensionMass,
            dimensionTime,
            dimensionElectricCurrent,
            dimensionThermodynamicTemperature,
            dimensionAmountOfSubstance,
            dimensionLuminousIntensity,
            dynamicParameterPropertyCodes,
            example,
            isDynamic,
            isRequired,
            isWritable,
            maxExclusive,
            maxInclusive,
            minExclusive,
            minInclusive,
            pattern,
            physicalQuantity,
            allowedValues,
            predefinedValue,
            propertyCode,
            propertyUri,
            propertySet,
            propertyStatus,
            propertyValueKind,
            symbol,
            units,
            qudtCodes
        );
    }

    @Override
    public String toString() {
        return 
            "ClassPropertyContractV1{" +
            "name=" + name + '\'' +
            ", uri=" + uri + '\'' +
            ", description=" + description + '\'' +
            ", definition=" + definition + '\'' +
            ", dataType=" + dataType + '\'' +
            ", dimension=" + dimension + '\'' +
            ", dimensionLength=" + dimensionLength + '\'' +
            ", dimensionMass=" + dimensionMass + '\'' +
            ", dimensionTime=" + dimensionTime + '\'' +
            ", dimensionElectricCurrent=" + dimensionElectricCurrent + '\'' +
            ", dimensionThermodynamicTemperature=" + dimensionThermodynamicTemperature + '\'' +
            ", dimensionAmountOfSubstance=" + dimensionAmountOfSubstance + '\'' +
            ", dimensionLuminousIntensity=" + dimensionLuminousIntensity + '\'' +
            ", dynamicParameterPropertyCodes=" + dynamicParameterPropertyCodes + '\'' +
            ", example=" + example + '\'' +
            ", isDynamic=" + isDynamic + '\'' +
            ", isRequired=" + isRequired + '\'' +
            ", isWritable=" + isWritable + '\'' +
            ", maxExclusive=" + maxExclusive + '\'' +
            ", maxInclusive=" + maxInclusive + '\'' +
            ", minExclusive=" + minExclusive + '\'' +
            ", minInclusive=" + minInclusive + '\'' +
            ", pattern=" + pattern + '\'' +
            ", physicalQuantity=" + physicalQuantity + '\'' +
            ", allowedValues=" + allowedValues + '\'' +
            ", predefinedValue=" + predefinedValue + '\'' +
            ", propertyCode=" + propertyCode + '\'' +
            ", propertyUri=" + propertyUri + '\'' +
            ", propertySet=" + propertySet + '\'' +
            ", propertyStatus=" + propertyStatus + '\'' +
            ", propertyValueKind=" + propertyValueKind + '\'' +
            ", symbol=" + symbol + '\'' +
            ", units=" + units + '\'' +
            ", qudtCodes=" + qudtCodes + '\'' +
            "}";
    }
}
