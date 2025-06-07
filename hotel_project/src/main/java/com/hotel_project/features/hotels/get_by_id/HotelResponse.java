package com.hotel_project.features.hotels.get_by_id;

import com.hotel_project.features.common.dto.AddressDTO;
import com.hotel_project.features.common.dto.ArrivalTimeDTO;
import com.hotel_project.features.common.dto.ContactDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelResponse {
    private long id;
    private String name;
    private String brand;
    private AddressDTO address;
    private ContactDTO contact;
    private ArrivalTimeDTO arrivalTime;
    private List<String> amenities;
}
