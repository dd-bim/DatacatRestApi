package datacat.models;


// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
import java.util.*;
import com.fasterxml.jackson.annotation.*;
import datacat.customization.DefaultValuesHandler;

// =====================================================================================================================
// M O D E L   C L A S S   S E C T I O N
// 
// =====================================================================================================================
@JsonTypeName("ReferenceDocumentContract.v1")
public class ReferenceDocumentContractV1 {

    @JsonProperty("title")
    private String title;

    @JsonProperty("name")
    private String name;

    @JsonProperty("date")
    private String date;


    // =====================================================================================================================
    // setting default values
    public ReferenceDocumentContractV1() {
        DefaultValuesHandler.ensureDefaults(this);
    }

    
    // =====================================================================================================================
    // getters and setters
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }


    // =====================================================================================================================
    // business logic method
    // standard object methods equals, hashCode, and toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReferenceDocumentContractV1 that = (ReferenceDocumentContractV1) o;
        return 
            Objects.equals(title, that.title) &&
            Objects.equals(name, that.name) &&
            Objects.equals(date, that.date);
    }


    @Override
    public int hashCode() {
        return Objects.hash(
            title,
            name,
            date
        );
    }

    @Override
    public String toString() {
        return 
            "ReferenceDocumentContractV1{" +
            "title=" + title + '\'' +
            ", name=" + name + '\'' +
            ", date=" + date + '\'' +
            "}";
    }
}
