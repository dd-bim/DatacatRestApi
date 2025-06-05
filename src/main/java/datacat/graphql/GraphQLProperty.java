package datacat.graphql;

// =====================================================================================================================
// C L A S S   S E C T I O N
// 
// =====================================================================================================================
public class GraphQLProperty {

    // =====================================================================================================================
    // SECTION: PROPERTY
    // =====================================================================================================================
    // ENDPOINT: /api/Property/v4
    public static String getPropertyDetailsQuery(String id, boolean includeClasses, String languageCode) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("{ getProperty(id: \\\"" + id + "\\\") { ")
                    .append("assignedTo { nodes { relatingObject { collectedBy { nodes { relatingCollection { collectedBy { nodes { relatingCollection { dictionaryUri:id}}}}}}}}}")
                    .append("uid:id ")
                    .append("uri:id ")
                    .append("code:name ")
                    .append("activationDateUtc:created ")
                    .append("description ")
                    .append("documentedBy { nodes { relatingDocument { documentReference:name } } } ");

        if (languageCode != null && !languageCode.isEmpty()) {
            queryBuilder.append("name(input:{languageTags:\\\"" + languageCode + "\\\"}) ");
        } else {
            queryBuilder.append("name ");
        }

        queryBuilder.append("revisionDateUtc:lastModified ")
                    .append("versionDateUtc:versionDate ")
                    .append("versionNumber:versionId ");

        if (includeClasses == true) {
            queryBuilder.append(getPropertyClassQuery());
        }
        queryBuilder.append("} }");

        return queryBuilder.toString();
    }

    // classes
    public static String getPropertyClassQuery() {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("propertyClasses:assignedTo { ")
                    .append("nodes { ")
                    .append("relatingObject {")
                    .append("uri:id ")
                    .append("code:name ")
                    .append("name ")
                    .append("} } }");

        return queryBuilder.toString();
    }

    // =====================================================================================================================
    // ENDPOINT: /api/Property/Relations/v1


    // =====================================================================================================================
    // ENDPOINT: /api/Property/Classes/v1
    // classes
    public static String getPropertyClassesQuery(String id, int queryOffset, int queryLimit, String languageCode) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("{ findProperties(input:{pageSize: " + queryLimit + " idIn: \\\"" + id + "\\\"}) {" )
                    .append("nodes { ")
                    .append("assignedTo { ")
                    .append("nodes { ")
                    .append("relatingObject { ")
                    .append("uri:id ")
                    .append("code:name ")
                    .append("name ")
                    .append("} ")
                    .append("} ")
                    .append("} ")
                    .append("} ")
                    .append("} }");

        return queryBuilder.toString();
    }

    // =====================================================================================================================
    // ENDPOINT: /api/PropertyValue/v2

}
