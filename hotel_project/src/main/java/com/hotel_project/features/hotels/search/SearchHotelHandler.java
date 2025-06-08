package com.hotel_project.features.hotels.search;

import com.hotel_project.features.common.ShortHotelResponseConvertor;
import com.hotel_project.features.common.dto.ShortHotelResponse;
import com.hotel_project.domain.Hotel;
import com.hotel_project.infrastructure.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SearchHotelHandler {
    private final HotelRepository hotelRepository;
    private final ShortHotelResponseConvertor convertor;

    @Autowired
    public SearchHotelHandler(HotelRepository hotelRepository,
                              ShortHotelResponseConvertor convertToShortHotelResponse) {
        this.hotelRepository = hotelRepository;
        this.convertor = convertToShortHotelResponse;
    }

    public List<ShortHotelResponse> execute(SearchHotelsRequest request) {
        List<Hotel> hotels = request == null ? hotelRepository.findAll() : hotelRepository.search(request);

        return convertor.convert(hotels);
    }
}
