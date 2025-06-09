package com.hotel_project.features.amenities.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class CreateAmenitiesRequest {
    private long hotelId;
    private List<String> amenities;
}
