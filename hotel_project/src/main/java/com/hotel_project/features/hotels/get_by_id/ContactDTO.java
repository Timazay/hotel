package com.hotel_project.features.hotels.get_by_id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactDTO {
    private String phone;
    private String email;
}
