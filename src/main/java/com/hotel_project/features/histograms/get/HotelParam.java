package com.hotel_project.features.histograms.get;

import com.hotel_project.common.exceptions.BadRequestException;
import java.util.Map;

public enum HotelParam {
    BRAND("brand"),
    CITY("city"),
    COUNTRY("country"),
    AMENITIES("amenities");

    private final String value;

    HotelParam(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static HotelParam fromValue(String value) throws BadRequestException {
        for (HotelParam param : values()) {
            if (param.getValue().equalsIgnoreCase(value)) {
                return param;
            }
        }
        throw new BadRequestException("Invalid parameter value", Map.of("value", value));
    }
}
