package by.timofey.hotel_project.features.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ShortHotelResponse {
    private long id;
    private String name;
    private String description;
    private String address;
    private String phone;
}
