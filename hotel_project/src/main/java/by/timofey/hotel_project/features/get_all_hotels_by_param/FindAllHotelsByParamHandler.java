package by.timofey.hotel_project.features.get_all_hotels_by_param;

import by.timofey.hotel_project.features.common.ConvertToShortHotelResponse;
import by.timofey.hotel_project.features.common.ShortHotelResponse;
import by.timofey.hotel_project.features.common.AmenityConvertorToList;
import by.timofey.hotel_project.infrastructure.entity.Amenity;
import by.timofey.hotel_project.infrastructure.entity.Hotel;
import by.timofey.hotel_project.infrastructure.repository.HotelRepository;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FindAllHotelsByParamHandler {
    private final HotelRepository hotelRepository;
    private final AmenityConvertorToList validateAmenities;
    private final ConvertToShortHotelResponse convertToShortHotelResponse;

    @Autowired
    public FindAllHotelsByParamHandler(HotelRepository hotelRepository, AmenityConvertorToList validateAmenities,
                                       ConvertToShortHotelResponse convertToShortHotelResponse) {
        this.hotelRepository = hotelRepository;
        this.validateAmenities = validateAmenities;
        this.convertToShortHotelResponse = convertToShortHotelResponse;
    }

    public List<ShortHotelResponse> execute(Map<String, String> params) {
        List<String> amenities = validateAmenities.convert(params.get(Parameters.AMENITIES.getValue()));

        Specification<Hotel> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            String nameParam = params.get(Parameters.NAME.getValue());
            if (nameParam != null) {
                predicates.add(cb.equal(cb.lower(root.get("name")), nameParam.toLowerCase()));
            }

            String brandParam = params.get(Parameters.BRAND.getValue());
            if (brandParam != null) {
                predicates.add(cb.equal(cb.lower(root.get("brand")), brandParam.toLowerCase()));
            }

            String cityParam = params.get(Parameters.CITY.getValue());
            if (cityParam != null) {
                predicates.add(cb.equal(cb.lower(root.get("address").get("city")), cityParam.toLowerCase()));
            }

            String countryParam = params.get(Parameters.COUNTRY.getValue());
            if (countryParam != null) {
                predicates.add(cb.equal(cb.lower(root.get("address").get("country")), countryParam.toLowerCase()));
            }


            if (amenities != null && !amenities.isEmpty()) {
                Join<Hotel, Amenity> join = root.join("amenities", JoinType.INNER);
                Expression<String> amenityExpression = cb.lower(join.get("amenity"));
                List<String> lowerAmenities = amenities.stream()
                        .map(String::toLowerCase)
                        .collect(Collectors.toList());

                predicates.add(amenityExpression.in(lowerAmenities));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        List<Hotel> hotels = hotelRepository.findAll(spec);
        return convertToShortHotelResponse.convert(hotels);
    }
}
