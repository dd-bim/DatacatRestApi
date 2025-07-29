package datacat.models;


// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
import com.fasterxml.jackson.annotation.*;
import datacat.customization.DefaultValuesHandler;
import lombok.Data;

// =====================================================================================================================
// M O D E L   C L A S S   S E C T I O N
// 
// =====================================================================================================================
@JsonTypeName("ClassReferenceContract.v1")
@Data
public class ClassReferenceContractV1 {

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("name")
    private String name;

    @JsonProperty("code")
    private String code;


    // =====================================================================================================================
    // setting default values
    public ClassReferenceContractV1() {
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
        }
    }
}
