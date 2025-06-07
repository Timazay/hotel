package com.hotel_project.features.hotels.get_by_id;

import com.hotel_project.common.exceptions.NotFoundException;
import com.hotel_project.domain.Amenity;
import com.hotel_project.domain.Hotel;
import com.hotel_project.features.common.dto.AddressDTO;
import com.hotel_project.features.common.dto.ArrivalTimeDTO;
import com.hotel_project.features.common.dto.ContactDTO;
import com.hotel_project.infrastructure.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class HotelFinderByIdHandler {
    private final HotelRepository hotelRepository;

    @Autowired
    public HotelFinderByIdHandler(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public HotelResponse execute(long id) throws NotFoundException {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() ->  new NotFoundException("There is no such hotel",
                        Map.of("id", String.valueOf(id))));
        ContactDTO contactDTO = new ContactDTO(hotel.getContact().getPhone(), hotel.getContact().getEmail());

        ArrivalTimeDTO arrivalTimeDTO = new ArrivalTimeDTO(hotel.getArrivalTime().getCheckIn(),
                hotel.getArrivalTime().getCheckOut());

        AddressDTO addressDTO = new AddressDTO(
                hotel.getAddress().getHouseNumber(),
                hotel.getAddress().getStreet(),
                hotel.getAddress().getCity(),
                hotel.getAddress().getCountry(),
                hotel.getAddress().getPostCode());
        List<String> amenities = new ArrayList<>();
        for (Amenity amenity : hotel.getAmenities()) {
            amenities.add(amenity.getAmenity());
        }

        HotelResponse response = new HotelResponse(
                hotel.getId(),
                hotel.getName(),
                hotel.getBrand(),
                addressDTO,
                contactDTO,
                arrivalTimeDTO,
                amenities
        );
        return response;
    }
}
