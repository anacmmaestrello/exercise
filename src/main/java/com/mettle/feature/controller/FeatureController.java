package com.mettle.feature.controller;

import com.mettle.feature.model.Feature;
import com.mettle.feature.model.FeatureResponse;
import com.mettle.feature.model.Permission;
import com.mettle.feature.model.PermissionRequest;
import com.mettle.feature.service.FeatureService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feature")
public class FeatureController {

    private final FeatureService service;

    public FeatureController(FeatureService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Feature> save (@RequestBody final Feature feature) {
        return new ResponseEntity<>(service.createFeature(feature), HttpStatus.CREATED);
    }

    @PostMapping("/enable")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Feature> enableFeatureForAll (String featureName) {
        return new ResponseEntity<>(service.enableFeatureForAll(featureName), HttpStatus.CREATED);
    }

    @PostMapping("/disable")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Feature> disableFeature (String featureName) {
        return new ResponseEntity<>(service.disableFeature(featureName), HttpStatus.CREATED);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    private ResponseEntity<List<Feature>> getAllFeatures() {
        return new ResponseEntity<>(service.getAllFeatures(), HttpStatus.OK);
    }

    @PostMapping("/permission")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Permission> enableFeature (@RequestBody final PermissionRequest request) {
        return new ResponseEntity<>(service.createPermission(request), HttpStatus.CREATED);
    }

    @GetMapping("/permission/{userId}")
    @ResponseStatus(HttpStatus.OK)
    private ResponseEntity<List<FeatureResponse>> getEnabledFeatures(@PathVariable String userId) {
        return new ResponseEntity<>(service.getEnabledFeatures(userId), HttpStatus.OK);
    }

}
