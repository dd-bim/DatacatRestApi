package datacat.models;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
import java.util.*;
import jakarta.validation.Valid;
import com.fasterxml.jackson.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import datacat.customization.DefaultValuesHandler;

// =====================================================================================================================
// M O D E L   C L A S S   S E C T I O N
// 
// =====================================================================================================================
@JsonTypeName("PropertyContract.v4")
public class PropertyContractV4 {

    @JsonProperty("dictionaryUri")
    private String dictionaryUri;

    @JsonProperty("activationDateUtc")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date activationDateUtc;

    @JsonProperty("code")
    private String code;

    @JsonProperty("creatorLanguageCode")
    private String creatorLanguageCode;

    @JsonProperty("countriesOfUse")
    private List<String> countriesOfUse = new ArrayList<>();

    @JsonProperty("countryOfOrigin")
    private String countryOfOrigin;

    @JsonProperty("deActivationDateUtc")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date deActivationDateUtc;

    @JsonProperty("definition")
    private String definition;

    @JsonProperty("deprecationExplanation")
    private String deprecationExplanation;

    @JsonProperty("description")
    private String description;

    @JsonProperty("documentReference")
    private String documentReference;

    @JsonProperty("name")
    private String name;

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("replacedObjectCodes")
    private List<String> replacedObjectCodes = new ArrayList<>();

    @JsonProperty("replacingObjectCodes")
    private List<String> replacingObjectCodes = new ArrayList<>();

    @JsonProperty("revisionDateUtc")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date revisionDateUtc;

    @JsonProperty("revisionNumber")
    private Integer revisionNumber;

    @JsonProperty("status")
    private String status;

    @JsonProperty("subdivisionsOfUse")
    private List<String> subdivisionsOfUse = new ArrayList<>();

    @JsonProperty("uid")
    private String uid;

    @JsonProperty("versionDateUtc")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date versionDateUtc;

    @JsonProperty("versionNumber")
    private Integer versionNumber;

    @JsonProperty("visualRepresentationUri")
    private String visualRepresentationUri;

    @JsonProperty("connectedPropertyCodes")
    private List<String> connectedPropertyCodes = new ArrayList<>();

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

    @JsonProperty("maxExclusive")
    private Double maxExclusive;

    @JsonProperty("maxInclusive")
    private Double maxInclusive;

    @JsonProperty("methodOfMeasurement")
    private String methodOfMeasurement;

    @JsonProperty("minExclusive")
    private Double minExclusive;

    @JsonProperty("minInclusive")
    private Double minInclusive;

    @JsonProperty("pattern")
    private String pattern;

    @JsonProperty("physicalQuantity")
    private String physicalQuantity;

    @JsonProperty("allowedValues")
    private List<@Valid PropertyValueContractV4> allowedValues = new ArrayList<>();

    @JsonProperty("propertyValueKind")
    private String propertyValueKind;

    @JsonProperty("propertyRelations")
    private List<@Valid PropertyRelationContractV4> propertyRelations = new ArrayList<>();

    @JsonProperty("textFormat")
    private String textFormat;

    @JsonProperty("units")
    private List<String> units = new ArrayList<>();

    @JsonProperty("qudtCodes")
    private List<String> qudtCodes = new ArrayList<>();

    @JsonProperty("propertyClasses")
    private List<@Valid PropertyClassContractV4> propertyClasses = new ArrayList<>();


    // =====================================================================================================================
    // setting default values
    public PropertyContractV4() {
        DefaultValuesHandler.ensureDefaults(this);
    }

    
    // =====================================================================================================================
    // getters and setters
    public String getDictionaryUri() {
        return dictionaryUri;
    }
    public void setDictionaryUri(String dictionaryUri) {
        this.dictionaryUri = dictionaryUri;
    }

    public Date getActivationDateUtc() {
        return activationDateUtc;
    }
    public void setActivationDateUtc(Date activationDateUtc) {
        this.activationDateUtc = activationDateUtc;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getCreatorLanguageCode() {
        return creatorLanguageCode;
    }
    public void setCreatorLanguageCode(String creatorLanguageCode) {
        this.creatorLanguageCode = creatorLanguageCode;
    }

    public List<String> getCountriesOfUse() {
        return countriesOfUse;
    }
    public void setCountriesOfUse(List<String> countriesOfUse) {
        this.countriesOfUse = countriesOfUse;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }
    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public Date getDeActivationDateUtc() {
        return deActivationDateUtc;
    }
    public void setDeActivationDateUtc(Date deActivationDateUtc) {
        this.deActivationDateUtc = deActivationDateUtc;
    }

    public String getDefinition() {
        return definition;
    }
    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getDeprecationExplanation() {
        return deprecationExplanation;
    }
    public void setDeprecationExplanation(String deprecationExplanation) {
        this.deprecationExplanation = deprecationExplanation;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDocumentReference() {
        return documentReference;
    }
    public void setDocumentReference(String documentReference) {
        this.documentReference = documentReference;
    }

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

    public List<String> getReplacedObjectCodes() {
        return replacedObjectCodes;
    }
    public void setReplacedObjectCodes(List<String> replacedObjectCodes) {
        this.replacedObjectCodes = replacedObjectCodes;
    }

    public List<String> getReplacingObjectCodes() {
        return replacingObjectCodes;
    }
    public void setReplacingObjectCodes(List<String> replacingObjectCodes) {
        this.replacingObjectCodes = replacingObjectCodes;
    }

    public Date getRevisionDateUtc() {
        return revisionDateUtc;
    }
    public void setRevisionDateUtc(Date revisionDateUtc) {
        this.revisionDateUtc = revisionDateUtc;
    }

    public Integer getRevisionNumber() {
        return revisionNumber;
    }
    public void setRevisionNumber(Integer revisionNumber) {
        this.revisionNumber = revisionNumber;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getSubdivisionsOfUse() {
        return subdivisionsOfUse;
    }
    public void setSubdivisionsOfUse(List<String> subdivisionsOfUse) {
        this.subdivisionsOfUse = subdivisionsOfUse;
    }

    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }

    public Date getVersionDateUtc() {
        return versionDateUtc;
    }
    public void setVersionDateUtc(Date versionDateUtc) {
        this.versionDateUtc = versionDateUtc;
    }

    public Integer getVersionNumber() {
        return versionNumber;
    }
    public void setVersionNumber(Integer versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getVisualRepresentationUri() {
        return visualRepresentationUri;
    }
    public void setVisualRepresentationUri(String visualRepresentationUri) {
        this.visualRepresentationUri = visualRepresentationUri;
    }

    public List<String> getConnectedPropertyCodes() {
        return connectedPropertyCodes;
    }
    public void setConnectedPropertyCodes(List<String> connectedPropertyCodes) {
        this.connectedPropertyCodes = connectedPropertyCodes;
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

    public String getMethodOfMeasurement() {
        return methodOfMeasurement;
    }
    public void setMethodOfMeasurement(String methodOfMeasurement) {
        this.methodOfMeasurement = methodOfMeasurement;
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

    public List<@Valid PropertyValueContractV4> getAllowedValues() {
        return allowedValues;
    }
    public void setAllowedValues(List<@Valid PropertyValueContractV4> allowedValues) {
        this.allowedValues = allowedValues;
    }

    public String getPropertyValueKind() {
        return propertyValueKind;
    }
    public void setPropertyValueKind(String propertyValueKind) {
        this.propertyValueKind = propertyValueKind;
    }

    public List<@Valid PropertyRelationContractV4> getPropertyRelations() {
        return propertyRelations;
    }
    public void setPropertyRelations(List<@Valid PropertyRelationContractV4> propertyRelations) {
        this.propertyRelations = propertyRelations;
    }

    public String getTextFormat() {
        return textFormat;
    }
    public void setTextFormat(String textFormat) {
        this.textFormat = textFormat;
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

    public List<@Valid PropertyClassContractV4> getPropertyClasses() {
        return propertyClasses;
    }
    public void setPropertyClasses(List<@Valid PropertyClassContractV4> propertyClasses) {
        this.propertyClasses = propertyClasses;
    }


    // =====================================================================================================================
    // business logic method
    public void generateUri(String serverUrl) {
        if(this.uri != null) {
            this.uri = serverUrl + "/property/" + this.uri;
        }
        if(this.dictionaryUri != null) {
            this.dictionaryUri = serverUrl + "/dictionary/" + this.dictionaryUri;
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
        PropertyContractV4 that = (PropertyContractV4) o;
        return 
            Objects.equals(dictionaryUri, that.dictionaryUri) &&
            Objects.equals(activationDateUtc, that.activationDateUtc) &&
            Objects.equals(code, that.code) &&
            Objects.equals(creatorLanguageCode, that.creatorLanguageCode) &&
            Objects.equals(countriesOfUse, that.countriesOfUse) &&
            Objects.equals(countryOfOrigin, that.countryOfOrigin) &&
            Objects.equals(deActivationDateUtc, that.deActivationDateUtc) &&
            Objects.equals(definition, that.definition) &&
            Objects.equals(deprecationExplanation, that.deprecationExplanation) &&
            Objects.equals(description, that.description) &&
            Objects.equals(documentReference, that.documentReference) &&
            Objects.equals(name, that.name) &&
            Objects.equals(uri, that.uri) &&
            Objects.equals(replacedObjectCodes, that.replacedObjectCodes) &&
            Objects.equals(replacingObjectCodes, that.replacingObjectCodes) &&
            Objects.equals(revisionDateUtc, that.revisionDateUtc) &&
            Objects.equals(revisionNumber, that.revisionNumber) &&
            Objects.equals(status, that.status) &&
            Objects.equals(subdivisionsOfUse, that.subdivisionsOfUse) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(versionDateUtc, that.versionDateUtc) &&
            Objects.equals(versionNumber, that.versionNumber) &&
            Objects.equals(visualRepresentationUri, that.visualRepresentationUri) &&
            Objects.equals(connectedPropertyCodes, that.connectedPropertyCodes) &&
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
            Objects.equals(maxExclusive, that.maxExclusive) &&
            Objects.equals(maxInclusive, that.maxInclusive) &&
            Objects.equals(methodOfMeasurement, that.methodOfMeasurement) &&
            Objects.equals(minExclusive, that.minExclusive) &&
            Objects.equals(minInclusive, that.minInclusive) &&
            Objects.equals(pattern, that.pattern) &&
            Objects.equals(physicalQuantity, that.physicalQuantity) &&
            Objects.equals(allowedValues, that.allowedValues) &&
            Objects.equals(propertyValueKind, that.propertyValueKind) &&
            Objects.equals(propertyRelations, that.propertyRelations) &&
            Objects.equals(textFormat, that.textFormat) &&
            Objects.equals(units, that.units) &&
            Objects.equals(qudtCodes, that.qudtCodes) &&
            Objects.equals(propertyClasses, that.propertyClasses);
    }


    @Override
    public int hashCode() {
        return Objects.hash(
            dictionaryUri,
            activationDateUtc,
            code,
            creatorLanguageCode,
            countriesOfUse,
            countryOfOrigin,
            deActivationDateUtc,
            definition,
            deprecationExplanation,
            description,
            documentReference,
            name,
            uri,
            replacedObjectCodes,
            replacingObjectCodes,
            revisionDateUtc,
            revisionNumber,
            status,
            subdivisionsOfUse,
            uid,
            versionDateUtc,
            versionNumber,
            visualRepresentationUri,
            connectedPropertyCodes,
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
            maxExclusive,
            maxInclusive,
            methodOfMeasurement,
            minExclusive,
            minInclusive,
            pattern,
            physicalQuantity,
            allowedValues,
            propertyValueKind,
            propertyRelations,
            textFormat,
            units,
            qudtCodes,
            propertyClasses
        );
    }

    @Override
    public String toString() {
        return 
            "PropertyContractV4{" +
            "dictionaryUri=" + dictionaryUri + '\'' +
            ", activationDateUtc=" + activationDateUtc + '\'' +
            ", code=" + code + '\'' +
            ", creatorLanguageCode=" + creatorLanguageCode + '\'' +
            ", countriesOfUse=" + countriesOfUse + '\'' +
            ", countryOfOrigin=" + countryOfOrigin + '\'' +
            ", deActivationDateUtc=" + deActivationDateUtc + '\'' +
            ", definition=" + definition + '\'' +
            ", deprecationExplanation=" + deprecationExplanation + '\'' +
            ", description=" + description + '\'' +
            ", documentReference=" + documentReference + '\'' +
            ", name=" + name + '\'' +
            ", uri=" + uri + '\'' +
            ", replacedObjectCodes=" + replacedObjectCodes + '\'' +
            ", replacingObjectCodes=" + replacingObjectCodes + '\'' +
            ", revisionDateUtc=" + revisionDateUtc + '\'' +
            ", revisionNumber=" + revisionNumber + '\'' +
            ", status=" + status + '\'' +
            ", subdivisionsOfUse=" + subdivisionsOfUse + '\'' +
            ", uid=" + uid + '\'' +
            ", versionDateUtc=" + versionDateUtc + '\'' +
            ", versionNumber=" + versionNumber + '\'' +
            ", visualRepresentationUri=" + visualRepresentationUri + '\'' +
            ", connectedPropertyCodes=" + connectedPropertyCodes + '\'' +
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
            ", maxExclusive=" + maxExclusive + '\'' +
            ", maxInclusive=" + maxInclusive + '\'' +
            ", methodOfMeasurement=" + methodOfMeasurement + '\'' +
            ", minExclusive=" + minExclusive + '\'' +
            ", minInclusive=" + minInclusive + '\'' +
            ", pattern=" + pattern + '\'' +
            ", physicalQuantity=" + physicalQuantity + '\'' +
            ", allowedValues=" + allowedValues + '\'' +
            ", propertyValueKind=" + propertyValueKind + '\'' +
            ", propertyRelations=" + propertyRelations + '\'' +
            ", textFormat=" + textFormat + '\'' +
            ", units=" + units + '\'' +
            ", qudtCodes=" + qudtCodes + '\'' +
            ", propertyClasses=" + propertyClasses + '\'' +
            "}";
    }
}
