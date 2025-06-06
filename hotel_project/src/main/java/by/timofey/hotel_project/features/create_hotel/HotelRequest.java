package by.timofey.hotel_project.features.create_hotel;

import by.timofey.hotel_project.infrastructure.entity.Address;
import by.timofey.hotel_project.infrastructure.entity.ArrivalTime;
import by.timofey.hotel_project.infrastructure.entity.Contact;
import lombok.Data;

@Data
public class HotelRequest {
    private String name;
    private String description;
    private String brand;
    private Address address;
    private Contact contact;
    private ArrivalTime arrivalTime;
}
