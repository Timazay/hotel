package com.hotel_project.features.hotels.create;

import com.hotel_project.features.hotels.get_by_id.AddressDTO;
import com.hotel_project.features.hotels.get_by_id.ArrivalTimeDTO;
import com.hotel_project.features.hotels.get_by_id.ContactDTO;
import lombok.Data;

@Data
public class CreateHotelRequest {
    private String name;
    private String description;
    private String brand;
    private AddressDTO address;
    private ContactDTO contact;
    private ArrivalTimeDTO arrivalTime;
}
