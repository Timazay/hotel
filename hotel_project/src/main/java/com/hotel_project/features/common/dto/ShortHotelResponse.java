package com.hotel_project.features.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortHotelResponse {
    private long id;
    private String name;
    private String description;
    private String address;
    private String phone;
}
