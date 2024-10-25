package datacat.graphql;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================


// =====================================================================================================================
// C L A S S   S E C T I O N
// this class holds different datacat specific GraphQL implementations like the authentication and statistics
// =====================================================================================================================
public class GraphQLDatacatSpecifics {

    // =====================================================================================================================
    // Authentication
    
    
    // =====================================================================================================================
    // Statistics
    public static String getStatisticsQuery() {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("{ statistics { catalogueItem:items { id count } } }");
        return queryBuilder.toString();

        // old approach without StringBuilder - functional
        // return "{ statistics { catalogueItem:items { id count } } }";
    }


    // =====================================================================================================================
    // ....space for more.....

}
