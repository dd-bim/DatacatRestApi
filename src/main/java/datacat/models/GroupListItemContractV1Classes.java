package datacat.models;

import java.util.*;
import com.fasterxml.jackson.annotation.*;


@JsonTypeName("GroupListItemContract.v1.Classes")
public class GroupListItemContractV1Classes {
    @JsonProperty("internalGroupId")
    private String internalGroupId;

    @JsonProperty("groupName")
    private String groupName;

    public GroupListItemContractV1Classes() {}

    public String getInternalGroupId() {
        return internalGroupId;
    }

    public void setInternalGroupId(String internalGroupId) {
        this.internalGroupId = internalGroupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupListItemContractV1Classes that = (GroupListItemContractV1Classes) o;
        return Objects.equals(internalGroupId, that.internalGroupId) &&
               Objects.equals(groupName, that.groupName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(internalGroupId, groupName);
    }

    @Override
    public String toString() {
        return "{" +
                "internalGroupId='" + internalGroupId + '\'' + 
                ", groupName='" + groupName + '\'' + 
                "}";
    }
}