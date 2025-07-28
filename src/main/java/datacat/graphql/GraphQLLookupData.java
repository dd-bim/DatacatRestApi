package datacat.graphql;

// =====================================================================================================================
// C L A S S   S E C T I O N
// =====================================================================================================================
public class GraphQLLookupData {

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
        queryBuilder.append("{ findExternalDocuments(input:{pageSize:100}) { ")
                    .append("nodes { ")
                    .append("title:name ")
                    .append("name ")
                    .append("date:dateOfPublication ")
                    .append("} ")
                    .append("} }");
        return queryBuilder.toString();
    }

    
    // =====================================================================================================================
    // ENDPOINT: /api/Language/v1
    public static String getLanguagesQuery() {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("{ findLanguages { ")
                    .append("nodes { ")
                    .append("isoCode:code ")
                    .append("name:englishName ")
                    .append("} ")
                    .append("} }");
        return queryBuilder.toString();
    }

    // =====================================================================================================================
    // ENDPOINT: /api/Country/v1
    public static String getCountriesQuery() {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("{ findCountries { ")
                    .append("nodes { ")
                    .append("code ")
                    .append("name")
                    .append("} ")
                    .append("} }");
        return queryBuilder.toString();
    }

}
