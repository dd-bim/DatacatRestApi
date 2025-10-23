package datacat.models;


// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
// import jakarta.validation.Valid;
import com.fasterxml.jackson.annotation.*;
import datacat.customization.DefaultValuesHandler;
import lombok.Data;

// =====================================================================================================================
// M O D E L   C L A S S   S E C T I O N
// 
// =====================================================================================================================
@JsonTypeName("ClassListItemContract.v1.Classes")
@Data
public class ClassListItemContractV1Classes {

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("code")
    private String code;

    @JsonProperty("name")
    private String name;

    @JsonProperty("classType")
    private String classType;

    @JsonProperty("referenceCode")
    private String referenceCode;

    @JsonProperty("parentClassCode")
    private String parentClassCode;

    @JsonProperty("descriptionPart")
    private String descriptionPart;

    @JsonProperty("__typename")
    private String typename;

    // @JsonProperty("children")
    // private List<@Valid ClassListItemContractV1Classes> children = new ArrayList<>();


    // =====================================================================================================================
    // setting default values
    public ClassListItemContractV1Classes() {
        DefaultValuesHandler.ensureDefaults(this);
    }

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
        } else {
            this.code = this.name.toLowerCase();
        }
    }
}
