package com.hotel_project;

import com.hotel_project.common.exceptions.BadRequestException;
import com.hotel_project.features.histograms.get.GetHotelHistogramHadler;
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
    private GetHotelHistogramHadler handler;

    @BeforeEach
    public void setUp() {
        this.handler = new GetHotelHistogramHadler(hotelRepository);
    }

    @Test
    public void testExecute_Amenities() {
        List<HistogramItemDto> mockResults = Arrays.asList(
                new HistogramItemDto("Pool", 10L),
                new HistogramItemDto("Gym", 5L)
        );
        when(hotelRepository.groupByAmenities()).thenReturn(mockResults);

        Map<String, String> result = null;
        try {
            result = handler.execute(HotelParam.fromValue("amenities"));
        } catch (BadRequestException e) {
            throw new RuntimeException(e);
        }

        assertEquals(2, result.size());
        assertEquals("10", result.get("Pool"));
        assertEquals("5", result.get("Gym"));
        verify(hotelRepository).groupByAmenities();
    }

    @Test
    public void testExecute_Brand() {
        List<HistogramItemDto> mockResults = Arrays.asList(
                new HistogramItemDto("BrandA", 7L),
                new HistogramItemDto("BrandB", 3L)
        );
        when(hotelRepository.groupByBrand()).thenReturn(mockResults);

        Map<String, String> result = null;
        try {
            result = handler.execute(HotelParam.fromValue("brand"));
        } catch (BadRequestException e) {
            throw new RuntimeException(e);
        }

        assertEquals(2, result.size());
        assertEquals("7", result.get("BrandA"));
        assertEquals("3", result.get("BrandB"));
        verify(hotelRepository).groupByBrand();
    }

    @Test
    public void testExecute_City() {
        List<HistogramItemDto> mockResults = Arrays.asList(
                new HistogramItemDto("New York", 12L),
                new HistogramItemDto("Paris", 8L)
        );
        when(hotelRepository.groupByCity()).thenReturn(mockResults);

        Map<String, String> result = null;
        try {
            result = handler.execute(HotelParam.fromValue("city"));
        } catch (BadRequestException e) {
            throw new RuntimeException(e);
        }

        assertEquals(2, result.size());
        assertEquals("12", result.get("New York"));
        assertEquals("8", result.get("Paris"));
        verify(hotelRepository).groupByCity();
    }

    @Test
    public void testExecute_Country() {
        List<HistogramItemDto> mockResults = Arrays.asList(
                new HistogramItemDto("USA", 20L),
                new HistogramItemDto("France", 15L)
        );
        when(hotelRepository.groupByCountry()).thenReturn(mockResults);

        Map<String, String> result = null;
        try {
            result = handler.execute(HotelParam.fromValue("country"));
        } catch (BadRequestException e) {
            throw new RuntimeException(e);
        }

        assertEquals(2, result.size());
        assertEquals("20", result.get("USA"));
        assertEquals("15", result.get("France"));
        verify(hotelRepository).groupByCountry();
    }
}
