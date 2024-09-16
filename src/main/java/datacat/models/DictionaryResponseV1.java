package datacat.models;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
import java.util.Objects;
import com.fasterxml.jackson.annotation.*;

// =====================================================================================================================
// M O D E L   C L A S S   S E C T I O N
// model class holds several nested classes, that are used to represent the structure of the JSON response
// new properties are added to the 'Node' class
// '@JsonProperty("")'' annotation is used to define exact name of property in JSON representation
// private fields are being shown in the Swagger UI as a schema
// =====================================================================================================================
@JsonTypeName("DictionaryResponse.v1")
public class DictionaryResponseV1 {

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("name")
    private String name;

    @JsonProperty("version")
    private String version;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DictionaryResponseV1 that = (DictionaryResponseV1) o;
        return Objects.equals(uri, that.uri) &&
                Objects.equals(name, that.name) &&
                Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uri, name, version);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class DictionaryResponseV1 {\n");
        sb.append("    uri: ").append(toIndentedString(uri)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    version: ").append(toIndentedString(version)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private String toIndentedString(Object o) {
        if (o == null) return "null";
        return o.toString().replace("\n", "\n    ");
    }
}