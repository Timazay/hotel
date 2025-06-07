package com.hotel_project.features.common;

import com.hotel_project.domain.Address;
import com.hotel_project.domain.Hotel;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class ConvertToShortHotelResponse {
    public List<ShortHotelResponse> convert(List<Hotel> hotels){
        List<ShortHotelResponse> responses = new ArrayList<>();

        for (Hotel hotel : hotels) {
            Address address = hotel.getAddress();
            responses.add(new ShortHotelResponse(hotel.getId(),
                    hotel.getName(),
                    hotel.getDescription(),
                    address.getHouseNumber() + " " + address.getStreet() + ", " + address.getCity()
                    + ", " + address.getPostCode() + ", " + address.getCountry(),
                    hotel.getContact().getPhone()));
        }
        return responses;
    }
}
