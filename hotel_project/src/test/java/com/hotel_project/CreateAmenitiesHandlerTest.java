package com.hotel_project;

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

    @BeforeEach
    public void setUp() {
        handler = new CreateAmenitiesHandler(hotelRepository, amenityRepository);

        Amenity amenity = new Amenity();
        amenity.setId(1L);
        amenity.setAmenity("WiFi");

        Amenity amenity2 = new Amenity();
        amenity2.setId(2L);
        amenity2.setAmenity("Pool");

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
    public void test_AddNewAndExistingAmenities_ToSave() throws Exception {
        List<String> amenitiesToAdd = Arrays.asList("WiFi", "Gym");
        CreateAmenitiesRequest request = new CreateAmenitiesRequest(2L, amenitiesToAdd);

        when(hotelRepository.findById(request.getHotelId())).thenReturn(Optional.of(hotel));
        Amenity wifi = existingAmenities.get(0);
        Amenity gym = new Amenity();
        gym.setId(3L);
        gym.setAmenity("Gym");
        when(amenityRepository.findByAmenityIn(amenitiesToAdd))
                .thenReturn(Arrays.asList(wifi, gym));

        handler.execute(request);

        List<String> updatedAmenities = hotel.getAmenities().stream()
                .map(Amenity::getAmenity)
                .collect(Collectors.toList());

        assertTrue(updatedAmenities.contains("WiFi"));
        assertTrue(updatedAmenities.contains("Pool"));
        assertTrue(updatedAmenities.contains("Gym"));
        assertEquals(3, updatedAmenities.size());

        verify(hotelRepository).save(hotel);
    }

    @Test
    public void test_DuplicateAmenities_ThrowsBadRequest() {
        List<String> amenitiesToAdd = Arrays.asList("WiFi", "WiFi");
        CreateAmenitiesRequest request = new CreateAmenitiesRequest(1, amenitiesToAdd);

        assertThrows(BadRequestException.class, () -> {
            handler.execute(request);
        });
    }

    @Test
    public void test_HotelNotFound_ThrowsNotFound() {
        when(hotelRepository.findById(2L)).thenReturn(Optional.empty());
        CreateAmenitiesRequest request = new CreateAmenitiesRequest(2, Arrays.asList("Spa"));

        assertThrows(NotFoundException.class, () -> {
            handler.execute(request);
        });
    }

    @Test
    public void test_AddNewAmenity_When_AmenityNotExists() throws Exception {
        List<String> amenitiesToAdd = Arrays.asList("Sauna");
        CreateAmenitiesRequest request = new CreateAmenitiesRequest(1, amenitiesToAdd);

        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        when(amenityRepository.findByAmenityIn(amenitiesToAdd))
                .thenReturn(Collections.emptyList());

        handler.execute(request);

        List<String> amenitiesNames = hotel.getAmenities().stream()
                .map(Amenity::getAmenity)
                .collect(Collectors.toList());

        assertTrue(amenitiesNames.contains("Sauna"));
        assertEquals(3, amenitiesNames.size());

        verify(hotelRepository).save(hotel);
    }

    @Test
    public void test_AddNewAmenity_When_AllAmenitiesAlreadyExists() throws Exception {
        List<String> amenitiesToAdd = Arrays.asList(
                hotel.getAmenities().get(0).getAmenity(),
                hotel.getAmenities().get(1).getAmenity()
        );
        CreateAmenitiesRequest request = new CreateAmenitiesRequest(1, amenitiesToAdd);

        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        when(amenityRepository.findByAmenityIn(amenitiesToAdd))
                .thenReturn(List.of(hotel.getAmenities().get(0), hotel.getAmenities().get(1)));

        handler.execute(request);

        List<String> amenitiesNames = hotel.getAmenities().stream()
                .map(Amenity::getAmenity)
                .collect(Collectors.toList());

        assertTrue(amenitiesNames.contains(hotel.getAmenities().get(0).getAmenity()));
        assertTrue(amenitiesNames.contains(hotel.getAmenities().get(1).getAmenity()));
        assertEquals(2, amenitiesNames.size());

        verify(hotelRepository).save(hotel);
    }
}
