package datacat.graphql;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
import org.slf4j.*;

// =====================================================================================================================
// C L A S S   S E C T I O N
// 
// =====================================================================================================================
public class GraphQLClass {

    private static final Logger logger = LoggerFactory.getLogger(GraphQLClass.class);

    // =====================================================================================================================
    // SECTION: CLASS
    // =====================================================================================================================
    // ENDPOINT: /api/Class/v1
    // class
    // features that are not implemented yet are commented out
    // lesser effort: 
    // - name with lang input
    // greater effort (needs further implementation in model class and deserialization):
    // - synonyms
    // - collectedBy
    // - classProperties

    public static String getClassDetailsQuery(String id, boolean includeProperties, String languageCode) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("{ getSubject(id: \\\"").append(id).append("\\\") { ")
                    .append("documentedBy { nodes { relatingDocument { dictionaryUri:id } } } ") // missing logic to transform id into uri
                    .append("activationDateUtc:created ")
                    .append("description ")
                    .append("documentedBy { nodes { relatingDocument { documentReference:name dictionaryUri:id } } } ");
        
        if (languageCode != null && !languageCode.isEmpty()) {
            queryBuilder.append("name(input:{languageTags:\\\"").append(languageCode).append("\\\"}) ");
        } else {
            queryBuilder.append("name ");
        }
        
        queryBuilder.append("revisionDateUtc:lastModified ")
                    .append("uid:id ")
                    .append("uri:id ")
                    .append("code:name ")
                    .append("versionDateUtc:versionDate ")
                    .append("versionNumber:versionId ")
                    .append("classType:recordType ")
                    // .append("synonyms:names { value } ") // somehow leads to error while deserialization
                    .append("assignedCollections { nodes { parentClassReference:relatedCollections { uri:id name } } } ");

        if (includeProperties == true) {
            queryBuilder.append(getClassPropertyQuery());
            logger.debug("Query includes properties");
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
                    .append("assignedMeasures { nodes { relatedMeasures { assignedUnits { nodes { allowedValues:relatedUnits { uri:id value:name description } } } } } } ")
                    // .append("documentedBy { nodes { relatingDocument { propertyDictionaryName:name propertyDictionaryUri:id } } } ")
                    .append("composedBy { nodes { propertySet:name } } ")
                    .append("assignedMeasures { nodes { relatedMeasures { assignedUnits { nodes { units:relatedUnits { name } } } } } } ")
                    .append("}");

        return queryBuilder.toString();
    }

    // =====================================================================================================================
    // ENDPOINT: /api/Class/Relations/v1


    // =====================================================================================================================
    // ENDPOINT: /api/Class/Properties/v1
    public static String getClassPropertiesQuery(String id, int queryOffset, int queryLimit, String languageCode) {
        StringBuilder queryBuilder = new StringBuilder();
        // queryBuilder.append("{ findSubjects(input:{pageSize: \\\"").append(queryLimit).append("\\\" idIn: \\\"").append(id).append("\\\"} ) { ")
        queryBuilder.append("{ findSubjects(input:{pageSize: ").append(queryLimit).append(" idIn: \\\"").append(id).append("\\\"} ) { ")
                    .append("pageInfo { count:pageElements } ")
                    .append("totalCount:totalElements ")

                    .append("nodes { ")
                    .append("classUri:id ")

                    .append("classProperties:properties { ")
                    .append("name ")
                    .append("description ")
                    .append("uid:id ")
                    .append("uri:id ")
                    .append("propertyCode:name ")
                    .append("assignedMeasures { nodes { relatedMeasures { assignedUnits { nodes { allowedValues:relatedUnits { uri:id value:name description } } } } } } ")
                    // .append("documentedBy { nodes { relatingDocument { propertyDictionaryName:name propertyDictionaryUri:id } } } ")
                    .append("composedBy { nodes { propertySet:name } } ")
                    .append("assignedMeasures { nodes { relatedMeasures { assignedUnits { nodes { units:relatedUnits { name } } } } } } ")
                    .append("}")

                    .append("}")

                    .append("} }");

        return queryBuilder.toString();
    }
}