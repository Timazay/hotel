package com.hotel_project.infrastructure.repository;

import com.hotel_project.domain.Hotel;
import com.hotel_project.features.histograms.get.HistogramItemDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long>,
        JpaSpecificationExecutor<Hotel>,
        CustomHotelRepository {

    @Query("SELECT new com.hotel_project.features.histograms.get.HistogramItemDto(h.address.city, COUNT(h)) " +
            "FROM Hotel h " +
            "GROUP BY h.address.city")
    List<HistogramItemDto> groupByCity();

    @Query(value = "SELECT new com.hotel_project.features.histograms.get.HistogramItemDto(h.brand, " +
            "COUNT(h)) FROM Hotel h " +
            "GROUP BY h.brand")
    List<HistogramItemDto> groupByBrand();

    @Query(value = "SELECT new com.hotel_project.features.histograms.get.HistogramItemDto(h.address.country, " +
            "COUNT(h)) FROM Hotel h " +
            "GROUP BY h.address.country")
    List<HistogramItemDto> groupByCountry();

    @Query("SELECT new com.hotel_project.features.histograms.get.HistogramItemDto(a.amenity, COUNT(h.id)) " +
            "FROM Hotel h JOIN h.amenities a " +
            "GROUP BY a.amenity")
    List<HistogramItemDto> groupByAmenities();
}
