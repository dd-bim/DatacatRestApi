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
@JsonTypeName("ClassContract.v1")
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
    // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") // uncomment after update
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private String versionDateUtc; // after update: change to 'Date' format

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

    public String getVersionDateUtc() {
        return versionDateUtc;
    }
    public void setVersionDateUtc(String versionDateUtc) {
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

    public String getClassType() {
        return classType;
    }
    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getReferenceCode() {
        return referenceCode;
    }
    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    public List<String> getSynonyms() {
        return synonyms;
    }
    public void setSynonyms(List<String> synonyms) {
        this.synonyms = synonyms;
    }

    public List<String> getRelatedIfcEntityNames() {
        return relatedIfcEntityNames;
    }
    public void setRelatedIfcEntityNames(List<String> relatedIfcEntityNames) {
        this.relatedIfcEntityNames = relatedIfcEntityNames;
    }

    public ClassReferenceContractV1 getParentClassReference() {
        return parentClassReference;
    }
    public void setParentClassReference(ClassReferenceContractV1 parentClassReference) {
        this.parentClassReference = parentClassReference;
    }

    public List<@Valid ClassPropertyContractV1> getClassProperties() {
        return classProperties;
    }
    public void setClassProperties(List<@Valid ClassPropertyContractV1> classProperties) {
        this.classProperties = classProperties;
    }

    public List<@Valid ClassRelationContractV1> getClassRelations() {
        return classRelations;
    }
    public void setClassRelations(List<@Valid ClassRelationContractV1> classRelations) {
        this.classRelations = classRelations;
    }

    // public List<?> getChildClassReferences() {
    //     return childClassReferences != null ? childClassReferences : new ArrayList<>();
    // }
    // public void setChildClassReferences(List<?> childClassReferences) {
    //     this.childClassReferences = childClassReferences;
    // }
    
    // public List<?> getReverseClassRelations() {
    //     return reverseClassRelations != null ? reverseClassRelations : new ArrayList<>();
    // }
    // public void setReverseClassRelations(List<?> reverseClassRelations) {
    //     this.reverseClassRelations = reverseClassRelations;
    // }
    
    // public List<?> getHierarchy() {
    //     return hierarchy != null ? hierarchy : new ArrayList<>();
    // }
    // public void setHierarchy(List<String> hierarchy) {
    //     this.hierarchy = hierarchy;
    // }

    // =====================================================================================================================
    // business logic method
    public void generateUri(String serverUrl) {
        if(this.uri != null) {
            this.uri = serverUrl + "/class/" + this.uri;
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
        ClassContractV1 that = (ClassContractV1) o;
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
            Objects.equals(classType, that.classType) &&
            Objects.equals(referenceCode, that.referenceCode) &&
            Objects.equals(synonyms, that.synonyms) &&
            Objects.equals(relatedIfcEntityNames, that.relatedIfcEntityNames) &&
            Objects.equals(parentClassReference, that.parentClassReference) &&
            Objects.equals(classProperties, that.classProperties) &&
            Objects.equals(classRelations, that.classRelations);
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
            classType,
            referenceCode,
            synonyms,
            relatedIfcEntityNames,
            parentClassReference,
            classProperties,
            classRelations
        );
    }

    @Override
    public String toString() {
        return 
            "ClassContractV1{" +
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
            ", classType=" + classType + '\'' +
            ", referenceCode=" + referenceCode + '\'' +
            ", synonyms=" + synonyms + '\'' +
            ", relatedIfcEntityNames=" + relatedIfcEntityNames + '\'' +
            ", parentClassReference=" + parentClassReference + '\'' +
            ", classProperties=" + classProperties + '\'' +
            ", classRelations=" + classRelations + '\'' +
            "}";
    }
}
