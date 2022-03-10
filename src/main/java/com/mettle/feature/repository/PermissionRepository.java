package com.mettle.feature.repository;

import com.mettle.feature.model.FeatureResponse;
import com.mettle.feature.model.Permission;
import com.mettle.feature.model.PermissionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, PermissionId> {

}
