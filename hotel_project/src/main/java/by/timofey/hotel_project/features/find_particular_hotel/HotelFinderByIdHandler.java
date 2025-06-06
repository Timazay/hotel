package by.timofey.hotel_project.features.find_particular_hotel;

import by.timofey.hotel_project.common.exceptions.NotFoundException;
import by.timofey.hotel_project.infrastructure.entity.Hotel;
import by.timofey.hotel_project.infrastructure.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class HotelFinderByIdHandler {
    private final HotelRepository hotelRepository;

    @Autowired
    public HotelFinderByIdHandler(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public Hotel execute(long id) throws NotFoundException {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() ->  new NotFoundException("There is no such hotel",
                        Map.of("id", String.valueOf(id))));
        return hotel;
    }
}
