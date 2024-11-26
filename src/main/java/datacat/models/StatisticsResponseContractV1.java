package datacat.models;


// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
import java.util.*;
import com.fasterxml.jackson.annotation.*;

// =====================================================================================================================
// M O D E L   C L A S S   S E C T I O N
// this class was specifically modified through a nested class structure to fit the deserialization requirements
// =====================================================================================================================
@JsonTypeName("StatisticsResponseContract.v1")
public class StatisticsResponseContractV1 {

    // =====================================================================================================================
    @JsonProperty("catalogueItems")
    private List<CatalogueItems> items;

    // =====================================================================================================================
    // getter and setter
    public List<CatalogueItems> getItems() {
        return items;
    }
    public void setItems(List<CatalogueItems> items) {
        this.items = items;
    }

    // =====================================================================================================================
    // standard method overrides
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StatisticsResponseContractV1 that = (StatisticsResponseContractV1) o;
        return Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items);
    }

    @Override
    public String toString() {
        return
            "{" +
            "catalogueItems=" + items + '\'' +
            "}";
    }
    
    // =====================================================================================================================
    // nested class 'CatalogueItem' featuring 'id', 'count', getter and setter, method overrides
    public static class CatalogueItems {
        @JsonProperty("itemId")
        private String itemId;
    
        @JsonProperty("itemCount")
        private int itemCount;

        // =====================================================================================================================
        // getter and setter
        public String getItemId() {
            return itemId;
        }
        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public int getItemCount() {
            return itemCount;
        }
        public void setItemCount(int itemCount) {
            this.itemCount = itemCount;
        }

        // =====================================================================================================================
        // standard object methods overrides
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CatalogueItems that = (CatalogueItems) o;
            return
                Objects.equals(itemId, that.itemId) &&
                Objects.equals(itemCount, that.itemCount);
        }

        @Override
        public int hashCode() {
            return Objects.hash(
                itemId,
                itemCount
            );
        }

        @Override
        public String toString() {
            return 
                "StatisticsResponseContractV1{" +
                "itemId=" + itemId + '\'' +
                ", itemCount=" + itemCount + '\'' +
                "}";
        }
    }
}