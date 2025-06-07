package com.hotel_project.features.hotels.create;

import com.hotel_project.features.common.dto.AddressDTO;
import com.hotel_project.features.common.dto.ArrivalTimeDTO;
import com.hotel_project.features.common.dto.ContactDTO;
import lombok.Data;

@Data
public class HotelRequest {
    private String name;
    private String description;
    private String brand;
    private AddressDTO address;
    private ContactDTO contact;
    private ArrivalTimeDTO arrivalTime;
}
