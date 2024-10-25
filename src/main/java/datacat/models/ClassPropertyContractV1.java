package datacat.models;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
import java.util.Objects;
import java.util.List;

import com.fasterxml.jackson.annotation.*;

// =====================================================================================================================
// M O D E L   C L A S S   S E C T I O N
// model class holds several nested classes, that are used to represent the structure of the JSON response
// new properties are added to the 'Node' class
// '@JsonProperty("")'' annotation is used to define exact name of property in JSON representation
// private fields are being shown in the Swagger UI as a schema
// =====================================================================================================================
@JsonTypeName("ClassPropertyContract.v1")
public class ClassPropertyContractV1 {

    //===================================================================================================================
    // preparation for the field from the json response
    // class has no nested classes, thus starts without 'data'
    @JsonProperty("name")
    private String name;

    // @JsonProperty("uri")
    // private String uri;

    @JsonProperty("description")
    private String description;

    @JsonProperty("definition")
    private String definition;

    // @JsonProperty("dataType")
    // private String dataType;

    // @JsonProperty("dimension")
    // private String dimension;

    // @JsonProperty("dimensionLength")
    // private String dimensionLength;

    // @JsonProperty("dimensionMass")
    // private String dimensionMass;

    // @JsonProperty("dimensionTime")
    // private String dimensionTime;

    // @JsonProperty("dimensionElectricCurrent")
    // private String dimensionElectricCurrent;

    // @JsonProperty("dimensionThermodynamicTemperature")
    // private String dimensionThermodynamicTemperature;

    // @JsonProperty("dimensionAmountOfSubstance")
    // private String dimensionAmountOfSubstance;

    // @JsonProperty("dimensionLuminousIntensity")
    // private String dimensionLuminousIntensity;

    // @JsonProperty("dynamicParameterPropertyCodes")
    // private List<String> dynamicParameterPropertyCodes;

    // @JsonProperty("example")
    // private String example;

    // @JsonProperty("isDynamic")
    // private String isDynamic;

    // @JsonProperty("isRequired")
    // private String isRequired;

    // @JsonProperty("isWritable")
    // private String isWritable;

    // @JsonProperty("maxExclusive")
    // private String maxExclusive;

    // @JsonProperty("maxInclusive")
    // private String maxInclusive;

    // @JsonProperty("minExclusive")
    // private String minExclusive;

    // @JsonProperty("minInclusive")
    // private String minInclusive;

    // @JsonProperty("pattern")
    // private String pattern;

    // @JsonProperty("physicalQuantity")
    // private String physicalQuantity;

    @JsonProperty("allowedValues")
    private String allowedValues;

    // @JsonProperty("predefinedValue")
    // private String predefinedValue;

    // @JsonProperty("propertyCode")
    // private String propertyCode;

    // @JsonProperty("propertyDictionaryName")
    // private String propertyDictionaryName;

    // @JsonProperty("propertyDictionaryUri")
    // private String propertyDictionaryUri;

    @JsonProperty("propertyUri")
    private String propertyUri;

    @JsonProperty("propertySet")
    private String propertySet;

    // @JsonProperty("propertyStatus")
    // private String propertyStatus;

    // @JsonProperty("propertyValueKind")
    // private String propertyValueKind;

    // @JsonProperty("symbol")
    // private String symbol;

    @JsonProperty("units")
    private List<String> units;

    // @JsonProperty("qudtCodes")
    // private List<String> qudtCodes;

    //===================================================================================================================
    // non-argument constructor to create an instance of the class without any data
    public ClassPropertyContractV1() {
    }

    //===================================================================================================================
    // getters and setters for the fields
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // public String getUri() {
    //     return uri;
    // }

    // public void setUri(String uri) {
    //     this.uri = uri;
    // }

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

    // public String getDataType() {
    //     return dataType;
    // }

    // public void setDataType(String dataType) {
    //     this.dataType = dataType;
    // }

    // public String getDimension() {
    //     return dimension;
    // }

    // public void setDimension(String dimension) {
    //     this.dimension = dimension;
    // }

    // public String getDimensionLength() {
    //     return dimensionLength;
    // }

    // public void setDimensionLength(String dimensionLength) {
    //     this.dimensionLength = dimensionLength;
    // }

    // public String getDimensionMass() {
    //     return dimensionMass;
    // }

    // public void setDimensionMass(String dimensionMass) {
    //     this.dimensionMass = dimensionMass;
    // }

    // public String getDimensionTime() {
    //     return dimensionTime;
    // }

    // public void setDimensionTime(String dimensionTime) {
    //     this.dimensionTime = dimensionTime;
    // }

    // public String getDimensionElectricCurrent() {
    //     return dimensionElectricCurrent;
    // }

    // public void setDimensionElectricCurrent(String dimensionElectricCurrent) {
    //     this.dimensionElectricCurrent = dimensionElectricCurrent;
    // }

    // public String getDimensionThermodynamicTemperature() {
    //     return dimensionThermodynamicTemperature;
    // }

    // public void setDimensionThermodynamicTemperature(String dimensionThermodynamicTemperature) {
    //     this.dimensionThermodynamicTemperature = dimensionThermodynamicTemperature;
    // }

    // public String getDimensionAmountOfSubstance() {
    //     return dimensionAmountOfSubstance;
    // }

    // public void setDimensionAmountOfSubstance(String dimensionAmountOfSubstance) {
    //     this.dimensionAmountOfSubstance = dimensionAmountOfSubstance;
    // }

    // public String getDimensionLuminousIntensity() {
    //     return dimensionLuminousIntensity;
    // }

    // public void setDimensionLuminousIntensity(String dimensionLuminousIntensity) {
    //     this.dimensionLuminousIntensity = dimensionLuminousIntensity;
    // }

    // public List<String> getDynamicParameterPropertyCodes() {
    //     return dynamicParameterPropertyCodes;
    // }

    // public void setDynamicParameterPropertyCodes(List<String> dynamicParameterPropertyCodes) {
    //     this.dynamicParameterPropertyCodes = dynamicParameterPropertyCodes;
    // }

    // public String getExample() {
    //     return example;
    // }

    // public void setExample(String example) {
    //     this.example = example;
    // }

    // public String getIsDynamic() {
    //     return isDynamic;
    // }

    // public void setIsDynamic(String isDynamic) {
    //     this.isDynamic = isDynamic;
    // }

    // public String getIsRequired() {
    //     return isRequired;
    // }

    // public void setIsRequired(String isRequired) {
    //     this.isRequired = isRequired;
    // }

    // public String getIsWritable() {
    //     return isWritable;
    // }

    // public void setIsWritable(String isWritable) {
    //     this.isWritable = isWritable;
    // }

    // public String getMaxExclusive() {
    //     return maxExclusive;
    // }

    // public void setMaxExclusive(String maxExclusive) {
    //     this.maxExclusive = maxExclusive;
    // }

    // public String getMaxInclusive() {
    //     return maxInclusive;
    // }

    // public void setMaxInclusive(String maxInclusive) {
    //     this.maxInclusive = maxInclusive;
    // }

    // public String getMinExclusive() {
    //     return minExclusive;
    // }

    // public void setMinExclusive(String minExclusive) {
    //     this.minExclusive = minExclusive;
    // }

    // public String getMinInclusive() {
    //     return minInclusive;
    // }

    // public void setMinInclusive(String minInclusive) {
    //     this.minInclusive = minInclusive;
    // }

    // public String getPattern() {
    //     return pattern;
    // }

    // public void setPattern(String pattern) {
    //     this.pattern = pattern;
    // }

    // public String getPhysicalQuantity() {
    //     return physicalQuantity;
    // }

    // public void setPhysicalQuantity(String physicalQuantity) {
    //     this.physicalQuantity = physicalQuantity;
    // }

    public String getAllowedValues() {
        return allowedValues;
    }

    public void setAllowedValues(String allowedValues) {
        this.allowedValues = allowedValues;
    }

    // public String getPredefinedValue() {
    //     return predefinedValue;
    // }

    // public void setPredefinedValue(String predefinedValue) {
    //     this.predefinedValue = predefinedValue;
    // }

    // public String getPropertyCode() {
    //     return propertyCode;
    // }

    // public void setPropertyCode(String propertyCode) {
    //     this.propertyCode = propertyCode;
    // }

    // public String getPropertyDictionaryName() {
    //     return propertyDictionaryName;
    // }

    // public void setPropertyDictionaryName(String propertyDictionaryName) {
    //     this.propertyDictionaryName = propertyDictionaryName;
    // }

    // public String getPropertyDictionaryUri() {
    //     return propertyDictionaryUri;
    // }

    // public void setPropertyDictionaryUri(String propertyDictionaryUri) {
    //     this.propertyDictionaryUri = propertyDictionaryUri;
    // }

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

    // public String getPropertyStatus() {
    //     return propertyStatus;
    // }

    // public void setPropertyStatus(String propertyStatus) {
    //     this.propertyStatus = propertyStatus;
    // }

    // public String getPropertyValueKind() {
    //     return propertyValueKind;
    // }

    // public void setPropertyValueKind(String propertyValueKind) {
    //     this.propertyValueKind = propertyValueKind;
    // }

    // public String getSymbol() {
    //     return symbol;
    // }

    // public void setSymbol(String symbol) {
    //     this.symbol = symbol;
    // }

    public List<String> getUnits() {
        return units;
    }

    public void setUnits(List<String> units) {
        this.units = units;
    }

    // public List<String> getQudtCodes() {
    //     return qudtCodes;
    // }

    // public void setQudtCodes(List<String> qudtCodes) {
    //     this.qudtCodes = qudtCodes;
    // }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClassPropertyContractV1 that = (ClassPropertyContractV1) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(definition, that.definition) &&
                Objects.equals(propertyUri, that.propertyUri) &&
                // Objects.equals(dataType, that.dataType) &&
                // Objects.equals(dimension, that.dimension) &&
                // Objects.equals(dimensionLength, that.dimensionLength) &&
                // Objects.equals(dimensionMass, that.dimensionMass) &&
                // Objects.equals(dimensionTime, that.dimensionTime) &&
                // Objects.equals(dimensionElectricCurrent, that.dimensionElectricCurrent) &&
                // Objects.equals(dimensionThermodynamicTemperature, that.dimensionThermodynamicTemperature) &&
                // Objects.equals(dimensionAmountOfSubstance, that.dimensionAmountOfSubstance) &&
                // Objects.equals(dimensionLuminousIntensity, that.dimensionLuminousIntensity) &&
                // Objects.equals(dynamicParameterPropertyCodes, that.dynamicParameterPropertyCodes) &&
                // Objects.equals(example, that.example) &&
                // Objects.equals(isDynamic, that.isDynamic) &&
                // Objects.equals(isRequired, that.isRequired) &&
                // Objects.equals(isWritable, that.isWritable) &&
                // Objects.equals(maxExclusive, that.maxExclusive) &&
                // Objects.equals(maxInclusive, that.maxInclusive) &&
                // Objects.equals(minExclusive, that.minExclusive) &&
                // Objects.equals(minInclusive, that.minInclusive) &&
                // Objects.equals(pattern, that.pattern) &&
                // Objects.equals(physicalQuantity, that.physicalQuantity) &&
                Objects.equals(allowedValues, that.allowedValues) &&
                // Objects.equals(predefinedValue, that.predefinedValue) &&
                // Objects.equals(propertyCode, that.propertyCode) &&
                // Objects.equals(propertyDictionaryName, that.propertyDictionaryName) &&
                // Objects.equals(propertyDictionaryUri, that.propertyDictionaryUri) &&
                Objects.equals(propertySet, that.propertySet) &&
                // Objects.equals(propertyStatus, that.propertyStatus) &&
                // Objects.equals(propertyValueKind, that.propertyValueKind) &&
                // Objects.equals(symbol, that.symbol) &&
                Objects.equals(units, that.units);
                // Objects.equals(qudtCodes, that.qudtCodes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            name, 
            description,
            definition, 
            propertyUri, 
            // dataType, 
            // dimension, 
            // dimensionLength, 
            // dimensionMass, 
            // dimensionTime, 
            // dimensionElectricCurrent, 
            // dimensionThermodynamicTemperature, 
            // dimensionAmountOfSubstance, 
            // dimensionLuminousIntensity, 
            // dynamicParameterPropertyCodes, 
            // example, 
            // isDynamic, 
            // isRequired, 
            // isWritable, 
            // maxExclusive, 
            // maxInclusive, 
            // minExclusive, 
            // minInclusive, 
            // pattern, 
            // physicalQuantity, 
            allowedValues, 
            // predefinedValue, 
            // propertyCode, 
            // propertyDictionaryName, 
            // propertyDictionaryUri, 
            propertySet, 
            // propertyStatus, 
            // propertyValueKind, 
            // symbol, 
            units 
            // qudtCodes
        );
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", definition='" + definition + '\'' +
                ", propertyUri='" + propertyUri + '\'' +
                // ", dataType='" + dataType + '\'' +
                // ", dimension='" + dimension + '\'' +
                // ", dimensionLength='" + dimensionLength + '\'' +
                // ", dimensionMass='" + dimensionMass + '\'' +
                // ", dimensionTime='" + dimensionTime + '\'' +
                // ", dimensionElectricCurrent='" + dimensionElectricCurrent + '\'' +
                // ", dimensionThermodynamicTemperature='" + dimensionThermodynamicTemperature + '\'' +
                // ", dimensionAmountOfSubstance='" + dimensionAmountOfSubstance + '\'' +
                // ", dimensionLuminousIntensity='" + dimensionLuminousIntensity + '\'' +
                // ", dynamicParameterPropertyCodes=" + dynamicParameterPropertyCodes +
                // ", example='" + example + '\'' +
                // ", isDynamic='" + isDynamic + '\'' +
                // ", isRequired='" + isRequired + '\'' +
                // ", isWritable='" + isWritable + '\'' +
                // ", maxExclusive='" + maxExclusive + '\'' +
                // ", maxInclusive='" + maxInclusive + '\'' +
                // ", minExclusive='" + minExclusive + '\'' +
                // ", minInclusive='" + minInclusive + '\'' +
                // ", pattern='" + pattern + '\'' +
                // ", physicalQuantity='" + physicalQuantity + '\'' +
                ", allowedValues='" + allowedValues + '\'' +
                // ", predefinedValue='" + predefinedValue + '\'' +
                // ", propertyCode='" + propertyCode + '\'' +
                // ", propertyDictionaryName='" + propertyDictionaryName + '\'' +
                // ", propertyDictionaryUri='" + propertyDictionaryUri + '\'' +
                ", propertySet='" + propertySet + '\'' +
                // ", propertyStatus='" + propertyStatus + '\'' +
                // ", propertyValueKind='" + propertyValueKind + '\'' +
                // ", symbol='" + symbol + '\'' +
                ", units=" + units +
                // ", qudtCodes=" + qudtCodes +
                '}';
    }
}