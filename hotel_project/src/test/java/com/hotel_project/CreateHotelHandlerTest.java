package com.hotel_project;

import com.hotel_project.common.exceptions.BadRequestException;
import com.hotel_project.domain.Address;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
                "Kyiv",
                "Ukraine",
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
    public void testExecute_Success() throws Exception {
        Hotel hotel = Hotel.builder()
                .name(request.getName())
                .brand(request.getBrand())
                .description(request.getDescription())
                .address(new Address(
                        "123",
                        "Main St",
                        "Kyiv",
                        "Ukraine",
                        "01001"))
                .build();

        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);

        ShortHotelResponse expectedResponse = new ShortHotelResponse();
        when(shortHotelResponseConvertor.convert(anyList())).thenReturn(List.of(expectedResponse));

        ShortHotelResponse response = handler.execute(request);

        assertNotNull(response);
        verify(hotelRequestValidation).validate(request);
        verify(hotelRepository).save(any(Hotel.class));
        verify(shortHotelResponseConvertor).convert(anyList());
    }

    @Test
    public void testExecute_ValidationFails() throws Exception {
        doThrow(new BadRequestException("Invalid data"))
                .when(hotelRequestValidation).validate(any());

        assertThrows(BadRequestException.class, () -> handler.execute(request));
        verify(hotelRequestValidation).validate(request);
        verify(hotelRepository, never()).save(any());
        verify(shortHotelResponseConvertor, never()).convert(any());
    }

    @Test
    public void testExecute_EmptyRequestThrows() throws Exception {
        CreateHotelRequest nullRequest = null;

        doThrow(new BadRequestException("Empty request"))
                .when(hotelRequestValidation).validate(nullRequest);

        assertThrows(BadRequestException.class, () -> handler.execute(nullRequest));
        verify(hotelRequestValidation).validate(nullRequest);
    }

    @Test
    public void testExecute_AddressAndContactProcessing() throws Exception {
        Hotel hotel = Hotel.builder()
                .name(request.getName())
                .brand(request.getBrand())
                .description(request.getDescription())
                .build();
        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);

        ShortHotelResponse expectedResponse = new ShortHotelResponse();
        when(shortHotelResponseConvertor.convert(anyList())).thenReturn(List.of(expectedResponse));

        ShortHotelResponse response = handler.execute(request);

        verify(hotelRepository).save(argThat(hotelArg -> {
            Address addr = hotelArg.getAddress();
            return addr.getCountry().equals("Ukraine") && addr.getCity().equals("Kyiv");
        }));
        assertNotNull(response);
    }
}
