package com.hotel_project.amenities.create;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.util.stream.Collectors;
import com.hotel_project.common.exceptions.BadRequestException;
import com.hotel_project.common.exceptions.NotFoundException;
import com.hotel_project.domain.*;
import com.hotel_project.features.amenities.create.CreateAmenitiesHandler;
import com.hotel_project.features.amenities.create.CreateAmenitiesRequest;
import com.hotel_project.infrastructure.repository.AmenityRepository;
import com.hotel_project.infrastructure.repository.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CreateAmenitiesHandlerTest {
    @Mock
    private HotelRepository hotelRepository;
    @Mock
    private AmenityRepository amenityRepository;

    @InjectMocks
    private CreateAmenitiesHandler handler;

    private Hotel hotel;
    private List<Amenity> existingAmenities;
    private final String POOL = "Pool";
    private final String WIFI = "WiFi";

    @BeforeEach
    public void setUp() {
        handler = new CreateAmenitiesHandler(hotelRepository, amenityRepository);

        Amenity amenity = new Amenity();
        amenity.setId(1L);
        amenity.setAmenity(WIFI);

        Amenity amenity2 = new Amenity();
        amenity2.setId(2L);
        amenity2.setAmenity(POOL);

        existingAmenities = Arrays.asList(
                amenity,
                amenity2
        );

        hotel = new Hotel();
        hotel.setId(2L);
        hotel.setName("Hotel");
        hotel.setAmenities(new ArrayList<>(existingAmenities));
    }

    @Test
    public void execute_When_Add_New_And_Existing_Amenities_Should_Return_List_Of_Amenities() throws Exception {
        String gym = "Gym";
        List<String> amenitiesToAdd = Arrays.asList(WIFI, gym);
        CreateAmenitiesRequest request = new CreateAmenitiesRequest(2L, amenitiesToAdd);

        when(hotelRepository.findById(request.getHotelId())).thenReturn(Optional.of(hotel));
        Amenity wifi = existingAmenities.get(0);
        Amenity gymAmenity = new Amenity();
        gymAmenity.setId(3L);
        gymAmenity.setAmenity(gym);
        when(amenityRepository.findByAmenityIn(amenitiesToAdd))
                .thenReturn(Arrays.asList(wifi, gymAmenity));

        handler.execute(request);

        List<String> actualResult = hotel.getAmenities().stream()
                .map(Amenity::getAmenity)
                .collect(Collectors.toList());

        assertTrue(actualResult.contains(WIFI));
        assertTrue(actualResult.contains(POOL));
        assertTrue(actualResult.contains(gymAmenity.getAmenity()));
        assertEquals(3, actualResult.size());
    }

    @Test
    public void execute_When_Add_Duplicate_Amenities_Should_Throw_BadRequestException() {
        List<String> amenitiesToAdd = Arrays.asList(WIFI, WIFI);
        CreateAmenitiesRequest request = new CreateAmenitiesRequest(1, amenitiesToAdd);

        assertThrows(BadRequestException.class, () -> {
            handler.execute(request);
        });
    }

    @Test
    public void execute_When_Find_By_Id_With_Not_Existing_Hotel_Should_Throw_NotFoundException() {
        when(hotelRepository.findById(2L)).thenReturn(Optional.empty());
        CreateAmenitiesRequest request = new CreateAmenitiesRequest(2, Arrays.asList("Spa"));

        assertThrows(NotFoundException.class, () -> {
            handler.execute(request);
        });
    }

    @Test
    public void execute_When_Add_New_Amenity_That_Not_Exists_Should_Return_List_Of_Amenities() throws Exception {
        String sauna = "Sauna";
        List<String> amenitiesToAdd = Arrays.asList(sauna);
        CreateAmenitiesRequest request = new CreateAmenitiesRequest(1, amenitiesToAdd);

        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        when(amenityRepository.findByAmenityIn(amenitiesToAdd))
                .thenReturn(Collections.emptyList());

        handler.execute(request);

        List<String> actualResult = hotel.getAmenities().stream()
                .map(Amenity::getAmenity)
                .collect(Collectors.toList());

        assertTrue(actualResult.contains(sauna));
        assertEquals(3, actualResult.size());;
    }

    @Test
    public void execute_When_Add_All_Amenities_That_Already_Exists_Should_Return_List_Of_Amenities() throws Exception {
        List<String> amenitiesToAdd = Arrays.asList(
                hotel.getAmenities().get(0).getAmenity(),
                hotel.getAmenities().get(1).getAmenity()
        );
        CreateAmenitiesRequest request = new CreateAmenitiesRequest(2, amenitiesToAdd);

        when(hotelRepository.findById(request.getHotelId())).thenReturn(Optional.of(hotel));
        when(amenityRepository.findByAmenityIn(amenitiesToAdd))
                .thenReturn(List.of(hotel.getAmenities().get(0), hotel.getAmenities().get(1)));

        handler.execute(request);

        List<String> actualResult = hotel.getAmenities().stream()
                .map(Amenity::getAmenity)
                .collect(Collectors.toList());

        assertTrue(actualResult.contains(hotel.getAmenities().get(0).getAmenity()));
        assertTrue(actualResult.contains(hotel.getAmenities().get(1).getAmenity()));
        assertEquals(2, actualResult.size());
    }
}
