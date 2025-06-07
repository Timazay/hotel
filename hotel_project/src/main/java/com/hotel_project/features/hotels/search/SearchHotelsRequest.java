package com.hotel_project.features.hotels.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class SearchHotelsRequest {
    private String name;
    private String brand;
    private String country;
    private String city;
    private List<String> amenities;
}
