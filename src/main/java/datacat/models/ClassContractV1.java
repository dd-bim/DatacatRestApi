package datacat.models;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import datacat.customization.DefaultValuesHandler;

import org.springframework.format.annotation.DateTimeFormat;

// =====================================================================================================================
// M O D E L   C L A S S   S E C T I O N
// model class holds several nested classes, that are used to represent the structure of the JSON response
// new properties are added to the 'Node' class
// '@JsonProperty("")'' annotation is used to define exact name of property in JSON representation
// private fields are being shown in the Swagger UI as a schema
// =====================================================================================================================
@JsonTypeName("ClassContract.v1")
public class ClassContractV1 {

    // =====================================================================================================================
    // variables
    // 'classProperties' works as a list of objects, is linked to its own class

    @JsonProperty("classType")
    private String classType;
    
    @JsonProperty("referenceCode")
    private String referenceCode;

    @JsonProperty("relatedIfcEntityNames")
    private List<String> relatedIfcEntityNames = new ArrayList<>();

    @JsonProperty("parentClassReference")
    private List<ClassReferenceContractV1> parentClassReference = new ArrayList<>();

    @JsonProperty("classProperties")
    private List<ClassPropertyContractV1> classProperties = new ArrayList<>();

    @JsonProperty("classRelations")
    private List<ClassRelationContractV1> classRelations = new ArrayList<>();

    // @JsonProperty("childClassReferences") // not compatible with datacat
    // private List<> childClassReferences = new ArrayList<>();

    // @JsonProperty("reverseClassRelations") // not compatible with datacat
    // private List<> reverseClassRelations = new ArrayList<>();

    // @JsonProperty("hierarchy") // not testet/implemented yet
    // private List<String> hierarchy = new ArrayList<>();

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

    @JsonProperty("deActivationDateUTC")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date deActivationDateUTC;

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
    private int revisionNumber = 0;

    @JsonProperty("status")
    private String status;

    @JsonProperty("subdivisionOfUse")
    private List<String> subdivisionOfUse = new ArrayList<>();

    @JsonProperty("uid")
    private String uid;

    @JsonProperty("versionDateUtc")
    // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") // uncomment after update
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private String versionDateUtc; // after update: change to 'Date' format

    @JsonProperty("versionNumber")
    // private int versionNumber = 0;
    private String versionNumber;

    @JsonProperty("visualRepresentationUri")
    private String visualRepresentationUri;

    @JsonProperty("synonyms")
    private List<String> synonyms = new ArrayList<>();

    //===================================================================================================================
    // non-argument constructor to create an instance of the class with null or specific default values
    public ClassContractV1() {
        DefaultValuesHandler.ensureDefaults(this); // Set default values using the utility class
    }

    // =====================================================================================================================
    // getters and setters
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
    
    public List<String> getRelatedIfcEntityNames() {
        return relatedIfcEntityNames != null ? relatedIfcEntityNames : new ArrayList<>();
    }
    
    public void setRelatedIfcEntityNames(List<String> relatedIfcEntityNames) {
        this.relatedIfcEntityNames = relatedIfcEntityNames;
    }
    
    public List<ClassReferenceContractV1> getParentClassReference() {
        return parentClassReference != null ? parentClassReference : new ArrayList<>();
    }
    
    public void setParentClassReference(List<ClassReferenceContractV1> parentClassReference) {
        this.parentClassReference = parentClassReference;
    }
    
    public List<ClassPropertyContractV1> getClassProperties() {
        return classProperties != null ? classProperties : new ArrayList<>();
    }
    
    public void setClassProperties(List<ClassPropertyContractV1> classProperties) {
        this.classProperties = classProperties;
    }
    
    public List<ClassRelationContractV1> getClassRelations() {
        return classRelations != null ? classRelations : new ArrayList<>();
    }
    
    public void setClassRelations(List<ClassRelationContractV1> classRelations) {
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
        return countriesOfUse != null ? countriesOfUse : new ArrayList<>();
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
    
    public Date getDeActivationDateUTC() {
        return deActivationDateUTC;
    }
    
    public void setDeActivationDateUTC(Date deActivationDateUTC) {
        this.deActivationDateUTC = deActivationDateUTC;
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
        return replacedObjectCodes != null ? replacedObjectCodes : new ArrayList<>();
    }
    
    public void setReplacedObjectCodes(List<String> replacedObjectCodes) {
        this.replacedObjectCodes = replacedObjectCodes;
    }
    
    public List<String> getReplacingObjectCodes() {
        return replacingObjectCodes != null ? replacingObjectCodes : new ArrayList<>();
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
    
    public int getRevisionNumber() {
        return revisionNumber;
    }
    
    public void setRevisionNumber(int revisionNumber) {
        this.revisionNumber = revisionNumber;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public List<String> getSubdivisionOfUse() {
        return subdivisionOfUse != null ? subdivisionOfUse : new ArrayList<>();
    }
    
    public void setSubdivisionOfUse(List<String> subdivisionOfUse) {
        this.subdivisionOfUse = subdivisionOfUse;
    }
    
    public String getUid() {
        return uid;
    }
    
    public void setUid(String uid) {
        this.uid = uid;
    }
    
    public String getVersionDateUtc() { // after update change to 'Date' format
        return versionDateUtc;
    }
    
    public void setVersionDateUtc(String versionDateUtc) { // after update change to 'Date' format
        this.versionDateUtc = versionDateUtc;
    }
    
    public String getVersionNumber() { // after update change to 'int' format
        return versionNumber;
    }
    
    public void setVersionNumber(String versionNumber) { // after update change to 'int' format
        this.versionNumber = versionNumber;
    }
    
    public String getVisualRepresentationUri() {
        return visualRepresentationUri;
    }
    
    public void setVisualRepresentationUri(String visualRepresentationUri) {
        this.visualRepresentationUri = visualRepresentationUri;
    }
    
    public List<String> getSynonyms() {
        return synonyms != null ? synonyms : new ArrayList<>();
    }
    
    public void setSynonyms(List<String> synonyms) {
        this.synonyms = synonyms;
    }

    // =====================================================================================================================
    // business logic method
    public void generateUri(String serverUrl) {
        if (this.uri != null) {
            this.uri = serverUrl + "/class/" + this.uri; // Construct the URI by combining the base URL with the uid (which holds the id)
            this.dictionaryUri = serverUrl + "/model/" + this.dictionaryUri; // Construct the URI by combining the base URL with the uid (which holds the id)
        }
    }

    public void transformToLowerCase() {
        if (this.code != null) {
            this.code = this.code.toLowerCase();
        }
    }

    // standard object methods equals, hashCode, and toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassContractV1 that = (ClassContractV1) o;
        return Objects.equals(classType, that.classType) &&
                Objects.equals(referenceCode, that.referenceCode) &&
                Objects.equals(relatedIfcEntityNames, that.relatedIfcEntityNames) &&
                Objects.equals(parentClassReference, that.parentClassReference) &&
                Objects.equals(classProperties, that.classProperties) &&
                Objects.equals(classRelations, that.classRelations) &&
                // Objects.equals(childClassReferences, that.childClassReferences) &&
                // Objects.equals(reverseClassRelations, that.reverseClassRelations) &&
                // Objects.equals(hierarchy, that.hierarchy) &&
                Objects.equals(dictionaryUri, that.dictionaryUri) &&
                Objects.equals(activationDateUtc, that.activationDateUtc) &&
                Objects.equals(code, that.code) &&
                Objects.equals(creatorLanguageCode, that.creatorLanguageCode) &&
                Objects.equals(countriesOfUse, that.countriesOfUse) &&
                Objects.equals(countryOfOrigin, that.countryOfOrigin) &&
                Objects.equals(deActivationDateUTC, that.deActivationDateUTC) &&
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
                Objects.equals(subdivisionOfUse, that.subdivisionOfUse) &&
                Objects.equals(uid, that.uid) &&
                Objects.equals(versionDateUtc, that.versionDateUtc) &&
                Objects.equals(versionNumber, that.versionNumber) &&
                Objects.equals(visualRepresentationUri, that.visualRepresentationUri) &&
                Objects.equals(synonyms, that.synonyms);
    }
    @Override
    public int hashCode() {
        return Objects.hash(
            classType,
            referenceCode,
            relatedIfcEntityNames,
            parentClassReference,
            classProperties,
            classRelations,
            // childClassReferences,
            // reverseClassRelations,
            // hierarchy,
            dictionaryUri,
            activationDateUtc,
            code,
            creatorLanguageCode,
            countriesOfUse,
            countryOfOrigin,
            deActivationDateUTC,
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
            subdivisionOfUse,
            uid,
            versionDateUtc,
            versionNumber,
            visualRepresentationUri,
            synonyms
        );
    }

    @Override
    public String toString() {
        return "ClassDetailsResponseV1{" +
                "classType='" + classType + '\'' +
                ", referenceCode='" + referenceCode + '\'' +
                ", relatedIfcEntityNames=" + relatedIfcEntityNames + '\'' +
                ", parentClassReference=" + parentClassReference + '\'' +
                ", classProperties=" + classProperties + '\'' +
                ", classRelations=" + classRelations + '\'' +
                // ", childClassReferences=" + childClassReferences + '\'' +
                // ", reverseClassRelations=" + reverseClassRelations + '\'' +
                // ", hierarchy=" + hierarchy + '\'' +
                ", dictionaryUri='" + dictionaryUri + '\'' +
                ", activationDateUtc='" + activationDateUtc + '\'' +
                ", code='" + code + '\'' +
                ", creatorLanguageCode='" + creatorLanguageCode + '\'' +
                ", countriesOfUse=" + countriesOfUse +
                ", countryOfOrigin='" + countryOfOrigin + '\'' +
                ", deActivationDateUTC='" + deActivationDateUTC + '\'' +
                ", definition='" + definition + '\'' +
                ", deprecationExplanation='" + deprecationExplanation + '\'' +
                ", description='" + description + '\'' +
                ", documentReference='" + documentReference + '\'' +
                ", name='" + name + '\'' +
                ", uri='" + uri + '\'' +
                ", replacedObjectCodes=" + replacedObjectCodes + '\'' +
                ", replacingObjectCodes=" + replacingObjectCodes + '\'' +
                ", revisionDateUtc='" + revisionDateUtc + '\'' +
                ", revisionNumber='" + revisionNumber + '\'' +
                ", status='" + status + '\'' +
                ", subdivisionOfUse=" + subdivisionOfUse + '\'' +
                ", uid='" + uid + '\'' +
                ", versionDateUtc='" + versionDateUtc + '\'' +
                ", versionNumber='" + versionNumber + '\'' +
                ", visualRepresentationUri='" + visualRepresentationUri + '\'' +
                ", synonyms=" + synonyms +  '\'' + 
                "}";
    }
}