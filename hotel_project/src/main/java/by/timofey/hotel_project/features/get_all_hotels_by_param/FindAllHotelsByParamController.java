package by.timofey.hotel_project.features.get_all_hotels_by_param;

import by.timofey.hotel_project.features.common.ShortHotelResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${hotel.prefix}")
@Tag(name = "All hotels founded by param with short info controller")
public class FindAllHotelsByParamController {
    private FindAllHotelsByParamHandler findAllHotelsByParamHandler;

    @Autowired
    public FindAllHotelsByParamController(FindAllHotelsByParamHandler findAllHotelsByParamHandler) {
        this.findAllHotelsByParamHandler = findAllHotelsByParamHandler;
    }

    @GetMapping("/search")
    @Operation(description = "Returns all hotels with short information with params")
    public ResponseEntity<List<ShortHotelResponse>> findAllHotelsByParam(@RequestParam Map<String, String> model) {
        List<ShortHotelResponse> responses = findAllHotelsByParamHandler.execute(model);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
