package by.timofey.hotel_project.features.get_all_hotels;

import by.timofey.hotel_project.features.common.ShortHotelResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${hotel.prefix}")
@Tag(name = "All hotels with short info controller")
public class FindAllHotelsController {
    private FindAllHotelsHandler findAllHotelsHandler;

    @Autowired
    public FindAllHotelsController(FindAllHotelsHandler findAllHotelsHandler) {
        this.findAllHotelsHandler = findAllHotelsHandler;
    }

    @GetMapping("/hotels")
    @Operation(description = "Find all hotels with short information about them")
    public ResponseEntity<List<ShortHotelResponse>> findAllHotels() {
        List<ShortHotelResponse> hotelsResponses = findAllHotelsHandler.execute();
        return new ResponseEntity<>(hotelsResponses, HttpStatus.OK);
    }
}
