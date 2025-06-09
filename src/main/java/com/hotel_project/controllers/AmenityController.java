package com.hotel_project.controllers;

import com.hotel_project.common.exceptions.BadRequestException;
import com.hotel_project.common.exceptions.NotFoundException;
import com.hotel_project.features.amenities.create.CreateAmenitiesHandler;
import com.hotel_project.features.amenities.create.CreateAmenitiesRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("${hotel.prefix}")
@Tag(name = "Amenities controller")
public class AmenityController {
    private CreateAmenitiesHandler createAmenitiesHandler;

    @Autowired
    public AmenityController(CreateAmenitiesHandler createAmenitiesHandler) {
        this.createAmenitiesHandler = createAmenitiesHandler;
    }

    @PostMapping("/hotels/{hotelId}/amenities")
    @Operation(description = "Add amenities to hotel")
    public ResponseEntity createAmenities(@PathVariable long hotelId, @RequestBody List<String> amenities)
            throws NotFoundException, BadRequestException {
        createAmenitiesHandler.execute(new CreateAmenitiesRequest(hotelId, amenities));
        return ResponseEntity.ok().build();
    }
}
