package datacat.graphql;

// =====================================================================================================================
// C L A S S   S E C T I O N
// =====================================================================================================================
public class GraphQlLookupData {

    // =====================================================================================================================
    // SECTION: LOOKUP DATA
    // =====================================================================================================================
    // ENDPOINT: /api/Unit/v1
    // public static String getUnitsQuery() {
    //     StringBuilder queryBuilder = new StringBuilder();
    //     queryBuilder.append("{ findUnits(input: {pageSize:10}) { ")
    //                 .append("nodes { ")
    //                 .append("code:id ")
    //                 .append("name:displayName ")
    //                 .append("} ")
    //                 .append("} }");
    //     return queryBuilder.toString();
    // }


    // =====================================================================================================================
    // ENDPOINT: /api/ReferenceDocument/v1
    public static String getReferenceDocumentsQuery() {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("{ findDocuments(input:{pageSize:100}) { ")
                    .append("nodes { ")
                    .append("code:name ")
                    .append("date:versionDate ")
                    .append("} ")
                    .append("} }");
        return queryBuilder.toString();
    }

    
    // =====================================================================================================================
    // ENDPOINT: /api/Language/v1
    public static String getLanguagesQuery() {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("{ languages { ")
                    .append("nodes { ")
                    .append("isoCode:languageTag ")
                    .append("name:displayLanguage ")
                    .append("} ")
                    .append("} }");
        return queryBuilder.toString();
    }

    // =====================================================================================================================
    // ENDPOINT: /api/Country/v1
    public static String getCountriesQuery() {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("{ languages { ")
                    .append("nodes { ")
                    .append("code:id ")
                    .append("name:displayCountry ")
                    .append("} ")
                    .append("} }");
        return queryBuilder.toString();
    }

}
