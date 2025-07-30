package datacat.graphql;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
import lombok.extern.slf4j.Slf4j;

// =====================================================================================================================
// C L A S S   Q U E R I E S
// =====================================================================================================================
@Slf4j
public class GraphQLClass {

    // =====================================================================================================================
    // SECTION: CLASS
    // =====================================================================================================================
    // ENDPOINT: /api/Class/v1

    public static String getClassDetailsQuery(String id, boolean includeProperties, String languageCode) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("{ getSubject(id: \\\"" + id + "\\\") { ")
                .append("dictionary { dictionaryUri:id } ")
                .append("activationDateUtc:created ")
                .append("description ")
                .append("referenceDocuments { documentReference:name } ")
                .append("con:countryOfOrigin { countryOfOrigin:code }")
                .append("def:definition { texts { definition:text } } ")
                .append("dep:deprecationExplanation { texts { deprecationExplanation:text } } ");

        if (languageCode != null && !languageCode.isEmpty()) {
            queryBuilder.append("name(input:{languageTags:\\\"" + languageCode + "\\\"}) ");
        } else {
            queryBuilder.append("name ");
        }

        queryBuilder.append("revisionDateUtc:lastModified ")
                .append("uid:id ")
                .append("uri:id ")
                .append("code:name ")
                .append("versionDateUtc:lastModified ")
                .append("majorVersion ")
                .append("minorVersion ")
                .append("classType:recordType ");

        if (includeProperties == true) {
            queryBuilder.append(getClassPropertyQuery());
            log.debug("Query includes properties");
        } else {
            log.debug("Query does not include properties");
        }
        queryBuilder.append("} }");

        return queryBuilder.toString();
    }

    // properties
    public static String getClassPropertyQuery() {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("classProperties:properties { ")
                .append("name ")
                .append("description ")
                .append("uid:id ")
                .append("uri:id ")
                .append("propertyCode:name ")
                .append("allowedValues:possibleValues { values { sortNumber:order orderedValue { uri:id value:name code:name } } } ")
                .append("units { name } ")
                .append("def:definition { texts { definition:text } } ")
                .append("dataType ")
                .append("examples { texts { example:text } } ")
                .append("dictionary { propertyDictionaryUri:id } ")
                .append("}");

        return queryBuilder.toString();
    }

    // =====================================================================================================================
    // ENDPOINT: /api/Class/Relations/v1

    // =====================================================================================================================
    // ENDPOINT: /api/Class/Properties/v1
    public static String getClassPropertiesQuery(String id, int pageSize, String languageCode) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("{ findSubjects(input:{pageSize: " + pageSize + " idIn: \\\"" + id + "\\\"} ) { ")
                .append("pageInfo { count:pageElements } ")
                .append("totalCount:totalElements ")
                .append("count:totalElements ")
                .append("nodes { ")
                .append("classProperties:properties { ")
                .append("name ")
                .append("description ")
                .append("uid:id ")
                .append("uri:id ")
                .append("propertyCode:name ")
                .append("allowedValues:possibleValues { values { sortNumber:order orderedValue { uri:id value:name code:name } } } ")
                .append("units { name } ")
                .append("def:definition { texts { definition:text } } ")
                .append("dataType ")
                .append("examples { texts { example:text } } ")
                .append("dictionary { propertyDictionaryUri:id } ")
                .append("}")
                .append("classUri:id ")
                .append("}")
                .append("} }");

        return queryBuilder.toString();
    }

}