package com.hotel_project.infrastructure.repository;

import com.hotel_project.domain.Hotel;
import com.hotel_project.features.hotels.search.SearchHotelsRequest;
import java.util.List;

public interface CustomHotelRepository {
    List<Hotel> search(SearchHotelsRequest request);
}
