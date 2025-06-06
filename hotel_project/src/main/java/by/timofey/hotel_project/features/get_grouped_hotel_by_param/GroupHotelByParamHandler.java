package by.timofey.hotel_project.features.get_grouped_hotel_by_param;

import by.timofey.hotel_project.common.exceptions.BadRequestException;
import by.timofey.hotel_project.infrastructure.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GroupHotelByParamHandler {
    private final HotelRepository hotelRepository;
    private final String AMENITY = "a.amenity";

    @Autowired
    public GroupHotelByParamHandler(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public Map<String, String> execute(String param) throws BadRequestException {
        String validParams = "brand,city,country,amenities";
        if (!validParams.contains(param)) {
            throw new BadRequestException("Invalid parameter", Map.of("param", param));
        }
        List<Object[]> results = new ArrayList<>();
        if (param.equals("amenities")){
            results = hotelRepository.groupByAmenities();
        } else if (param.equals("brand")){
            results =hotelRepository.groupByBrand();
        } else if (param.equals("city")){
            results = hotelRepository.groupByCity();
        } else if (param.equals("country")){
            results = hotelRepository.groupByCountry();
        }

        Map<String, String> resultMap = new HashMap<>();
        for (Object[] row : results) {
            String key = (String) row[0];
            Long count = (Long) row[1];
            resultMap.put(key, count.toString());
        }
        return resultMap;
    }
}
