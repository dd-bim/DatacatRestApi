package datacat.graphql;

// =====================================================================================================================
// C L A S S   S E C T I O N
// this class holds different datacat specific GraphQL implementations like the authentication and statistics
// =====================================================================================================================
public class GraphQlDatacatSpecifics {

    // =====================================================================================================================
    // Authentication
    // 
    
    // =====================================================================================================================
    // Statistics // DONE
    public static String getStatisticsQuery() {

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("{ statistics { ")
                    .append("catalogueItems:items { ")
                    .append("itemId:id ")
                    .append("itemCount:count ")
                    .append("} ")
                    .append("} }");

        return queryBuilder.toString();
    }


    // =====================================================================================================================
    // ....space for more.....

}