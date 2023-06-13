package com.manish.NewsAggregator.repository;

import com.manish.NewsAggregator.model.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface CacheDataRepository extends JpaRepository<Query, Long> {
    @org.springframework.data.jpa.repository.Query(value = "Select * from Query where Query.QUERY_NAME = :query limit :limit_num offset :offset_num", nativeQuery = true)
    Query getResultsWithQuery(
            @Param("query") String query,
            @Param("offset_num") int offset,
            @Param("limit_num") int limit
    );

    @org.springframework.data.jpa.repository.Query(value = "Select * from Query where Query.QUERY_NAME = :query limit 1", nativeQuery = true)
    Query getQueryByValue(@Param("query") String query);
}
