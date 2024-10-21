package datacat.models;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.Arrays;
import org.openapitools.jackson.nullable.JsonNullable;
import java.util.NoSuchElementException;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ClassRelationsContractV1
 */

@JsonTypeName("ClassRelationsContract.v1")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-10-21T13:00:04.898874600+02:00[Europe/Berlin]", comments = "Generator version: 7.8.0")
public class ClassRelationsContractV1 {

  private JsonNullable<String> classUri = JsonNullable.<String>undefined();

  public ClassRelationsContractV1 classUri(String classUri) {
    this.classUri = JsonNullable.of(classUri);
    return this;
  }

  /**
   * Uri of the class
   * @return classUri
   */
  
  @Schema(name = "classUri", description = "Uri of the class", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("classUri")
  public JsonNullable<String> getClassUri() {
    return classUri;
  }

  public void setClassUri(JsonNullable<String> classUri) {
    this.classUri = classUri;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ClassRelationsContractV1 classRelationsContractV1 = (ClassRelationsContractV1) o;
    return equalsNullable(this.classUri, classRelationsContractV1.classUri);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b || (a != null && b != null && a.isPresent() && b.isPresent() && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(hashCodeNullable(classUri));
  }

  private static <T> int hashCodeNullable(JsonNullable<T> a) {
    if (a == null) {
      return 1;
    }
    return a.isPresent() ? Arrays.deepHashCode(new Object[]{a.get()}) : 31;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ClassRelationsContractV1 {\n");
    sb.append("    classUri: ").append(toIndentedString(classUri)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

