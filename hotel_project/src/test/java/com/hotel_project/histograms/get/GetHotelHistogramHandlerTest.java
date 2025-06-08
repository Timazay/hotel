package com.hotel_project.histograms.get;

import com.hotel_project.common.exceptions.BadRequestException;
import com.hotel_project.features.histograms.get.GetHotelHistogramHandler;
import com.hotel_project.features.histograms.get.HistogramItemDto;
import com.hotel_project.features.histograms.get.HotelParam;
import com.hotel_project.infrastructure.repository.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetHotelHistogramHandlerTest {
    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private GetHotelHistogramHandler handler;

    @BeforeEach
    public void setUp() {
        this.handler = new GetHotelHistogramHandler(hotelRepository);
    }

    @Test
    public void execute_When_Hotel_Param_Is_Amenities_Should_Return_Map_Of_String_With_Grouped_Values() {
        String pool = "Pool";
        String gym = "Gym";
        List<HistogramItemDto> mockResults = Arrays.asList(
                new HistogramItemDto(pool, 10L),
                new HistogramItemDto(gym, 5L)
        );
        when(hotelRepository.groupByAmenities()).thenReturn(mockResults);

        Map<String, String> actualResults = null;
        try {
            actualResults = handler.execute(HotelParam.fromValue("amenities"));
        } catch (BadRequestException e) {
            throw new RuntimeException(e);
        }

        assertEquals(2, actualResults.size());
        assertEquals("10", actualResults.get(pool));
        assertEquals("5", actualResults.get(gym));
        verify(hotelRepository).groupByAmenities();
    }

    @Test
    public void execute_When_Hotel_Param_Is_Brand_Should_Return_Map_Of_String_With_Grouped_Values() {
        String brand = "BrandA";
        String brand2 = "BrandB";
        List<HistogramItemDto> mockResults = Arrays.asList(
                new HistogramItemDto(brand, 7L),
                new HistogramItemDto(brand2, 3L)
        );
        when(hotelRepository.groupByBrand()).thenReturn(mockResults);

        Map<String, String> result = null;
        try {
            result = handler.execute(HotelParam.fromValue("brand"));
        } catch (BadRequestException e) {
            throw new RuntimeException(e);
        }

        assertEquals(2, result.size());
        assertEquals("7", result.get(brand));
        assertEquals("3", result.get(brand2));
        verify(hotelRepository).groupByBrand();
    }

    @Test
    public void execute_Hotel_Param_Is_City_Should_Return_Map_Of_String_With_Grouped_Values() {
        String city = "New York";
        String city2 = "Paris";
        List<HistogramItemDto> mockResults = Arrays.asList(
                new HistogramItemDto(city, 12L),
                new HistogramItemDto(city2, 8L)
        );
        when(hotelRepository.groupByCity()).thenReturn(mockResults);

        Map<String, String> result = null;
        try {
            result = handler.execute(HotelParam.fromValue("city"));
        } catch (BadRequestException e) {
            throw new RuntimeException(e);
        }

        assertEquals(2, result.size());
        assertEquals("12", result.get(city));
        assertEquals("8", result.get(city2));
        verify(hotelRepository).groupByCity();
    }

    @Test
    public void execute_When_Hotel_Param_Is_Country_Should_Return_Map_Of_String_With_Grouped_Values() {
        String country = "USA";
        String country2 = "France";
        List<HistogramItemDto> mockResults = Arrays.asList(
                new HistogramItemDto(country, 20L),
                new HistogramItemDto(country2, 15L)
        );
        when(hotelRepository.groupByCountry()).thenReturn(mockResults);

        Map<String, String> result = null;
        try {
            result = handler.execute(HotelParam.fromValue("country"));
        } catch (BadRequestException e) {
            throw new RuntimeException(e);
        }

        assertEquals(2, result.size());
        assertEquals("20", result.get(country));
        assertEquals("15", result.get(country2));
        verify(hotelRepository).groupByCountry();
    }
}
