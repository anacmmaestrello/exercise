package com.mettle.feature.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeatureResponse {

    private Long featureId;
    private String featureName;
    private Boolean global;

}
