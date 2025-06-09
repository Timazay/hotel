package com.hotel_project.hotels.create;

import com.hotel_project.common.exceptions.BadRequestException;
import com.hotel_project.domain.Address;
import com.hotel_project.domain.ArrivalTime;
import com.hotel_project.domain.Contact;
import com.hotel_project.domain.Hotel;
import com.hotel_project.features.common.ShortHotelResponseConvertor;
import com.hotel_project.features.common.dto.ShortHotelResponse;
import com.hotel_project.features.hotels.create.CreateHotelHandler;
import com.hotel_project.features.hotels.create.CreateHotelRequest;
import com.hotel_project.features.hotels.create.CreateHotelRequestValidator;
import com.hotel_project.features.hotels.get_by_id.AddressDTO;
import com.hotel_project.features.hotels.get_by_id.ArrivalTimeDTO;
import com.hotel_project.features.hotels.get_by_id.ContactDTO;
import com.hotel_project.infrastructure.repository.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateHotelHandlerTest {
    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private CreateHotelRequestValidator hotelRequestValidation;

    @Mock
    private ShortHotelResponseConvertor shortHotelResponseConvertor;

    @InjectMocks
    private CreateHotelHandler handler;

    private CreateHotelRequest request;

    @BeforeEach
    public void setUp() {
        this.handler= new CreateHotelHandler(hotelRepository, hotelRequestValidation, shortHotelResponseConvertor);

        AddressDTO addressDTO = new AddressDTO(
                "123",
                "Main St",
                "Minsk",
                "Belarus",
                "01001");

        ArrivalTimeDTO arrivalTimeDTO = new ArrivalTimeDTO("14:00", "12:00");

        ContactDTO contactDTO = new ContactDTO("+380123456789", "test@example.com");

        request = new CreateHotelRequest();
        request.setName("Hotel Name");
        request.setBrand("BrandX");
        request.setDescription("Nice hotel");
        request.setAddress(addressDTO);
        request.setArrivalTime(arrivalTimeDTO);
        request.setContact(contactDTO);
    }

    @Test
    public void execute_When_Valid_Request_Passed_Should_Return_Expected_ShortHotelResponse() throws Exception {
        Hotel hotel = Hotel.builder()
                .name(request.getName())
                .brand(request.getBrand())
                .description(request.getDescription())
                .address(new Address(
                        request.getAddress().getHouseNumber(),
                        request.getAddress().getStreet(),
                        request.getAddress().getCity(),
                        request.getAddress().getCountry(),
                        request.getAddress().getPostCode()))
                .arrivalTime(new ArrivalTime(
                        request.getArrivalTime().getCheckIn(),
                        request.getArrivalTime().getCheckOut()))
                .contact(new Contact(
                        request.getContact().getPhone(),
                        request.getContact().getEmail()))
                .build();

        when(hotelRepository.save(refEq(hotel))).thenReturn(hotel);

        ShortHotelResponse expectedResponse = new ShortHotelResponse();
        when(shortHotelResponseConvertor.convert(argThat(
                actual -> actual.equals(List.of(hotel))))).thenReturn(List.of(expectedResponse));

        ShortHotelResponse actualResponse = handler.execute(request);

        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    public void execute_When_Request_Dont_Passed_Validation_Should_Throw_BadRequestException() throws Exception {
        doThrow(new BadRequestException("Invalid data"))
                .when(hotelRequestValidation).validate(request);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> handler.execute(request));
        assertEquals("Invalid data", exception.getMessage());
    }
}
