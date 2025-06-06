package by.timofey.hotel_project.features.get_all_hotels;

import by.timofey.hotel_project.features.common.ConvertToShortHotelResponse;
import by.timofey.hotel_project.features.common.ShortHotelResponse;
import by.timofey.hotel_project.infrastructure.entity.Address;
import by.timofey.hotel_project.infrastructure.entity.Contact;
import by.timofey.hotel_project.infrastructure.entity.Hotel;
import by.timofey.hotel_project.infrastructure.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindAllHotelsHandler {
    private final HotelRepository hotelRepository;
    private final ConvertToShortHotelResponse convertToShortHotelResponse;

    @Autowired
    public FindAllHotelsHandler(HotelRepository hotelRepository,
                                ConvertToShortHotelResponse convertToShortHotelResponse) {
        this.hotelRepository = hotelRepository;
        this.convertToShortHotelResponse = convertToShortHotelResponse;
    }

    public List<ShortHotelResponse> execute() {
        List<Hotel> hotels = hotelRepository.findAll();
        return convertToShortHotelResponse.convert(hotels);
    }
}
