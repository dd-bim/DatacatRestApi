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
    // api/Class/v1
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
                    .append("documentedBy { nodes { relatingDocument { documentReference:name } } } ");
        
        if (languageCode != null && !languageCode.isEmpty()) {
            queryBuilder.append("name(input:{languageTags:\\\"").append(languageCode).append("\\\"}) ");
        } else {
            queryBuilder.append("name ");
        }
        
        queryBuilder.append("revisionDateUtc:lastModified ")
                    .append("uid:id ")
                    .append("versionDateUtc:versionDate ")
                    .append("versionNumber:versionId ")
                    .append("classType:recordType ")
                    // .append(s"synonyms:names { value } ") // wrong approach
                    .append("assignedCollections { nodes { parentClassReference:relatedCollections { uri:id name } } } ");

        if (includeProperties == true) {
            queryBuilder.append(getClassPropertiesQuery());
            logger.debug("Query includes properties");
        }
        queryBuilder.append("} }");
        return queryBuilder.toString();
    }

    // properties
    public static String getClassPropertiesQuery() {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("classProperties:properties { ")
                    .append("name ")
                    .append("description ")
                    .append("uid:id ")
                    .append("assignedMeasures { nodes { relatedMeasures { assignedValues { nodes { allowedValues:relatedValues { uri:id value:name description } } } } } } ")
                    // .append("documentedBy { nodes { relatingDocument { propertyDictionaryName:name propertyDictionaryUri:id } } } ")
                    .append("composedBy { nodes { propertySet:name } } ")
                    .append("assignedMeasures { nodes { relatedMeasures { assignedUnits { nodes { units:relatedUnits { name } } } } } } ")
                    .append("}");
        return queryBuilder.toString();
    }
}