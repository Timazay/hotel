package by.timofey.hotel_project.features.create_amenities;

import by.timofey.hotel_project.common.exceptions.BadRequestException;
import by.timofey.hotel_project.common.exceptions.NotFoundException;
import by.timofey.hotel_project.features.common.AmenityConvertorToList;
import by.timofey.hotel_project.infrastructure.entity.Amenity;
import by.timofey.hotel_project.infrastructure.entity.Hotel;
import by.timofey.hotel_project.infrastructure.repository.AmenityRepository;
import by.timofey.hotel_project.infrastructure.repository.HotelRepository;
import org.hibernate.tool.schema.spi.SqlScriptException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CreateAmenitiesHandler {
    private final HotelRepository hotelRepository;
    private final AmenityRepository amenityRepository;
    private final AmenityConvertorToList amenityValidation;

    @Autowired
    public CreateAmenitiesHandler(HotelRepository hotelRepository, AmenityConvertorToList amenityValidation,
                                  AmenityRepository amenityRepository) {
        this.hotelRepository = hotelRepository;
        this.amenityValidation = amenityValidation;
        this.amenityRepository = amenityRepository;
    }

    public void execute(long hotelId, String query) throws NotFoundException, BadRequestException {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new NotFoundException("there is no such hotel", Map.of("hotelId",
                        String.valueOf(hotelId))));
        List<String> amenities = amenityValidation.convert(query);
        List<Amenity> amenityList = hotel.getAmenities();

        Set<Amenity> amenitySet = new HashSet<>(amenityList);

        for (String amenityName : amenities) {
            Amenity amenity = amenityRepository.findByAmenity(amenityName)
                    .orElse(null);
            if (amenity == null) {
                amenity = new Amenity();
                amenity.setAmenity(amenityName);
                    amenityRepository.save(amenity);

            }if (!amenitySet.contains(amenity)) {
                amenitySet.add(amenity);
            }
        }

        hotel.setAmenities(new ArrayList<>(amenitySet));

        try {
            hotelRepository.save(hotel);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("One of amenities already belongs to this hotel");
        }

    }
}
