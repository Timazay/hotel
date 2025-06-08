package com.hotel_project;

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

        Hotel hotel1 = new Hotel();
        Hotel hotel2 = new Hotel();
        hotelList = Arrays.asList(hotel1, hotel2);

        ShortHotelResponse shortResponse1 = new ShortHotelResponse();
        ShortHotelResponse shortResponse2 = new ShortHotelResponse();
        responseList = Arrays.asList(shortResponse1, shortResponse2);
    }

    @Test
    public void testExecute_WithRequest() {
        when(hotelRepository.search(request)).thenReturn(hotelList);
        when(convertor.convert(hotelList)).thenReturn(responseList);

        List<ShortHotelResponse> result = handler.execute(request);

        assertEquals(responseList, result);
        verify(hotelRepository).search(request);
        verify(convertor).convert(hotelList);
    }

    @Test
    public void testExecute_NullRequestReturnsAll() {
        when(hotelRepository.findAll()).thenReturn(hotelList);
        when(convertor.convert(hotelList)).thenReturn(responseList);

        List<ShortHotelResponse> result = handler.execute(null);

        assertEquals(responseList, result);
        verify(hotelRepository).findAll();
        verify(convertor).convert(hotelList);
    }
}
