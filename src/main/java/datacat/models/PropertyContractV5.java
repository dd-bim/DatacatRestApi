package datacat.models;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
import java.util.*;
import jakarta.validation.Valid;
import lombok.Data;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.format.annotation.DateTimeFormat;
import datacat.customization.DefaultValuesHandler;
import datacat.models.deserializers.PropertyContractV5Deserializer;

// =====================================================================================================================
// M O D E L   C L A S S   S E C T I O N
// 
// =====================================================================================================================
@JsonTypeName("PropertyContract.v5")
@JsonDeserialize(using = PropertyContractV5Deserializer.class)
@Data
public class PropertyContractV5 {

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

    // =====================================================================================================================
    // setting default values
    public PropertyContractV5() {
        DefaultValuesHandler.ensureDefaults(this);
    }

    // =====================================================================================================================
    // business logic method
    public void generateUri(String serverUrl) {
        if(this.uri != null) {
            this.uri = serverUrl + "/property/" + this.uri;
        }
        if(this.dictionaryUri != null) {
            this.dictionaryUri = serverUrl + "/model/" + this.dictionaryUri;
        }
        // Generate URIs for all allowedValues
        if(this.allowedValues != null && !this.allowedValues.isEmpty()) {
            for(PropertyValueContractV4 allowedValue : this.allowedValues) {
                if(allowedValue != null) {
                    allowedValue.generateUri(serverUrl);
                }
            }
        }
    }


    public void transformToLowerCase() {
        if(this.code != null) {
            this.code = this.code.toLowerCase();
        }
    }
}
