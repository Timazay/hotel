package com.hotel_project.features.hotels.get_by_id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArrivalTimeDTO {
    private String checkIn;
    private String checkOut;
}
