package datacat.models;


// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
import java.util.*;
import jakarta.validation.Valid;
import com.fasterxml.jackson.annotation.*;
import datacat.customization.DefaultValuesHandler;

// =====================================================================================================================
// M O D E L   C L A S S   S E C T I O N
// 
// =====================================================================================================================
@JsonTypeName("PropertyClassesContract.v1")
public class PropertyClassesContractV1 {

    @JsonProperty("totalCount")
    private Integer totalCount;

    @JsonProperty("offset")
    private Integer offset = 0;

    @JsonProperty("count")
    private Integer count;

    @JsonProperty("propertyUri")
    private String propertyUri;

    @JsonProperty("propertyClasses")
    private List<@Valid PropertyClassItemContractV1> propertyClasses;


    // =====================================================================================================================
    // setting default values
    public PropertyClassesContractV1() {
        DefaultValuesHandler.ensureDefaults(this);
    }

    
    // =====================================================================================================================
    // getters and setters
    public Integer getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getOffset() {
        return offset;
    }
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getCount() {
        return count;
    }
    public void setCount(Integer count) {
        this.count = count;
    }

    public String getPropertyUri() {
        return propertyUri;
    }
    public void setPropertyUri(String propertyUri) {
        this.propertyUri = propertyUri;
    }

    public List<@Valid PropertyClassItemContractV1> getPropertyClasses() {
        return propertyClasses;
    }
    public void setPropertyClasses(List<@Valid PropertyClassItemContractV1> propertyClasses) {
        this.propertyClasses = propertyClasses;
    }


    // =====================================================================================================================
    // business logic method
    public void generateUri(String serverUrl) {
        if(this.propertyUri != null) {
            this.propertyUri = serverUrl + "/property/" + this.propertyUri;
        }
    }


    // standard object methods equals, hashCode, and toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyClassesContractV1 that = (PropertyClassesContractV1) o;
        return 
            Objects.equals(totalCount, that.totalCount) &&
            Objects.equals(offset, that.offset) &&
            Objects.equals(count, that.count) &&
            Objects.equals(propertyUri, that.propertyUri) &&
            Objects.equals(propertyClasses, that.propertyClasses);
    }


    @Override
    public int hashCode() {
        return Objects.hash(
            totalCount,
            offset,
            count,
            propertyUri,
            propertyClasses
        );
    }

    @Override
    public String toString() {
        return 
            "PropertyClassesContractV1{" +
            "totalCount=" + totalCount + '\'' +
            ", offset=" + offset + '\'' +
            ", count=" + count + '\'' +
            ", propertyUri=" + propertyUri + '\'' +
            ", propertyClasses=" + propertyClasses + '\'' +
            "}";
    }
}
