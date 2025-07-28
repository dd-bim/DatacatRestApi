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
import datacat.models.deserializers.DictionaryClassesResponseDeserializer;

// =====================================================================================================================
// M O D E L   C L A S S   S E C T I O N
// 
// =====================================================================================================================
@JsonTypeName("DictionaryClassesResponseContract.v1.Classes")
@JsonDeserialize(using = DictionaryClassesResponseDeserializer.class)
@Data
public class DictionaryClassesResponseContractV1Classes {

    private String code;

    private String uri;

    private String name;

    private String version;

    private String organizationCodeOwner;

    private String organizationNameOwner;

    private String defaultLanguageCode;

    private Boolean isLatestVersion;

    private Boolean isVerified;

    private String license;

    private String licenseUrl;

    private String qualityAssuranceProcedure;

    private String qualityAssuranceProcedureUrl;

    private String status;

    private String moreInfoUrl;

    private Date releaseDate;

    private Date lastUpdatedUtc;

    private List<@Valid ClassListItemContractV1Classes> classes = new ArrayList<>();

    private Integer classesTotalCount;

    private Integer classesOffset;

    private Integer classesCount;


    // =====================================================================================================================
    // setting default values
    public DictionaryClassesResponseContractV1Classes() {
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
