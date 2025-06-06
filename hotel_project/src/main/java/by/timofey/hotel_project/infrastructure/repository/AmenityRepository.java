package by.timofey.hotel_project.infrastructure.repository;

import by.timofey.hotel_project.infrastructure.entity.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AmenityRepository extends JpaRepository<Amenity, Integer> {
    Optional<Amenity> findByAmenity(String amenity);

}
