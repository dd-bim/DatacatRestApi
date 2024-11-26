package datacat.graphql;

// =====================================================================================================================
// C L A S S   S E C T I O N
// 
// =====================================================================================================================
public class GraphQlDictionary {

    // =====================================================================================================================
    // ENDPOINT: /api/Dictionary/v1
    // OPTION 1: query to fetch all dictionaries
    public static String getDictionaryByIdQuery(String id, int limit) {
        final String dictTag = "6f96aaa7-e08f-49bb-ac63-93061d4c5db2"; // 'dictTag' is a constant internal value pointing only on models in XtdBag
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("{ findBags(input:{pageSize:1 tagged:\\\"" + dictTag + "\\\" idIn: \\\"" + id + "\\\"}) { ")
                    .append("nodes { ")
                    .append("uri:id ")
                    .append("name ")
                    .append("code:name ")
                    .append("version:versionId ")
                    .append("releaseDate:created ")
                    .append("lastUpdatedUtc:versionDate ")
                    .append("} ")
                    .append("totalCount:totalElements ")
                    .append("} }");
        return queryBuilder.toString();
    }

    // OPTION 2: query to fetch all dictionaries
    public static String getAllDictionariesQuery(int limit) {
        final String dictTag = "6f96aaa7-e08f-49bb-ac63-93061d4c5db2"; // 'dictTag' is a constant internal value pointing only on models in XtdBag
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("{ findBags(input:{pageSize:100 tagged:\\\"" + dictTag + "\\\"}) { ")
                    .append("nodes { ")
                    .append("uri:id ")
                    .append("name ")
                    .append("code:name ")
                    .append("version:versionId ")
                    .append("releaseDate:created ")
                    .append("lastUpdatedUtc:versionDate ")
                    .append("} ")
                    .append("totalCount:totalElements ")
                    .append("} }");
        return queryBuilder.toString();
    }

    // =====================================================================================================================
    // ENDPOINT: /api/Dictionary/v1/Properties

    

    // =====================================================================================================================
    // ENDPOINT: /api/Dictionary/v1/Classes
    // STEP 1: first query to fetch the classes related to the requested dictionary
    public static String getDictionaryGroupQuery(String id, String languageCode) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("{ getBag(id:\\\"" + id + "\\\") { ")
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
        queryBuilder.append("{ getBag(id:\\\"" + groupId + "\\\") { ")
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


}