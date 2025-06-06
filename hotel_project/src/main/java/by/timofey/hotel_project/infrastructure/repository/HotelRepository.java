package by.timofey.hotel_project.infrastructure.repository;

import by.timofey.hotel_project.infrastructure.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long>, JpaSpecificationExecutor<Hotel> {
    static final String QUERY = "SELECT h.id FROM hotels h JOIN hotel_amenities ah ON h.id=ah.hotel_id " +
            "INNER JOIN amenities a ON ah.amenity_id=a.id " +
            "INNER JOIN contacts c ON h.id=c.hotel_id " +
            "WHERE ";

    @Query(value = "SELECT DISTINCT h.* FROM hotels h JOIN hotel_amenities ah ON h.id=ah.hotel_id " +
            "INNER JOIN amenities a ON ah.amenity_id=a.id " +
            "INNER JOIN contacts c ON h.id=c.hotel_id " +
            "WHERE h.id IN(:ids)", nativeQuery = true)
    List<Hotel> findAllById(List<Long> ids);

    @Query(value = "SELECT city, COUNT(*) AS total_count FROM hotels  " +
            "GROUP BY city", nativeQuery = true)
    List<Object[]> groupByCity();

    @Query(value = "SELECT brand, COUNT(*) AS total_count FROM hotels  " +
            "GROUP BY brand", nativeQuery = true)
    List<Object[]> groupByBrand();

    @Query(value = "SELECT country, COUNT(*) AS total_count FROM hotels  " +
            "GROUP BY country", nativeQuery = true)
    List<Object[]> groupByCountry();

    @Query(value = "SELECT a.amenity, COUNT(h.id) AS total_count FROM hotels h " +
            "JOIN hotel_amenities ah ON h.id=ah.hotel_id " +
            "INNER JOIN amenities a ON ah.amenity_id=a.id " +
            "GROUP BY a.amenity", nativeQuery = true)
    List<Object[]> groupByAmenities();

    List<Long> id(Long id);
}
