package com.hotel_project.features.hotels.get_by_id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
    private String houseNumber;
    private String street;
    private String city;
    private String country;
    private String postCode;
}
