package by.timofey.hotel_project.features.common;

import by.timofey.hotel_project.infrastructure.entity.Amenity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AmenityConvertorToList {

    public List<String> convert(String values) {
        if (values == null || values.equals("")) {
            return null;
        }
        List<String> amenities = Arrays.stream(
                        values.replace("[", "")
                                .replace("]", "")
                                .trim()
                                .split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        return amenities;

    }
}
