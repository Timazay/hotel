package com.hotel_project.features.histograms.get;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupByParamDto {
    private String key;
    private Long count;
}

