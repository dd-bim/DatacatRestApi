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
import datacat.models.deserializers.ClassContractV1Deserializer;

// =====================================================================================================================
// M O D E L   C L A S S   S E C T I O N
// 
// =====================================================================================================================
@JsonTypeName("ClassContract.v1")
@JsonDeserialize(using = ClassContractV1Deserializer.class)
@Data
public class ClassContractV1 {

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern =
    "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date versionDateUtc;

    @JsonProperty("versionNumber")
    // private int versionNumber = 0;
    private Integer versionNumber;

    @JsonProperty("visualRepresentationUri")
    private String visualRepresentationUri;

    @JsonProperty("classType")
    private String classType;

    @JsonProperty("referenceCode")
    private String referenceCode;

    @JsonProperty("synonyms")
    private List<String> synonyms = new ArrayList<>();

    @JsonProperty("relatedIfcEntityNames")
    private List<String> relatedIfcEntityNames = new ArrayList<>();

    @JsonProperty("parentClassReference")
    private ClassReferenceContractV1 parentClassReference;

    @JsonProperty("classProperties")
    private List<@Valid ClassPropertyContractV1> classProperties = new ArrayList<>();

    @JsonProperty("classRelations")
    private List<@Valid ClassRelationContractV1> classRelations = new ArrayList<>();

    // @JsonProperty("childClassReferences") // not compatible with datacat
    // private List<> childClassReferences = new ArrayList<>();

    // @JsonProperty("reverseClassRelations") // not compatible with datacat
    // private List<> reverseClassRelations = new ArrayList<>();

    // @JsonProperty("hierarchy") // not testet/implemented yet
    // private List<String> hierarchy = new ArrayList<>();

    // =====================================================================================================================
    // setting default values
    public ClassContractV1() {
        DefaultValuesHandler.ensureDefaults(this);
    }

    // =====================================================================================================================
    // business logic method
    public void generateUri(String serverUrl) {
        if (this.uri != null) {
            this.uri = serverUrl + "/class/" + this.uri;
        }
        if (this.dictionaryUri != null) {
            this.dictionaryUri = serverUrl + "/model/" + this.dictionaryUri;
        }
    }

    public void transformToLowerCase() {
        if (this.code != null) {
            this.code = this.code.toLowerCase();
        }
    }
}
