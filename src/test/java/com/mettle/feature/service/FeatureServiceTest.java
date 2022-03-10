package com.mettle.feature.service;

import com.mettle.feature.model.Feature;
import com.mettle.feature.model.PermissionRequest;
import com.mettle.feature.repository.FeatureRepository;
import com.mettle.feature.repository.PermissionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FeatureServiceTest {

    @InjectMocks
    private FeatureService service;

    @Mock
    private PermissionRepository permissionRepository;

    @Mock
    private FeatureRepository featureRepository;

    @BeforeEach
    void init_mocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createFeature() {
        when(featureRepository.findByName(eq("test"))).thenReturn(null);
        ArgumentCaptor<Feature> argument = ArgumentCaptor.forClass(Feature.class);
        service.createFeature(new Feature(0L, "test", true));
        verify(featureRepository).save(argument.capture());
        assertNotNull(argument.getValue());
        assertEquals("test", argument.getValue().getName());
        assertEquals(false, argument.getValue().getEnabled());
    }

    @Test
    public void createExistingFeature() {
        when(featureRepository.findByName(eq("exists"))).thenReturn(new Feature());
        assertThrows(RuntimeException.class, () -> service.createFeature(new Feature(0L, "exists", true)));
    }

    @Test
    public void createNullFeature() {
        assertThrows(RuntimeException.class, () -> service.createFeature((Feature) null));
    }

    @Test
    public void createEmptyFeature() {
        assertThrows(RuntimeException.class, () -> service.createFeature(new Feature()));
    }

    @Test
    public void createPermission() {
        assertThrows(RuntimeException.class, () -> service.createPermission((PermissionRequest) null));
    }

    @Test
    public void createEmptyPermission() {
        assertThrows(RuntimeException.class, () -> service.createPermission(new PermissionRequest()));
    }

    @Test
    public void createEmptyFeaturePermission() {
        assertThrows(RuntimeException.class, () -> service.createPermission(new PermissionRequest("feature", "")));
    }



}
