package datacat.models;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
import java.util.*;
import com.fasterxml.jackson.annotation.*;

// =====================================================================================================================
// M O D E L   C L A S S   S E C T I O N
// model class holds several nested classes, that are used to represent the structure of the JSON response
// =====================================================================================================================
@JsonTypeName("StatisticsResponseContract.v1")
public class StatisticsResponseContractV1 {

    // =====================================================================================================================
    //
    //
    @JsonProperty("catalogueItem")
    private List<CatalogueItem> items;

    public List<CatalogueItem> getItems() {
        return items;
    }

    public void setItems(List<CatalogueItem> items) {
        this.items = items;
    }

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
        return "{" +
                "items=" + items +
                '}';
    }
    
    
    // =====================================================================================================================
    // nested class 'CatalogueItem' featuring 'id', 'count', getter and setter, method overrides
    public static class CatalogueItem {
        @JsonProperty("id")
        private String id;

        @JsonProperty("count")
        private int count;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            CatalogueItem item = (CatalogueItem) o;
            return count == item.count && Objects.equals(id, item.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, count);
        }

        @Override
        public String toString() {
            return "{" +
                    "id=" + id + '\'' +
                    ", count=" + count +
                    '}';
        }
    }
}