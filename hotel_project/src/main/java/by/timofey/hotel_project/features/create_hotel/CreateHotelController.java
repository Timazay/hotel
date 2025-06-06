package by.timofey.hotel_project.features.create_hotel;

import by.timofey.hotel_project.common.exceptions.BadRequestException;
import by.timofey.hotel_project.features.common.ShortHotelResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${hotel.prefix}")
@Tag(name = "Controller to create new hotel")
public class CreateHotelController {
    private final CreateHotelHandler createHotelHandler;

    public CreateHotelController(CreateHotelHandler createHotelHandler) {
        this.createHotelHandler = createHotelHandler;
    }
    @PostMapping("/hotels")
    @Operation(description = "Create new hotel and returns short info")
    public ResponseEntity<ShortHotelResponse> createHotel(@RequestBody HotelRequest hotel) throws BadRequestException {
        ShortHotelResponse response = createHotelHandler.execute(hotel);
        return ResponseEntity.ok(response);
    }
}
