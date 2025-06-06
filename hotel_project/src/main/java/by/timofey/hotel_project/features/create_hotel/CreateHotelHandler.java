package by.timofey.hotel_project.features.create_hotel;

import by.timofey.hotel_project.common.exceptions.BadRequestException;
import by.timofey.hotel_project.features.common.ShortHotelResponse;
import by.timofey.hotel_project.infrastructure.entity.Address;
import by.timofey.hotel_project.infrastructure.entity.ArrivalTime;
import by.timofey.hotel_project.infrastructure.entity.Contact;
import by.timofey.hotel_project.infrastructure.entity.Hotel;
import by.timofey.hotel_project.infrastructure.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreateHotelHandler {
    private final HotelRepository hotelRepository;
    private final HotelRequestValidation hotelRequestValidation;

    @Autowired
    public CreateHotelHandler(HotelRepository hotelRepository, HotelRequestValidation hotelRequestValidation) {
        this.hotelRepository = hotelRepository;
        this.hotelRequestValidation = hotelRequestValidation;
    }

    public ShortHotelResponse execute(HotelRequest request) throws BadRequestException {
        hotelRequestValidation.validate(request);
        String city = request.getAddress().getCity();
        Address address = request.getAddress();
        String country = address.getCountry();
        address.setCountry(country.substring(0, 1).toUpperCase() + country.substring(1).toLowerCase());
        address.setCity(city.substring(0, 1).toUpperCase() + city.substring(1).toLowerCase());

        Hotel hotel = Hotel.builder()
                .name(request.getName())
                .brand(request.getBrand())
                .description(request.getDescription())
                .address(address)
                .build();
        List<Contact> contacts = new ArrayList<>();
        Contact contact = request.getContact();
        contacts.add(contact);
        contact.setHotel(hotel);
        hotel.setContacts(contacts);

        List<Hotel> hotels = new ArrayList<>();
        hotels.add(hotel);
        List<ArrivalTime> arrivalTimes = new ArrayList<>();
        ArrivalTime arrivalTime = request.getArrivalTime();

        arrivalTime.setHotels(hotels);
        arrivalTimes.add(request.getArrivalTime());
        hotel.setArrivalTimes(arrivalTimes);


        hotel = hotelRepository.save(hotel);

        return new ShortHotelResponse(hotel.getId(), hotel.getName(), hotel.getDescription(),
                hotel.getAddress().toString(), hotel.getContacts().stream().map(Contact::getPhone)
                .collect(Collectors.joining(" ")));

    }
}
