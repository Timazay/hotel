package by.timofey.hotel_project.features.common;

import by.timofey.hotel_project.infrastructure.entity.Address;
import by.timofey.hotel_project.infrastructure.entity.Contact;
import by.timofey.hotel_project.infrastructure.entity.Hotel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConvertToShortHotelResponse {
    public List<ShortHotelResponse> convert(List<Hotel> hotels){
        List<ShortHotelResponse> responses = new ArrayList<>();

        for (Hotel hotel : hotels) {
            Address address = hotel.getAddress();
            responses.add(new ShortHotelResponse(hotel.getId(),
                    hotel.getName(),
                    hotel.getDescription(),
                    address.toString(),
                    hotel.getContacts().stream().map(Contact::getPhone)
                            .collect(Collectors.joining(" "))));
        }
        return responses;
    }
}
