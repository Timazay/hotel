package com.hotel_project.features.amenities.create;

import com.hotel_project.common.exceptions.BadRequestException;
import com.hotel_project.common.exceptions.NotFoundException;
import com.hotel_project.domain.Amenity;
import com.hotel_project.domain.Hotel;
import com.hotel_project.infrastructure.repository.AmenityRepository;
import com.hotel_project.infrastructure.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.*;

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
        List<Amenity> copyMatches = new ArrayList<>(matches);

        for (int i = 0; i < amenities.size(); i++) {
            for (int j = 0; j < matches.size(); j++) {
                if (!matches.get(j).getAmenity().equals(amenities.get(i))) {
                    Amenity amenity = new Amenity();
                    amenity.setAmenity(amenities.get(i));
                    copyMatches.add(amenity);
                    break;
                }
            }
        }
        hotel.setAmenities(copyMatches);

        try {
            hotelRepository.save(hotel);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("One of amenities already belongs to this hotel");
        }
    }
}
