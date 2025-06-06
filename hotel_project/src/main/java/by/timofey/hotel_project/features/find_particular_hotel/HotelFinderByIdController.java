package by.timofey.hotel_project.features.find_particular_hotel;

import by.timofey.hotel_project.common.exceptions.NotFoundException;
import by.timofey.hotel_project.infrastructure.entity.Hotel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${hotel.prefix}")
@Tag(name = "Hotel with whole information controller")
public class HotelFinderByIdController {
    private HotelFinderByIdHandler hotelFinderByIdHandler;

    @Autowired
    public HotelFinderByIdController(HotelFinderByIdHandler hotelFinderByIdHandler) {
        this.hotelFinderByIdHandler = hotelFinderByIdHandler;
    }

    @GetMapping("/hotels/{id}")
    @Operation(description = "Find particular hotel by id, with full information")
    public ResponseEntity<Hotel> findById(@PathVariable int id) throws NotFoundException {
        Hotel hotel = hotelFinderByIdHandler.execute(id);
        return new ResponseEntity<>(hotel, HttpStatus.OK);
    }
}
