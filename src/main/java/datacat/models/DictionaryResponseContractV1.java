package datacat.models;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

// internal
import datacat.customization.DefaultValuesHandler;


// =====================================================================================================================
// M O D E L   C L A S S   S E C T I O N
// 
// =====================================================================================================================
@JsonTypeName("DictionaryResponseContract.v1")
public class DictionaryResponseContractV1 {

    @JsonProperty("totalCount")
    private Integer totalCount;

    @JsonProperty("offset")
    private Integer offset;

    @JsonProperty("count")
    private Integer count;

    @JsonProperty("dictionaries")
    private List<DictionaryContractV1> dictionaries = new ArrayList<>();


    // =====================================================================================================================
    // non-argument constructor
    public DictionaryResponseContractV1() {
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
    
    public List<DictionaryContractV1> getDictionaries() {
        return dictionaries;
    }
    
    public void setDictionaries(List<DictionaryContractV1> dictionaries) {
        this.dictionaries = dictionaries;
    }

    // =====================================================================================================================
    // standard object methods equals, hashCode, and toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DictionaryResponseContractV1 that = (DictionaryResponseContractV1) o;
        return 
            Objects.equals(totalCount, that.totalCount) &&
            Objects.equals(offset, that.offset) &&
            Objects.equals(count, that.count) &&
            Objects.equals(dictionaries, that.dictionaries);
    }


    @Override
    public int hashCode() {
        return Objects.hash(
            totalCount,
            offset,
            count,
            dictionaries
        );
    }

    @Override
    public String toString() {
        return "DictionaryResponseContractV1{" +
                "totalCount=" + totalCount + 
                "offset=" + offset + 
                "count=" + count + 
                "dictionaries=" + dictionaries + 
                "}";
    }
}

