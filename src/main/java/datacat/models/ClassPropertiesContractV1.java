package datacat.models;


// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
import java.util.*;
import jakarta.validation.Valid;
import lombok.Data;

import com.fasterxml.jackson.annotation.*;
import datacat.customization.DefaultValuesHandler;

// =====================================================================================================================
// M O D E L   C L A S S   S E C T I O N
// 
// =====================================================================================================================
@JsonTypeName("ClassPropertiesContract.v1")
@Data
public class ClassPropertiesContractV1 {

    @JsonProperty("totalCount")
    private Integer totalCount;

    @JsonProperty("offset")
    private Integer offset = 0;

    @JsonProperty("count")
    private Integer count = 0;

    @JsonProperty("classUri")
    private String classUri;

    @JsonProperty("classProperties")
    private List<@Valid ClassPropertyContractV1> classProperties = new ArrayList<>();


    // =====================================================================================================================
    // setting default values
    public ClassPropertiesContractV1() {
        DefaultValuesHandler.ensureDefaults(this);
    }

    // =====================================================================================================================
    // business logic method
    public void generateUri(String serverUrl) {
        if(this.classUri != null) {
            this.classUri = serverUrl + "/class/" + this.classUri;
        }
    }
}
