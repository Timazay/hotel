package com.hotel_project.features.amenities.create;

import com.hotel_project.common.exceptions.BadRequestException;
import com.hotel_project.common.exceptions.NotFoundException;
import com.hotel_project.domain.Amenity;
import com.hotel_project.domain.Hotel;
import com.hotel_project.infrastructure.repository.AmenityRepository;
import com.hotel_project.infrastructure.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CreateAmenitiesHandler {
    private final HotelRepository hotelRepository;
    private final AmenityRepository amenityRepository;

    @Autowired
    public CreateAmenitiesHandler(HotelRepository hotelRepository, AmenityRepository amenityRepository) {
        this.hotelRepository = hotelRepository;
        this.amenityRepository = amenityRepository;
    }

    public void execute(CreateAmenitiesRequest request) throws NotFoundException, BadRequestException {
        List<String> amenities = request.getAmenities();

        if (amenities != null) {
            Set<String> amenitiesSet = new HashSet<>(amenities);
            if (amenitiesSet.size() != amenities.size()) {
                throw new BadRequestException("Amenities has duplicate values");
            }
        }

        Hotel hotel = hotelRepository.findById(request.getHotelId())
                .orElseThrow(() -> new NotFoundException("there is no such hotel", Map.of("hotelId",
                        String.valueOf(request.getHotelId()))));


        List<Amenity> matches = amenityRepository.findByAmenityIn(amenities);
        List<Amenity> hotelAmenities = hotel.getAmenities();

        Set<String> matchAmenityNames = matches.stream()
                .map(Amenity::getAmenity)
                .collect(Collectors.toSet());

        List<Amenity> updatedAmenities = new ArrayList<>(hotelAmenities);

        for (Amenity amenity : matches) {
            if (!updatedAmenities.contains(amenity)) {
                updatedAmenities.add(amenity);
            }
        }

        for (String s : amenities) {
            if (!matchAmenityNames.contains(s)) {
                Amenity newAmenity = new Amenity();
                newAmenity.setAmenity(s);
                updatedAmenities.add(newAmenity);
            }
        }

        hotel.setAmenities(updatedAmenities);

        hotelRepository.save(hotel);
    }
}
