package com.hotel_project.controllers;

import com.hotel_project.common.exceptions.BadRequestException;
import com.hotel_project.common.exceptions.NotFoundException;
import com.hotel_project.domain.Hotel;
import com.hotel_project.features.common.ShortHotelResponse;
import com.hotel_project.features.hotels.create.CreateHotelHandler;
import com.hotel_project.features.hotels.create.HotelRequest;
import com.hotel_project.features.hotels.get_all.FindAllHotelsHandler;
import com.hotel_project.features.hotels.get_by_id.HotelFinderByIdHandler;
import com.hotel_project.features.hotels.get_by_id.HotelResponse;
import com.hotel_project.features.hotels.search.FindAllHotelsByParamHandler;
import com.hotel_project.features.hotels.search.SearchHotelsRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("${hotel.prefix}")
@Tag(name = "Hotel controller")
public class HotelController {
    private final CreateHotelHandler createHotelHandler;
    private final HotelFinderByIdHandler hotelFinderByIdHandler;
    private final FindAllHotelsHandler findAllHotelsHandler;
    private final FindAllHotelsByParamHandler findAllHotelsByParamHandler;

    public HotelController(CreateHotelHandler createHotelHandler, HotelFinderByIdHandler hotelFinderByIdHandler,
                           FindAllHotelsHandler findAllHotelsHandler,
                           FindAllHotelsByParamHandler findAllHotelsByParamHandler) {
        this.createHotelHandler = createHotelHandler;
        this.hotelFinderByIdHandler = hotelFinderByIdHandler;
        this.findAllHotelsHandler = findAllHotelsHandler;
        this.findAllHotelsByParamHandler = findAllHotelsByParamHandler;
    }

    @PostMapping("/hotels")
    @Operation(description = "Create new hotel and returns short info")
    public ResponseEntity<ShortHotelResponse> createHotel(@RequestBody HotelRequest hotel) throws BadRequestException {
        ShortHotelResponse response = createHotelHandler.execute(hotel);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/hotels/{id}")
    @Operation(description = "Find particular hotel by id, with full information")
    public ResponseEntity<HotelResponse> findById(@PathVariable int id) throws NotFoundException {
        HotelResponse hotel = hotelFinderByIdHandler.execute(id);
        return new ResponseEntity<>(hotel, HttpStatus.OK);
    }

    @GetMapping("/hotels")
    @Operation(description = "Find all hotels with short information about them")
    public ResponseEntity<List<ShortHotelResponse>> findAllHotels() {
        List<ShortHotelResponse> hotelsResponses = findAllHotelsHandler.execute();
        return new ResponseEntity<>(hotelsResponses, HttpStatus.OK);
    }

    @GetMapping("/search")
    @Operation(description = "Returns all hotels with short information with params")
    public ResponseEntity<List<ShortHotelResponse>> findAllHotelsByParam(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) List<String> amenities) {
        List<ShortHotelResponse> responses = findAllHotelsByParamHandler.execute(
                new SearchHotelsRequest(name, brand, country, city, amenities));
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
