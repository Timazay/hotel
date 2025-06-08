package com.hotel_project.features.histograms.get;

import com.hotel_project.infrastructure.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GetHotelHistogramHadler {
    private final HotelRepository hotelRepository;

    @Autowired
    public GetHotelHistogramHadler(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public Map<String, String> execute(HotelParam param) {
        List<HistogramItemDto> results = new ArrayList<>();
        switch (param.getValue()) {
            case "amenities":
                results = hotelRepository.groupByAmenities();
                break;
            case "brand":
                results = hotelRepository.groupByBrand();
                break;
            case "city":
                results = hotelRepository.groupByCity();
                break;
            case "country":
                results = hotelRepository.groupByCountry();
                break;
        }

        Map<String, String> resultMap = new HashMap<>();

        for (HistogramItemDto result : results) {
            String key = result.getKey();
            Long count = result.getCount();
            resultMap.put(key, count.toString());
        }
        return resultMap;
    }
}
