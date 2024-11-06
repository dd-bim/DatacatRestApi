package datacat.graphql;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================


// =====================================================================================================================
// C L A S S   S E C T I O N
// 
// =====================================================================================================================
public class GraphQLDictionary {

    // =====================================================================================================================
    // ENDPOINT: /api/Dictionary/v1
    // OPTION 1: query to fetch all dictionaries
    public static String getDictionary(String id, String bearerToken) {
        final String dictTag = "6f96aaa7-e08f-49bb-ac63-93061d4c5db2"; // 'dictTag' is a constant internal value pointing only on models in XtdBag
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("{ findBags(input:{pageSize:1 tagged:\\\"" + dictTag + "\\\" idIn: \\\"" + id + "\\\"}) { ")
                    .append("dictionaries:nodes { ")
                    .append("uri:id ")
                    .append("name ")
                    .append("version:versionId ")
                    .append("}")
                    .append("dictionariesTotalCount:totalElements ")
                    .append("} }");
        return queryBuilder.toString();
    }

    // OPTION 2: query to fetch all dictionaries
    public static String getAllDictionaries(String bearerToken) {
        final String dictTag = "6f96aaa7-e08f-49bb-ac63-93061d4c5db2"; // 'dictTag' is a constant internal value pointing only on models in XtdBag
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("{ findBags(input:{pageSize:10000 tagged: \\\"" + dictTag + "\\\"}) { ")
                    .append("dictionaries:nodes { ")
                    .append("uri:id ")
                    .append("name ")
                    .append("version:versionId ")
                    .append("}")
                    .append("dictionariesTotalCount:totalElements ")
                    .append("} }");
        return queryBuilder.toString();
    }

    // =====================================================================================================================
    // ENDPOINT: /api/Dictionary/v1/Classes
    // STEP 1: first query to fetch the classes related to the requested dictionary
    public static String getDictionaryGroupQuery(String id, String languageCode) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("{getBag(id:\\\"" + id + "\\\") { ")
                    .append("name ")
                    .append("uri:id ")
                    .append("code:name ")
                    .append("version:versionId ")
                    .append("releaseDate:created ")
                    .append("lastUpdatedUtc:versionDate ")
                    .append("collects { nodes { relatedThings { internalGroupId:id } } } ")
                    .append("} } ");
        return queryBuilder.toString();
    }
    // // STEP 2: queries fetch classes related to certain groups
    public static String getGroupClassesQuery(String groupId, int limit, String languageCode) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("{getBag(id:\\\"" + groupId + "\\\") { ")
                    // .append("collects { nodes { relatedThings { uri:id name descriptionPart:description } } }")
                    // .append("collects { nodes { relatedThings { name } } }")
                    .append("collects { ")
                    .append("nodes { ")
                    .append("relatedThings { ")
                    .append("name ")
                    .append("code:name ")
                    .append("uri:id ")
                    .append("descriptionPart:description ")
                    .append("collectedBy { nodes { parentClassCode:name } } ")
                    .append("} } } } }");
                    // .append("} }");
        return queryBuilder.toString();
    }

    // STEP 1: first query to fetch the groups related to the requested dictionary
    // public static String getDictionaryGroupQuery(String id, String languageCode) {
    //     final String dictTag = "6f96aaa7-e08f-49bb-ac63-93061d4c5db2"; // 'dictTag' is a constant internal value pointing only on models in xtdBag
    //     StringBuilder queryBuilder = new StringBuilder();
    //     queryBuilder.append("{ findBags(input:{pageSize:10000 tagged: \\\"" + dictTag + "\\\" idIn: \\\"" + id + "\\\"}) { ")
    //                 .append("dictionary:nodes { ")
    //                 .append("name ")
    //                 .append("version:versionId ")
    //                 // .append("collects { nodes { groups:relatedThings { internalGroupId:id groupName:name } } } ")
    //                 .append("} } }");
    //     return queryBuilder.toString();
    // }

    // STEP 2: queries fetch classes related to certain groups
    // public static String getGroupClassesQuery(String groupId, int limit, String languageCode) {
    //     final String groupTag = "5997da9b-a716-45ae-84a9-e2a7d186bcf9"; // 'groupTag' is a constant internal value pointing only on groups in xtdBag
    //     StringBuilder queryBuilder = new StringBuilder();
    //     queryBuilder.append("{ findBags(input: {pageSize:\\\"" + limit + "\\\" tagged:\\\"" + groupTag + "\\\" idIn: \\\"" + groupId + "\\\"}) { ")
    //                 .append("nodes { ")
    //                 .append("collects { ")
    //                 .append("pageInfo{ classesCount:pageElements } ")
    //                 .append("classes:nodes { uri:id name descriptionPart:description } ")
    //                 .append("} } } }");
    //     return queryBuilder.toString();
    // }

    // =====================================================================================================================
    // ENDPOINT: /api/Dictionary/v1/Properties
}