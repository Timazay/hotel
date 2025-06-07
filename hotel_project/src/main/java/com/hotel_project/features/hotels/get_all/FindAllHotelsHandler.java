package com.hotel_project.features.hotels.get_all;

import com.hotel_project.features.common.ConvertToShortHotelResponse;
import com.hotel_project.features.common.ShortHotelResponse;
import com.hotel_project.domain.Hotel;
import com.hotel_project.infrastructure.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

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
