package com.hotel_project.hotels.get_by_id;

import com.hotel_project.common.exceptions.NotFoundException;
import com.hotel_project.domain.*;
import com.hotel_project.features.hotels.get_by_id.GetHotelByIdHandler;
import com.hotel_project.features.hotels.get_by_id.HotelResponse;
import com.hotel_project.infrastructure.repository.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetHotelByIdHandlerTest {
    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private GetHotelByIdHandler handler;

    private long hotelId;
    private Hotel hotel;

    @BeforeEach
    public void setUp() {
        this.handler = new GetHotelByIdHandler(hotelRepository);
        hotelId = 1L;

        Address address = new Address("123", "Main St", "Minsk", "Belarus", "01001");
        Contact contact = new Contact("+380123456789", "test@example.com");
        ArrivalTime arrivalTime = new ArrivalTime("14:00", "12:00");

        hotel = new Hotel();
        hotel.setId(hotelId);
        hotel.setName("Sample Hotel");
        hotel.setBrand("BrandX");
        hotel.setAddress(address);
        hotel.setContact(contact);
        hotel.setArrivalTime(arrivalTime);

        Amenity amenity = new Amenity();
        amenity.setId(1L);
        amenity.setAmenity("Pool");

        Amenity amenity2 = new Amenity();
        amenity2.setId(2L);
        amenity2.setAmenity("WiFi");

        hotel.setAmenities(List.of(amenity, amenity2));
    }

    @Test
    public void execute_When_Find_Existing_Hotel_By_Id_Should_Return_HotelResponse() throws Exception {
        when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(hotel));

        HotelResponse actualResponse = handler.execute(hotelId);

        assertThat(actualResponse.getId()).isEqualTo(hotel.getId());
        assertThat(actualResponse.getName()).isEqualTo(hotel.getName());
        assertThat(actualResponse.getAddress().getCity()).isEqualTo(hotel.getAddress().getCity());
        assertThat(actualResponse.getContact().getEmail()).isEqualTo(hotel.getContact().getEmail());
        assertThat(actualResponse.getAmenities()).hasSize(2);

    }

    @Test
    public void execute_When_Find_Not_Existing_Hotel_By_Id_Should_Throw_NotFoundException() {
        when(hotelRepository.findById(hotelId)).thenReturn(Optional.empty());

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> handler.execute(hotelId));

        assertEquals("There is no such hotel", thrown.getMessage());
        assertTrue(thrown.getMetadata().containsKey("id"));
        assertEquals(String.valueOf(hotelId), thrown.getMetadata().get("id"));
    }
}
