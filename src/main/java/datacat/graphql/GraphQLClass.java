package datacat.graphql;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
import org.slf4j.*;

// =====================================================================================================================
// C L A S S   Q U E R I E S
// =====================================================================================================================
public class GraphQLClass {

    private static final Logger logger = LoggerFactory.getLogger(GraphQLClass.class);

    // =====================================================================================================================
    // SECTION: CLASS
    // =====================================================================================================================
    // ENDPOINT: /api/Class/v1

    public static String getClassDetailsQuery(String id, boolean includeProperties, String languageCode) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("{ getSubject(id: \\\"" + id + "\\\") { ")
                    .append("collectedBy { nodes { relatingCollection { collectedBy { nodes { relatingCollection { dictionaryUri:id}}}}}}")
                    .append("activationDateUtc:created ")
                    .append("description ")
                    .append("documentedBy { nodes { relatingDocument { documentReference:name } } } ");

            
        if (languageCode != null && !languageCode.isEmpty()) {
            queryBuilder.append("name(input:{languageTags:\\\"" + languageCode + "\\\"}) ");
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
                    .append("assignedCollections { nodes { parentClassReference:relatedCollections { uri:id name } } } ");

        if (includeProperties == true) {
            queryBuilder.append(getClassPropertyQuery());
            logger.debug("Query includes properties");
        } else {
            logger.debug("Query does not include properties");
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
                    .append("composedBy { nodes { propertySet:name } } ")
                    .append("assignedMeasures { nodes { relatedMeasures { assignedUnits { nodes { units:relatedUnits { name } } } } } } ")
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
                    .append("nodes { ")
                    .append("classProperties:properties { ")
                    .append("name ")
                    .append("description ")
                    .append("uid:id ")
                    .append("uri:id ")
                    .append("propertyCode:name ")
                    .append("}")
                    .append("classUri:id ")
                    .append("}")
                    .append("} }");

        return queryBuilder.toString();
    }

    // worked properly so far without pageSize
    // public static String getClassPropertiesQuery(String id, int queryOffset, int queryLimit, String languageCode) {
    //     StringBuilder queryBuilder = new StringBuilder();
    //     queryBuilder.append("{ findSubjects(input:{pageSize: " + queryLimit + " idIn: \\\"" + id + "\\\"} ) { ")
    //                 .append("pageInfo { count:pageElements } ")
    //                 .append("totalCount:totalElements ")
    //                 .append("nodes { ")
    //                 .append("classProperties:properties { ")
    //                 .append("name ")
    //                 .append("description ")
    //                 .append("uid:id ")
    //                 .append("uri:id ")
    //                 .append("propertyCode:name ")
    //                 .append("}")
    //                 .append("classUri:id ")
    //                 .append("}")
    //                 .append("} }");

    //     return queryBuilder.toString();
    // }



}