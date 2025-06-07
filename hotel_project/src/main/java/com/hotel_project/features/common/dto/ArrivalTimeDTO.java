package com.hotel_project.features.common.dto;

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
