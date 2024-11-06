package datacat.models;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
import java.util.Objects;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import datacat.customization.DefaultValuesHandler;

import org.springframework.format.annotation.DateTimeFormat;

// =====================================================================================================================
// M O D E L   C L A S S   S E C T I O N
// 
// '@JsonProperty("")'' annotation is used to define exact name of property in JSON representation
// private fields are being shown in the Swagger UI as a schema
// =====================================================================================================================
@JsonTypeName("DictionaryClassesResponseContract.v1.Classes")
public class DictionaryClassesResponseContractV1Classes {

    // =====================================================================================================================
    // variables
    // 'classes' works as a list of objects, is linked to its own class
    @JsonProperty("classesTotalCount")
    private Integer classesTotalCount;

    @JsonProperty("classesOffset")
    private Integer classesOffset;

    @JsonProperty("classesCount")
    private Integer classesCount;

    @JsonProperty("code")
    private String code;

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("name")
    private String name;

    @JsonProperty("version")
    private String version;

    @JsonProperty("organizationCodeOwner")
    private String organizationCodeOwner;

    @JsonProperty("organizationNameOwner")
    private String organizationNameOwner;

    @JsonProperty("defaultLanguageCode")
    private String defaultLanguageCode;

    @JsonProperty("isLatestVersion")
    private Boolean isLatestVersion;

    @JsonProperty("isVerified")
    private Boolean isVerified;

    @JsonProperty("license")
    private String license;

    @JsonProperty("licenseUrl")
    private String licenseUrl;

    @JsonProperty("qualityAssuranceProcedure")
    private String qualityAssuranceProcedure;

    @JsonProperty("qualityAssuranceProcedureUrl")
    private String qualityAssuranceProcedureUrl;

    @JsonProperty("status")
    private String status;

    @JsonProperty("moreInfoUrl")
    private String moreInfoUrl;

    @JsonProperty("releaseDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date releaseDate;

    @JsonProperty("lastUpdatedUtc")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date lastUpdatedUtc;

    @JsonProperty("classes")
    private List<ClassListItemContractV1Classes> classes = new ArrayList<>();

    // =====================================================================================================================
    // non-argument constructor
    public DictionaryClassesResponseContractV1Classes() {
        DefaultValuesHandler.ensureDefaults(this);
    }

    // =====================================================================================================================
    // getters and setters
    public Integer getClassesTotalCount() {
        return classesTotalCount;
    }
    
    public void setClassesTotalCount(Integer classesTotalCount) {
        this.classesTotalCount = classesTotalCount;
    }
    
    public Integer getClassesOffset() {
        return classesOffset;
    }
    
    public void setClassesOffset(Integer classesOffset) {
        this.classesOffset = classesOffset;
    }
    
    public Integer getClassesCount() {
        return classesCount;
    }
    
    public void setClassesCount(Integer classesCount) {
        this.classesCount = classesCount;
    }

    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getUri() {
        return uri;
    }
    
    public void setUri(String uri) {
        this.uri = uri;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getVersion() {
        return version;
    }
    
    public void setVersion(String version) {
        this.version = version;
    }
    
    public String getOrganizationCodeOwner() {
        return organizationCodeOwner;
    }
    
    public void setOrganizationCodeOwner(String organizationCodeOwner) {
        this.organizationCodeOwner = organizationCodeOwner;
    }
    
    public String getOrganizationNameOwner() {
        return organizationNameOwner;
    }
    
    public void setOrganizationNameOwner(String organizationNameOwner) {
        this.organizationNameOwner = organizationNameOwner;
    }
    
    public String getDefaultLanguageCode() {
        return defaultLanguageCode;
    }
    
    public void setDefaultLanguageCode(String defaultLanguageCode) {
        this.defaultLanguageCode = defaultLanguageCode;
    }
    
    public Boolean getIsLatestVersion() {
        return isLatestVersion;
    }
    
    public void setIsLatestVersion(Boolean isLatestVersion) {
        this.isLatestVersion = isLatestVersion;
    }
    
    public Boolean getIsVerified() {
        return isVerified;
    }
    
    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }
    
    public String getLicense() {
        return license;
    }
    
    public void setLicense(String license) {
        this.license = license;
    }
    
    public String getLicenseUrl() {
        return licenseUrl;
    }
    
    public void setLicenseUrl(String licenseUrl) {
        this.licenseUrl = licenseUrl;
    }
    
    public String getQualityAssuranceProcedure() {
        return qualityAssuranceProcedure;
    }
    
    public void setQualityAssuranceProcedure(String qualityAssuranceProcedure) {
        this.qualityAssuranceProcedure = qualityAssuranceProcedure;
    }
    
    public String getQualityAssuranceProcedureUrl() {
        return qualityAssuranceProcedureUrl;
    }
    
    public void setQualityAssuranceProcedureUrl(String qualityAssuranceProcedureUrl) {
        this.qualityAssuranceProcedureUrl = qualityAssuranceProcedureUrl;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getMoreInfoUrl() {
        return moreInfoUrl;
    }
    
    public void setMoreInfoUrl(String moreInfoUrl) {
        this.moreInfoUrl = moreInfoUrl;
    }
    
    public Date getReleaseDate() {
        return releaseDate;
    }
    
    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
    
    public Date getLastUpdatedUtc() {
        return lastUpdatedUtc;
    }
    
    public void setLastUpdatedUtc(Date lastUpdatedUtc) {
        this.lastUpdatedUtc = lastUpdatedUtc;
    }

    public List<ClassListItemContractV1Classes> getClasses() {
        return classes;
    }
    
    public void setClasses(List<ClassListItemContractV1Classes> classes) {
        this.classes = classes;
    }

    // =====================================================================================================================
    // business logic method
    public void generateUri(String serverUrl) {
        if (this.uri != null) {
            this.uri = serverUrl + "/model/" + this.uri; // Construct the URI by combining the base URL with the uid (which holds the id)
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
        DictionaryClassesResponseContractV1Classes that = (DictionaryClassesResponseContractV1Classes) o;
        return Objects.equals(classesTotalCount, that.classesTotalCount) &&
            Objects.equals(classesOffset, that.classesOffset) &&
            Objects.equals(classesCount, that.classesCount) &&
            Objects.equals(code, that.code) &&
            Objects.equals(uri, that.uri) &&
            Objects.equals(name, that.name) &&
            Objects.equals(version, that.version)&&
            Objects.equals(organizationCodeOwner, that.organizationCodeOwner) &&
            Objects.equals(organizationNameOwner, that.organizationNameOwner) &&
            Objects.equals(defaultLanguageCode, that.defaultLanguageCode) &&
            Objects.equals(isLatestVersion, that.isLatestVersion) &&
            Objects.equals(isVerified, that.isVerified) &&
            Objects.equals(license, that.license) &&
            Objects.equals(licenseUrl, that.licenseUrl) &&
            Objects.equals(qualityAssuranceProcedure, that.qualityAssuranceProcedure) &&
            Objects.equals(qualityAssuranceProcedureUrl, that.qualityAssuranceProcedureUrl) &&
            Objects.equals(status, that.status) &&
            Objects.equals(moreInfoUrl, that.moreInfoUrl) &&
            Objects.equals(releaseDate, that.releaseDate) &&
            Objects.equals(lastUpdatedUtc, that.lastUpdatedUtc) &&
            Objects.equals(classes, that.classes);
    }


    @Override
    public int hashCode() {
        return Objects.hash(
            classesTotalCount,
            classesOffset,
            classesCount,
            code,
            uri,
            name,
            version,
            organizationCodeOwner,
            organizationNameOwner,
            defaultLanguageCode,
            isLatestVersion,
            isVerified,
            license,
            licenseUrl,
            qualityAssuranceProcedure,
            qualityAssuranceProcedureUrl,
            status,
            moreInfoUrl,
            releaseDate,
            lastUpdatedUtc,
            classes
            );

    }

    @Override
    public String toString() {
        return "DictionaryClassesResponseContractV1Classes{" + 
                "classesTotalCount=" + classesTotalCount + '\'' + 
                "classesOffset=" + classesOffset + '\'' + 
                "classesCount=" + classesCount + '\'' + 
                "code=" + code + '\'' + 
                "uri=" + uri + '\'' + 
                "name=" + name + '\'' + 
                "version=" + version + '\'' + 
                "organizationCodeOwner=" + organizationCodeOwner + '\'' + 
                "organizationNameOwner=" + organizationNameOwner + '\'' + 
                "defaultLanguageCode=" + defaultLanguageCode + '\'' + 
                "isLatestVersion=" + isLatestVersion + '\'' + 
                "isVerified=" + isVerified + '\'' + 
                "license=" + license + '\'' + 
                "licenseUrl=" + licenseUrl + '\'' + 
                "qualityAssuranceProcedure=" + qualityAssuranceProcedure + '\'' + 
                "qualityAssuranceProcedureUrl=" + qualityAssuranceProcedureUrl + '\'' + 
                "status=" + status + '\'' + 
                "moreInfoUrl=" + moreInfoUrl + '\'' + 
                "releaseDate=" + releaseDate + '\'' + 
                "lastUpdatedUtc=" + lastUpdatedUtc + '\'' +
                "classes=" + classes + '\'' +
                "}";
    }
}

// =====================================================================================================================
// Working Approach ..... just in case
// @JsonTypeName("DictionaryClassesResponseContract.v1.Classes")
// public class DictionaryClassesResponseContractV1Classes {

    // @JsonProperty("name")
    // private String name;

    // @JsonProperty("version")
    // private String version;

    // @JsonProperty("classes")
    // private List<ClassListItemContractV1Classes> classes = new ArrayList<>();

    // public DictionaryClassesResponseContractV1Classes() {}

    // public String getName() {
    //     return name;
    // }

    // public void setName(String name) {
    //     this.name = name;
    // }

    // public String getVersion() {
    //     return version;
    // }

    // public void setVersion(String version) {
    //     this.version = version;
    // }

    // public List<ClassListItemContractV1Classes> getClasses() {
    //     return classes;
    // }

    // public void setClasses(List<ClassListItemContractV1Classes> classes) {
    //     this.classes = classes;
    // }

    // @Override
    // public boolean equals(Object o) {
    //     if (this == o) return true;
    //     if (o == null || getClass() != o.getClass()) return false;
    //     DictionaryClassesResponseContractV1Classes that = (DictionaryClassesResponseContractV1Classes) o;
    //     return Objects.equals(name, that.name) &&
    //         Objects.equals(version, that.version)&&
    //         Objects.equals(classes, that.classes);
    // }

    // @Override
    // public int hashCode() {
    //     return Objects.hash(name, version, classes);

    // }

    // @Override
    // public String toString() {
    //     return "DictionaryClassesResponseContractV1Classes{" + 
    //             ", name='" + name + '\'' +
    //             "version=" + version + '\'' + 
    //             "classes=" + classes + '\'' +
    //             "}";
    // }
// }