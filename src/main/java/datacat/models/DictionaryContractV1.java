package datacat.models;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
import java.util.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.format.annotation.DateTimeFormat;
import datacat.customization.DefaultValuesHandler;
import datacat.models.deserializers.DictionaryDeserializer;
import datacat.models.dto.LanguageDto;
import lombok.Data;

// =====================================================================================================================
// M O D E L   C L A S S   S E C T I O N
// 
// =====================================================================================================================
@JsonTypeName("DictionaryContract.v1")
@JsonDeserialize(using = DictionaryDeserializer.class)
@Data
public class DictionaryContractV1 {

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

    @JsonProperty("changeRequestEmail")
    private String changeRequestEmail;

    @JsonProperty("defaultLanguageCode")
    private String defaultLanguageCode;

    @JsonProperty("isLatestVersion")
    private Boolean isLatestVersion;

    @JsonProperty("isVerified")
    private Boolean isVerified;

    @JsonProperty("isPrivate")
    private Boolean isPrivate;

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

    @JsonProperty("availableLanguages")
    private List<LanguageDto> availableLanguages = new ArrayList<>();


    // =====================================================================================================================
    // setting default values
    public DictionaryContractV1() {
        DefaultValuesHandler.ensureDefaults(this);
    }

    // =====================================================================================================================
    // business logic method
    public void generateUri(String serverUrl) {
        if(this.uri != null) {
            this.uri = serverUrl + "/model/" + this.uri;
        }
    }

    public void transformToLowerCase() {
        if(this.code != null) {
            this.code = this.code.toLowerCase();
        } else {
            this.code = this.name.toLowerCase();
        }
    }

}
