package com.hotel_project.controllers;

import com.hotel_project.common.exceptions.BadRequestException;
import com.hotel_project.common.exceptions.NotFoundException;
import com.hotel_project.features.common.dto.ShortHotelResponse;
import com.hotel_project.features.hotels.create.CreateHotelHandler;
import com.hotel_project.features.hotels.create.CreateHotelRequest;
import com.hotel_project.features.hotels.get_all.GetAllHotelsHandler;
import com.hotel_project.features.hotels.get_by_id.GetHotelByIdHandler;
import com.hotel_project.features.hotels.get_by_id.HotelResponse;
import com.hotel_project.features.hotels.search.SearchHotelHandler;
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
    private final CreateHotelHandler createHotel;
    private final GetHotelByIdHandler getHotelById;
    private final GetAllHotelsHandler getAllHotels;
    private final SearchHotelHandler searchHotels;

    public HotelController(CreateHotelHandler createHotelHandler, GetHotelByIdHandler hotelFinderByIdHandler,
                           GetAllHotelsHandler findAllHotelsHandler,
                           SearchHotelHandler findAllHotelsByParamHandler) {
        this.createHotel = createHotelHandler;
        this.getHotelById = hotelFinderByIdHandler;
        this.getAllHotels = findAllHotelsHandler;
        this.searchHotels = findAllHotelsByParamHandler;
    }

    @PostMapping("/hotels")
    @Operation(description = "Create new hotel and returns short info")
    public ResponseEntity<ShortHotelResponse> createHotel(@RequestBody CreateHotelRequest hotel) throws BadRequestException {
        ShortHotelResponse response = createHotel.execute(hotel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/hotels/{id}")
    @Operation(description = "Find particular hotel by id, with full information")
    public ResponseEntity<HotelResponse> findById(@PathVariable int id) throws NotFoundException {
        HotelResponse hotel = getHotelById.execute(id);
        return new ResponseEntity<>(hotel, HttpStatus.OK);
    }

    @GetMapping("/hotels")
    @Operation(description = "Find all hotels with short information about them")
    public ResponseEntity<List<ShortHotelResponse>> findAllHotels() {
        List<ShortHotelResponse> hotelsResponses = getAllHotels.execute();
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
        List<ShortHotelResponse> responses = searchHotels.execute(
                new SearchHotelsRequest(name, brand, country, city, amenities));
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
