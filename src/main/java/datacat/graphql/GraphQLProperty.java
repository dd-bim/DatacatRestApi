package datacat.graphql;

// =====================================================================================================================
// C L A S S   S E C T I O N
// 
// =====================================================================================================================
public class GraphQLProperty {

    // =====================================================================================================================
    // SECTION: PROPERTY
    // =====================================================================================================================
    // ENDPOINT: /api/Property/v5
    public static String getPropertyDetailsQuery(String id, String languageCode) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("{ getProperty(id: \\\"" + id + "\\\") { ")
                .append("dictionary { dictionaryUri:id } ")
                .append("activationDateUtc:created ")
                .append("description ")
                .append("referenceDocuments { documentReference:name } ")
                .append("con:countryOfOrigin { countryOfOrigin:code }")
                .append("def:definition { texts { definition:text } } ")
                .append("dep:deprecationExplanation { texts { deprecationExplanation:text } } ")
                .append("status ")
                .append("revisionDateUtc:lastModified ")
                .append("versionDateUtc:lastModified ")
                .append("uid:id ")
                .append("uri:id ")
                .append("majorVersion ")
                .append("minorVersion ")
                .append("allowedValues:possibleValues { values { nodes {sortNumber:order orderedValue { uri:id value:name code:name } } } } ")
                .append("units { name } ")
                .append("dataType ")
                .append("examples { texts { example:text } } ")
                .append("code:name ");

        if (languageCode != null && !languageCode.isEmpty()) {
            queryBuilder.append("name(input:{languageTags:\\\"" + languageCode + "\\\"}) ");
        } else {
            queryBuilder.append("name ");
        }

        queryBuilder.append("} }");

        return queryBuilder.toString();
    }

    // =====================================================================================================================
    // ENDPOINT: /api/Property/Relations/v1

    // =====================================================================================================================
    // ENDPOINT: /api/Property/Classes/v1
    // classes
    public static String getPropertyClassesQuery(String id, int queryOffset, int queryLimit, String languageCode) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("{ findProperties(input:{pageSize: " + queryLimit + " idIn: \\\"" + id + "\\\"}) {")
                .append("totalCount:totalElements ")
                .append("count:totalElements ")
                .append("nodes { ")
                .append("propertyUri:id ")
                .append("propertyClasses:subjects { ")
                .append("uri:id ")
                .append("name ")
                .append("description ")
                .append("dictionary { dictionaryUri:id } ")
                .append("} ")
                .append("} } }");

        return queryBuilder.toString();
    }

    // =====================================================================================================================
    // ENDPOINT: /api/PropertyValue/v2

}
