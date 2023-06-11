package com.manish.NewsAggregator.repository;

import com.manish.NewsAggregator.model.Results;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CacheDataRepository extends JpaRepository<Results, String> {
    @Query(value = "Select * from Results where Results.QUERY = :query limit :limit_num offset :offset_num", nativeQuery = true)
    Results getResultsWithQuery(@Param("query") String query, @Param("offset_num") int offset, @Param("limit_num") int limit);

}
