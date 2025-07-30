package datacat.services;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
import org.springframework.stereotype.Service;

// Logging
import lombok.extern.slf4j.Slf4j;

// Internal
import datacat.models.StatisticsResponseContractV1;
import datacat.graphql.GraphQLDatacatSpecifics;

// =====================================================================================================================
// D A T A C A T   S P E C I F I C S   S E R V I C E   S E C T I O N
// Service for Datacat-specific endpoints
// =====================================================================================================================
@Service
@Slf4j
public class DatacatSpecificsService {

    private final BaseApiService baseApiService;

    public DatacatSpecificsService(BaseApiService baseApiService) {
        this.baseApiService = baseApiService;
    }

    // =====================================================================================================================
    // DATACAT SPECIFICS ENDPOINTS
    // =====================================================================================================================
    
    // ENDPOINT: /api/Statistics
    public StatisticsResponseContractV1 getStatistics() {

        String query = GraphQLDatacatSpecifics.getStatisticsQuery();
        String response = baseApiService.executeQuery(query, null);
        String rootField = "statistics";

        StatisticsResponseContractV1 statistics = baseApiService.deserializeGetResponse(response, rootField,
                StatisticsResponseContractV1.class);
        log.debug("Deserialized Statistics Response: {}", statistics);

        return statistics;
    }
}
