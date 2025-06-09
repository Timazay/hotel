package com.hotel_project.infrastructure.repository;

import com.hotel_project.domain.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Collection;
import java.util.List;

public interface AmenityRepository extends JpaRepository<Amenity, Long> {
    List<Amenity> findByAmenityIn(Collection<String> amenities);
}

