package datacat.models;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
import java.util.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.format.annotation.DateTimeFormat;

// =====================================================================================================================
// M O D E L   C L A S S   S E C T I O N
// model class holds several nested classes, that are used to represent the structure of the JSON response
// new properties are added to the 'Node' class
// '@JsonProperty("")'' annotation is used to define exact name of property in JSON representation
// private fields are being shown in the Swagger UI as a schema
// =====================================================================================================================
@JsonTypeName("ClassDetailsResponse.v1")
public class ClassDetailsResponseV1 {


    // =====================================================================================================================
    // 
    // classProperties works as a list of objects, is linked to its own class
    // NEW PROPERTIES ?? WORK HERE!

    @JsonProperty("classProperties")
    private List<ClassPropertyResponseV1> classProperties;

    @JsonProperty("dictionaryUri")
    private String dictionaryUri;

    @JsonProperty("definition")
    private String definition;

    @JsonProperty("name")
    private String name;

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("uid")
    private String uid;

    @JsonProperty("versionDateUtc")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private String versionDateUtc;

    public List<ClassPropertyResponseV1> getClassProperties() {
        return classProperties;
    }

    public void setClassProperties(List<ClassPropertyResponseV1> classProperties) {
        this.classProperties = classProperties;
    }

    public String getDictionaryUri() {
        return dictionaryUri;
    }

    public void setDictionaryUri(String dictionaryUri) {
        this.dictionaryUri = dictionaryUri;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getVersionDateUtc() {
        return versionDateUtc;
    }

    public void setVersionDateUtc(String versionDateUtc) {
        this.versionDateUtc = versionDateUtc;
    }

    // equals, hashCode, and toString methods (similar to ClassDetailsV1)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClassDetailsResponseV1 that = (ClassDetailsResponseV1) o;
        return Objects.equals(classProperties, that.classProperties) &&
                Objects.equals(dictionaryUri, that.dictionaryUri) &&
                Objects.equals(definition, that.definition) &&
                Objects.equals(name, that.name) &&
                Objects.equals(uri, that.uri) &&
                Objects.equals(uid, that.uid) &&
                Objects.equals(versionDateUtc, that.versionDateUtc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(classProperties, dictionaryUri, definition, name, uri, uid, versionDateUtc);
    }

    @Override
    public String toString() {
        return "{" +
                "classProperties=" + classProperties +
                ", dictionaryUri=" + dictionaryUri +
                ", definition=" + definition +
                ", name='" + name + '\'' +
                ", uri='" + uri + '\'' +
                ", uid=" + uid +
                ", versionDateUtc=" + versionDateUtc +
                '}';
    }


    // =====================================================================================================================
    // nested static class for 'ClassPropertyResponseV1' featuring 'propertyName', getter and setter, method overrides
    public static class ClassPropertyResponseV1 {

        @JsonProperty("propertyName")
        private String propertyName;


        public String getPropertyName() {
            return propertyName;
        }

        public void setPropertyName(String propertyName) {
            this.propertyName = propertyName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            
            ClassPropertyResponseV1 that = (ClassPropertyResponseV1) o;
            return Objects.equals(propertyName, that.propertyName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(propertyName);
        }

        @Override
        public String toString() {
            return "{" +
                    "propertyName='" + propertyName + '\'' +
                    '}';
        }
    }
}