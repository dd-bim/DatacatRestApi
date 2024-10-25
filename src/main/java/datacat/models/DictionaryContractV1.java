package datacat.models;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;


// =====================================================================================================================
// M O D E L   C L A S S   S E C T I O N
// 
// =====================================================================================================================
@JsonTypeName("DictionaryContract.v1")
public class DictionaryContractV1 {

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("name")
    private String name;

    @JsonProperty("version")
    private String version;


    // =====================================================================================================================
    // non-argument constructor
    public DictionaryContractV1() {}

    
    // =====================================================================================================================
    // getters and setters
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    // =====================================================================================================================
    // public void generateUriFromUid() {
    //     if (this.uid != null) {
    //         this.uri = "https://datacat.org/model/" + this.uid; // Construct the URI by combining the base URL with the uid (which holds the id)
    //     }
    // }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DictionaryContractV1 dictionaryContractV1 = (DictionaryContractV1) o;
        return Objects.equals(uri, dictionaryContractV1.uri) &&
                Objects.equals(name, dictionaryContractV1.name) &&
                Objects.equals(version, dictionaryContractV1.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            uri, 
            name, 
            version
        );
    }

    @Override
    public String toString() {
        return "DictionaryContractV1{" +
                "uri='" + uri + '\'' +
                ", name='" + name + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}