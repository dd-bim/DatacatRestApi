package datacat.models;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
import java.util.*;
import com.fasterxml.jackson.annotation.*;

// =====================================================================================================================
// M O D E L   C L A S S   S E C T I O N
// model class holds several nested classes, that are used to represent the structure of the JSON response
// new properties are added to the 'Node' class
// '@JsonProperty("")'' annotation is used to define exact name of property in JSON representation
// private fields are being shown in the Swagger UI as a schema
// =====================================================================================================================
@JsonTypeName("DictionaryResponseContract.v1")
public class DictionaryResponseContractV1 {

    @JsonProperty("totalCount")
    private int totalCount;

    @JsonProperty("offset")
    private int offset;

    @JsonProperty("count")
    private int count;
    
    @JsonProperty("dictionaries")
    private List<DictionaryContractV1> dictionaries = new ArrayList<>();

    // =================================================================================================================
    // non-argument constructor
    public DictionaryResponseContractV1() {}

    // =================================================================================================================
    // getters and setters
    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<DictionaryContractV1> getDictionaries() {
        return dictionaries;
    }

    public void setDictionaries(List<DictionaryContractV1> dictionaries) {
        this.dictionaries = dictionaries;
    }

    // =================================================================================================================
    // standard object methods equals, hashCode, and toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DictionaryResponseContractV1 that = (DictionaryResponseContractV1) o;
        return totalCount == that.totalCount &&
                offset == that.offset &&
                count == that.count &&
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
                ", offset=" + offset +
                ", count=" + count +
                ", dictionaries=" + dictionaries +
                '}';
    }
}


    
//     // =================================================================================================================
//     // preparation for 'nodes' object list as 'dictionaries' 
//     @JsonProperty("dictionaries")
//     private List<Node> dictionaries;

//     @JsonProperty("dictionariesTotalCount")
//     private int dictionariesTotalCount;

//     public List<Node> getDictionaries() {
//         return dictionaries;
//     }

//     public void setDictionaries(List<Node> dictionaries) {
//         this.dictionaries = dictionaries;
//     }

//     public int getDictionariesTotalCount() {
//         return dictionariesTotalCount;
//     }

//     public void setDictionariesTotalCount(int dictionariesTotalCount) {
//         this.dictionariesTotalCount = dictionariesTotalCount;
//     }

//     @Override
//     public boolean equals(Object o) {
//         if (this == o) return true;
//         if (o == null || getClass() != o.getClass()) return false;

//         DictionaryResponseContractV1 that = (DictionaryResponseContractV1) o;
//             return dictionariesTotalCount == that.dictionariesTotalCount &&
//                     Objects.equals(dictionaries, that.dictionaries);
//     }

//     @Override
//     public int hashCode() {
//         return Objects.hash(dictionaries, dictionariesTotalCount);
//     }   

//     @Override
//     public String toString() {
//         StringBuilder sb = new StringBuilder();
//         sb.append("class DictionaryResponseContractV1 {\n");
//         sb.append("    dictionaries: ").append(toIndentedString(dictionaries)).append("\n");
//         sb.append("    dictionariesTotalCount: ").append(toIndentedString(dictionariesTotalCount)).append("\n");
//         sb.append("}");
//         return sb.toString();
//     }

//     // =================================================================================================================
//     // nested class 'dictionaries'
//     public static class Node {

//         @JsonProperty("uri")
//         private String id;

//         @JsonProperty("name")
//         private String name;

//         @JsonProperty("version")
//         private String versionId;

//         public String getId() {
//             return id;
//         }

//         public void setId(String id) {
//             this.id = id;
//         }

//         public String getName() {
//             return name;
//         }

//         public void setName(String name) {
//             this.name = name;
//         }

//         public String getVersionId() {
//             return versionId;
//         }

//         public void setVersionId(String versionId) {
//             this.versionId = versionId;
//         }

//         @Override
//         public boolean equals(Object o) {
//             if (this == o) return true;
//             if (o == null || getClass() != o.getClass()) return false;
//             Node node = (Node) o;
//             return Objects.equals(id, node.id) &&
//                     Objects.equals(name, node.name) &&
//                     Objects.equals(versionId, node.versionId);
//         }

//         @Override
//         public int hashCode() {
//             return Objects.hash(id, name, versionId);
//         }

//         @Override
//         public String toString() {
//             StringBuilder sb = new StringBuilder();
//             sb.append("class Node {\n");
//             sb.append("    id: ").append(toIndentedString(id)).append("\n");
//             sb.append("    name: ").append(toIndentedString(name)).append("\n");
//             sb.append("    versionId: ").append(toIndentedString(versionId)).append("\n");
//             sb.append("}");
//             return sb.toString();
//         }
//     }

//     private static String toIndentedString(Object o) {
//         if (o == null) return "null";
//         return o.toString().replace("\n", "\n    ");
//     }
// }