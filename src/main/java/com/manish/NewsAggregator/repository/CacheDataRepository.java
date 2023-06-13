package com.manish.NewsAggregator.repository;

import com.manish.NewsAggregator.model.QueryData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CacheDataRepository extends JpaRepository<QueryData, Long> {
    @Query(value = "Select * from Query_Data where Query_Data.QUERY_NAME = :query limit :limit_num offset :offset_num", nativeQuery = true)
    QueryData getResultsWithQuery(
            @Param("query") String query,
            @Param("offset_num") int offset,
            @Param("limit_num") int limit
    );

    @Query(value = "Select * from Query_Data where Query_Data.QUERY_NAME = :query limit 1", nativeQuery = true)
    QueryData getQueryByValue(@Param("query") String query);
}
