package com.hotel_project.features.hotels.get_all;

import com.hotel_project.features.common.ShortHotelResponseConvertor;
import com.hotel_project.features.common.dto.ShortHotelResponse;
import com.hotel_project.domain.Hotel;
import com.hotel_project.infrastructure.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GetAllHotelsHandler {
    private final HotelRepository hotelRepository;
    private final ShortHotelResponseConvertor convertor;

    @Autowired
    public GetAllHotelsHandler(HotelRepository hotelRepository,
                               ShortHotelResponseConvertor convertToShortHotelResponse) {
        this.hotelRepository = hotelRepository;
        this.convertor = convertToShortHotelResponse;
    }

    public List<ShortHotelResponse> execute() {
        List<Hotel> hotels = hotelRepository.findAll();
        return convertor.convert(hotels);
    }
}
