package datacat.models;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
import java.util.Objects;
// import java.util.List;
// import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import datacat.customization.DefaultValuesHandler;


// =====================================================================================================================
// M O D E L   C L A S S   S E C T I O N
// 
// =====================================================================================================================
@JsonTypeName("ClassListItemContract.v1.Classes")
public class ClassListItemContractV1Classes {

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("code")
    private String code;

    @JsonProperty("name")
    private String name;

    @JsonProperty("classType")
    private String classType;

    @JsonProperty("referenceCode")
    private String referenceCode;

    @JsonProperty("parentClassCode")
    private String parentClassCode;

    @JsonProperty("descriptionPart")
    private String descriptionPart;

    // @JsonProperty("children")
    // private List<ClassListItemContractV1Classes> children = new ArrayList<>();

    // =====================================================================================================================
    // non-argument constructor
    public ClassListItemContractV1Classes() {
        DefaultValuesHandler.ensureDefaults(this);
    }

    // =====================================================================================================================
    // getters and setters
    public String getUri() {
        return uri;
    }
    
    public void setUri(String uri) {
        this.uri = uri;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getClassType() {
        return classType;
    }
    
    public void setClassType(String classType) {
        this.classType = classType;
    }
    
    public String getReferenceCode() {
        return referenceCode;
    }
    
    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }
    
    public String getParentClassCode() {
        return parentClassCode;
    }
    
    public void setParentClassCode(String parentClassCode) {
        this.parentClassCode = parentClassCode;
    }
    
    public String getDescriptionPart() {
        return descriptionPart;
    }
    
    public void setDescriptionPart(String descriptionPart) {
        this.descriptionPart = descriptionPart;
    }
    
    // public List<ClassListItemContractV1Classes> getChildren() {
    //     return children;
    // }
    
    // public void setChildren(List<ClassListItemContractV1Classes> children) {
    //     this.children = children;
    // }

    // =====================================================================================================================
    // business logic method
    public void generateUri(String serverUrl) {
        if (this.uri != null) {
            this.uri = serverUrl + "/class/" + this.uri; // Construct the URI by combining the base URL with the uid (which holds the id)
        }
    }

    public void transformToLowerCase() {
        if (this.code != null) {
            this.code = this.code.toLowerCase();
        }
    }


    // standard object methods equals, hashCode, and toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassListItemContractV1Classes that = (ClassListItemContractV1Classes) o;
        return 
            Objects.equals(uri, that.uri) &&
            Objects.equals(code, that.code) &&
            Objects.equals(name, that.name) &&
            Objects.equals(classType, that.classType) &&
            Objects.equals(referenceCode, that.referenceCode) &&
            Objects.equals(parentClassCode, that.parentClassCode) &&
            Objects.equals(descriptionPart, that.descriptionPart);
            // Objects.equals(children, that.children);
    }


    @Override
    public int hashCode() {
        return Objects.hash(
            uri,
            code,
            name,
            classType,
            referenceCode,
            parentClassCode,
            descriptionPart
            // children
        );

    }

    @Override
    public String toString() {
        return "ClassListItemContractV1Classes{" +
                "uri=" + uri + 
                "code=" + code + 
                "name=" + name + 
                "classType=" + classType + 
                "referenceCode=" + referenceCode + 
                "parentClassCode=" + parentClassCode + 
                "descriptionPart=" + descriptionPart + 
                // "children=" + children + 
                "}";
    }
}

// =====================================================================================================================
// Working Approach .... just in case
// @JsonTypeName("ClassListItemContract.v1.Classes")
// public class ClassListItemContractV1Classes {

//     @JsonProperty("name")
//     private String name;

//     public ClassListItemContractV1Classes() {}

//     public String getName() {
//         return name;
//     }

//     public void setName(String name) {
//         this.name = name;
//     }

//     @Override
//     public boolean equals(Object o) {
//         if (this == o) return true;
//         if (o == null || getClass() != o.getClass()) return false;
//         ClassListItemContractV1Classes that = (ClassListItemContractV1Classes) o;
//         return Objects.equals(name, that.name);
//     }

//     @Override
//     public int hashCode() {
//         return Objects.hash(name);
//     }

//     @Override
//     public String toString() {
//         return "ClassListItemContractV1Classes{" + 
//                 "name=" + name + 
//                 "}";
//     }
// }