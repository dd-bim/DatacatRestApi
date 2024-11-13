package datacat.models;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import datacat.customization.DefaultValuesHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


// =====================================================================================================================
// M O D E L   C L A S S   S E C T I O N
// 
// =====================================================================================================================
@JsonTypeName("ClassPropertiesContract.v1")
public class ClassPropertiesContractV1 {

    private static final Logger logger = LoggerFactory.getLogger(ClassPropertiesContractV1.class);

    @JsonProperty("totalCount")
    private Integer totalCount;

    @JsonProperty("offset")
    private Integer offset;

    @JsonProperty("count")
    private Integer count;

    @JsonProperty("classUri")
    private String classUri;

    @JsonProperty("classProperties")
    private List<ClassPropertyItemContractV1> classProperties = new ArrayList<>();


    // =====================================================================================================================
    // non-argument constructor
    public ClassPropertiesContractV1() {
        DefaultValuesHandler.ensureDefaults(this);
    }

    
    // =====================================================================================================================
    // getters and setters
    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
        logger.debug("Set totalCount: {}", totalCount);
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
        logger.debug("Set offset: {}", offset);
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
        logger.debug("Set count: {}", count);
    }

    public String getClassUri() {
        return classUri;
    }

    public void setClassUri(String classUri) {
        this.classUri = classUri;
    }

    public List<ClassPropertyItemContractV1> getClassProperties() {
        return classProperties != null ? classProperties : new ArrayList<>();
    }

    public void setClassProperties(List<ClassPropertyItemContractV1> classProperties) {
        this.classProperties = classProperties;
    }

    // =====================================================================================================================
    // business logic method
    public void generateUri(String serverUrl) {
        if (this.classUri != null) {
            this.classUri = serverUrl + "/class/" + this.classUri;
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassPropertiesContractV1 that = (ClassPropertiesContractV1) o;
        return Objects.equals(totalCount, that.totalCount) &&
                Objects.equals(offset, that.offset) &&
                Objects.equals(count, that.count) &&
                Objects.equals(classUri, that.classUri) &&
                Objects.equals(classProperties, that.classProperties);
    }


    @Override
    public int hashCode() {
        return Objects.hash(
            totalCount,
            offset,
            count,
            classUri,
            classProperties
        );
    }

    @Override
    public String toString() {
        return "ClassPropertiesContractV1{" +
                "totalCount=" + totalCount + '\'' +
                ", offset=" + offset + '\'' +
                ", count=" + count + '\'' +
                ", classUri='" + classUri + '\'' +
                ", classProperties=" + classProperties + '\'' +
                '}';
    }
}