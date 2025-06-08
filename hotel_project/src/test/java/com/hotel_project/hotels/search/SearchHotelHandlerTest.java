package com.hotel_project.hotels.search;

import com.hotel_project.domain.Address;
import com.hotel_project.domain.ArrivalTime;
import com.hotel_project.domain.Contact;
import com.hotel_project.domain.Hotel;
import com.hotel_project.features.common.ShortHotelResponseConvertor;
import com.hotel_project.features.common.dto.ShortHotelResponse;
import com.hotel_project.features.hotels.search.SearchHotelHandler;
import com.hotel_project.features.hotels.search.SearchHotelsRequest;
import com.hotel_project.infrastructure.repository.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SearchHotelHandlerTest {
    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private ShortHotelResponseConvertor convertor;

    @InjectMocks
    private SearchHotelHandler handler;

    private SearchHotelsRequest request;
    private List<Hotel> hotelList;
    private List<ShortHotelResponse> responseList;

    @BeforeEach
    public void setUp() {
        this.handler = new SearchHotelHandler(hotelRepository, convertor);

        request = new SearchHotelsRequest();

        Address address = new Address(
                "123",
                "Main St",
                "Minsk",
                "Belarus",
                "01001");

        ArrivalTime arrivalTime = new ArrivalTime("14:00", "12:00");

        Contact contact = new Contact("+380123456789", "test@example.com");

        Hotel hotel1 = new Hotel();
        Hotel hotel2 = new Hotel();
        hotel1.setName("Hotel Name");
        hotel1.setBrand("BrandX");
        hotel1.setDescription("Nice hotel");
        hotel1.setAddress(address);
        hotel1.setArrivalTime(arrivalTime);
        hotel1.setContact(contact);

        hotel2.setName("Hotel Name");
        hotel2.setBrand("BrandX");
        hotel2.setDescription("Nice hotel");
        hotel2.setAddress(address);
        hotel2.setArrivalTime(arrivalTime);
        hotel2.setContact(contact);
        hotelList = Arrays.asList(hotel1, hotel2);

        ShortHotelResponse shortResponse1 = new ShortHotelResponse();

        ShortHotelResponse shortResponse2 = new ShortHotelResponse();
        responseList = Arrays.asList(shortResponse1, shortResponse2);
    }

    @Test
    public void execute_When_SearchHotelRequest_Find_Hotels_By_One_Of_Param_Should_Return_List_Of_Matching_ShortHotelResponse() {
        request.setName("Hotel Name");
        when(hotelRepository.search(request)).thenReturn(hotelList);
        when(convertor.convert(hotelList)).thenReturn(responseList);

        List<ShortHotelResponse> actualResult = handler.execute(request);

        assertEquals(responseList, actualResult);
        verify(hotelRepository).search(request);
    }

    @Test
    public void execute_When_Parameters_Is_Null_Should_Return_List_Of_All_ShortHotelResponse() {
        when(hotelRepository.findAll()).thenReturn(hotelList);
        when(convertor.convert(hotelList)).thenReturn(responseList);

        List<ShortHotelResponse> result = handler.execute(null);

        assertEquals(responseList, result);
        verify(hotelRepository).findAll();
    }
}
