package datacat.graphql;

// =====================================================================================================================
// C L A S S   S E C T I O N
// 
// =====================================================================================================================
public class GraphQLDictionary {

    // =====================================================================================================================
    // ENDPOINT: /api/Dictionary/v1
    // OPTION 1: query to fetch all dictionaries
    public static String getDictionaryByIdQuery(String id, int limit) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("{ findDictionaries(input:{pageSize:1 idIn: \\\"" + id + "\\\"}) { ")
                    .append("nodes { ")
                    .append("uri:id ")
                    .append("name {texts { text language {code englishName } }} ")  // Behalten verschachtelte Struktur für Sprachen
                    .append("releaseDate:created ")
                    .append("lastUpdatedUtc:lastModified ")
                    .append("} ")
                    .append("totalCount:totalElements ")
                    .append("count:totalElements ")
                    .append("} }");
        return queryBuilder.toString();
    }

    // OPTION 2: query to fetch all dictionaries
    public static String getAllDictionariesQuery(int limit) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("{ findDictionaries(input:{pageSize:100 }) { ")
                    .append("nodes { ")
                    .append("uri:id ")
                    .append("name {texts { text language {code englishName } }} ")  // Behalten verschachtelte Struktur für Sprachen  
                    .append("releaseDate:created ")
                    .append("lastUpdatedUtc:lastModified ")
                    .append("} ")
                    .append("totalCount:totalElements ")
                    .append("count:totalElements ")
                    .append("} }");
        return queryBuilder.toString();
    }

    // =====================================================================================================================
    // ENDPOINT: /api/Dictionary/v1/Properties

    

    // =====================================================================================================================
    // ENDPOINT: /api/Dictionary/v1/Classes
    public static String getDictionaryGroupQuery(String id, String languageCode) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("{ getDictionary(id:\\\"" + id + "\\\") { ")
                    .append("name {texts { text language {code englishName } }} ")
                    .append("uri:id ")
                    .append("releaseDate:created ")
                    .append("lastUpdatedUtc:lastModified ")
                    .append("classes:concepts (pageSize:100000) { nodes {... on XtdSubject {")
                    .append("uri:id ")
                    .append("name ")
                    .append("code:name ")
                    .append("classType:recordType ")
                    .append("descriptionPart:description ")
                    .append("} } }")
                    .append("} } ");
        return queryBuilder.toString();
    }
}