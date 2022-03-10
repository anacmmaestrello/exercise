package com.mettle.feature.service;

import com.mettle.feature.model.*;
import com.mettle.feature.repository.FeatureRepository;
import com.mettle.feature.repository.PermissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeatureService {

    private final FeatureRepository featureRepository;

    private final PermissionRepository permissionRepository;

    public FeatureService(FeatureRepository featureRepository, PermissionRepository permissionRepository) {
        this.featureRepository = featureRepository;
        this.permissionRepository = permissionRepository;
    }

    public Feature createFeature(Feature feature) {
        if (feature == null || feature.getName() == null || feature.getName().isEmpty()) {
            throw new RuntimeException("Feature name cannot be empty");
        }
        Feature exists = featureRepository.findByName(feature.getName());
        if (exists != null) {
            throw new RuntimeException("Feature name already exists");
        }
        feature.setEnabled(false);
        return featureRepository.save(feature);
    }

    public Permission createPermission(PermissionRequest request) {
        if (request == null || request.getFeatureName() == null || request.getFeatureName().isEmpty()) {
            throw new RuntimeException("Feature name cannot be empty");        }

        if (request.getUserId() == null || request.getUserId().isEmpty()) {
            throw new RuntimeException("Please provide the user id");
        }
        Feature feature = featureRepository.findByName(request.getFeatureName());
        if (feature == null) {
            throw new RuntimeException("Feature name not found");
        }
        return permissionRepository.save(new Permission(new PermissionId(feature.getId(), request.getUserId())));
    }

    public List<FeatureResponse> getEnabledFeatures(String userId) {
        return featureRepository.findAvailableFeaturesByUserId(userId);
    }

    public Feature enableFeatureForAll(String featureName) {
        Feature feature = validateFeature(featureName);
        feature.setEnabled(true);
        return featureRepository.save(feature);
    }

    public Feature disableFeature(String featureName) {
        Feature feature = validateFeature(featureName);
        feature.setEnabled(false);
        return featureRepository.save(feature);
    }

    private Feature validateFeature(String featureName) {
        if (featureName == null || featureName.isEmpty()) {
            throw new RuntimeException("Feature name cannot be empty");
        }
        Feature feature = featureRepository.findByName(featureName);
        if (feature == null) {
            throw new RuntimeException("Feature not found" + featureName);
        }
        return feature;
    }

    public List<Feature> getAllFeatures() {
        return featureRepository.findAll();
    }
}
