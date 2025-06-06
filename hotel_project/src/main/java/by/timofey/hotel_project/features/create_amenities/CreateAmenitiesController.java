package by.timofey.hotel_project.features.create_amenities;

import by.timofey.hotel_project.common.exceptions.BadRequestException;
import by.timofey.hotel_project.common.exceptions.NotFoundException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${hotel.prefix}")
@Tag(name = "Controller to create amenities")
public class CreateAmenitiesController {
    private CreateAmenitiesHandler createAmenitiesHandler;

    @Autowired
    public CreateAmenitiesController(CreateAmenitiesHandler createAmenitiesHandler) {
        this.createAmenitiesHandler = createAmenitiesHandler;
    }
    @PostMapping("/hotels/{id}/amenities")
    public ResponseEntity createAmenities(@PathVariable long id, @RequestParam String amenities)
            throws NotFoundException, BadRequestException {
        createAmenitiesHandler.execute(id, amenities);
        return ResponseEntity.ok().build();
    }
}
