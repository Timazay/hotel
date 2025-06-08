package com.hotel_project.features.hotels.create;

import com.hotel_project.common.exceptions.BadRequestException;
import com.hotel_project.common.helpers.StringHelper;
import com.hotel_project.features.common.dto.ShortHotelResponse;
import com.hotel_project.domain.Address;
import com.hotel_project.domain.ArrivalTime;
import com.hotel_project.domain.Contact;
import com.hotel_project.domain.Hotel;
import com.hotel_project.features.hotels.get_by_id.AddressDTO;
import com.hotel_project.features.hotels.get_by_id.ArrivalTimeDTO;
import com.hotel_project.features.hotels.get_by_id.ContactDTO;
import com.hotel_project.infrastructure.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateHotelHandler {
    private final HotelRepository hotelRepository;
    private final CreateHotelRequestValidator hotelRequestValidation;

    @Autowired
    public CreateHotelHandler(HotelRepository hotelRepository, CreateHotelRequestValidator hotelRequestValidation) {
        this.hotelRepository = hotelRepository;
        this.hotelRequestValidation = hotelRequestValidation;
    }

    public ShortHotelResponse execute(CreateHotelRequest request) throws BadRequestException {
        hotelRequestValidation.validate(request);
        String city = request
                .getAddress()
                .getCity();
        AddressDTO addressDTO = request.getAddress();
        Address  address = new Address(addressDTO.getHouseNumber(),
                addressDTO.getStreet(),
                addressDTO.getCity(),
                addressDTO.getCountry(),
                addressDTO.getPostCode());

        ArrivalTimeDTO arrivalTimeDTO = request.getArrivalTime();
        ArrivalTime arrivalTime = new ArrivalTime(arrivalTimeDTO.getCheckIn(), arrivalTimeDTO.getCheckOut());

        ContactDTO contactDTO = request.getContact();
        Contact contact = new Contact(contactDTO.getPhone(), contactDTO.getEmail());

        String country = address.getCountry();
        address.setCountry(StringHelper.makeFirstLetterInUpperCase(country));
        address.setCity(StringHelper.makeFirstLetterInUpperCase(city));

        Hotel hotel = Hotel.builder()
                .name(request.getName())
                .brand(request.getBrand())
                .description(request.getDescription())
                .address(address)
                .arrivalTime(arrivalTime)
                .contact(contact)
                .build();

        hotel = hotelRepository.save(hotel);

        return new ShortHotelResponse(hotel.getId(),
                hotel.getName(),
                hotel.getDescription(),
                hotel.getAddress().toString(),
                hotel.getContact().toString());
    }
}
