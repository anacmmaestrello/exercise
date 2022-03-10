package com.mettle.feature.repository;

import com.mettle.feature.model.Feature;
import com.mettle.feature.model.FeatureResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long> {

    @Query(
            value = "SELECT * FROM feature WHERE upper(name) = upper(:name)",
            nativeQuery = true
    )
    Feature findByName(String name);

    @Query("select new com.mettle.feature.model.FeatureResponse(a.id, a.name, a.enabled) " +
            "from feature as a left join permission as b on a.id = b.permisionId.featureId " +
            "where a.enabled = true or b.permisionId.userId = :userId" )
    List<FeatureResponse> findAvailableFeaturesByUserId(String userId);
}
