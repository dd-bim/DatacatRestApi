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
@JsonTypeName("ClassPropertyResponse.v1")
public class ClassPropertyResponseV1 {

    //===================================================================================================================
    // preparation for the field from the json response
    // class has no nested classes, thus starts without 'data'
    @JsonProperty("name")
    private String name;

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("description")
    private String description;

    @JsonProperty("propertyUri")
    private String propertyUri;

    @JsonProperty("propertySet")
    private String propertySet;

    public ClassPropertyResponseV1() {
    }

    // getter and setter for the fields
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPropertyUri() {
        return propertyUri;
    }

    public void setPropertyUri(String propertyUri) {
        this.propertyUri = propertyUri;
    }

    public String getPropertySet() {
        return propertySet;
    }

    public void setPropertySet(String propertySet) {
        this.propertySet = propertySet;
    }

    // equals, hashCode, and toString methods are overridden
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClassPropertyResponseV1 that = (ClassPropertyResponseV1) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(uri, that.uri) &&
                Objects.equals(description, that.description) &&
                Objects.equals(propertyUri, that.propertyUri) &&
                Objects.equals(propertySet, that.propertySet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, uri, description, propertyUri, propertySet);
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", uri='" + uri + '\'' +
                ", description='" + description + '\'' +
                ", propertyUri='" + propertyUri + '\'' +
                ", propertySet='" + propertySet + '\'' +
                '}';
    }
}