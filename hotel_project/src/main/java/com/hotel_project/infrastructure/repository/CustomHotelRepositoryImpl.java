package com.hotel_project.infrastructure.repository;

import com.hotel_project.domain.Amenity;
import com.hotel_project.domain.Hotel;
import com.hotel_project.features.hotels.search.SearchHotelsRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CustomHotelRepositoryImpl implements CustomHotelRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Hotel> search(SearchHotelsRequest request) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Hotel> query = cb.createQuery(Hotel.class);
        Root<Hotel> root = query.from(Hotel.class);

        List<Predicate> predicates = new ArrayList<>();

        if (request.getName() != null) {
            predicates.add(cb.equal(cb.lower(root.get("name")), request.getName().toLowerCase()));
        }

        if (request.getBrand() != null) {
            predicates.add(cb.equal(cb.lower(root.get("brand")), request.getBrand().toLowerCase()));
        }

        if (request.getCity() != null) {
            predicates.add(cb.equal(cb.lower(root.get("address").get("city")), request.getCity().toLowerCase()));
        }

        if (request.getCountry() != null) {
            predicates.add(cb.equal(cb.lower(root.get("address").get("country")), request.getCountry().toLowerCase()));
        }

        if (request.getAmenities() != null && !request.getAmenities().isEmpty()) {
            Join<Hotel, Amenity> join = root.join("amenities", JoinType.INNER);
            Expression<String> amenityExpression = cb.lower(join.get("amenity"));
            List<String> lowerAmenities = request.getAmenities().stream()
                    .map(String::toLowerCase)
                    .collect(Collectors.toList());
            predicates.add(amenityExpression.in(lowerAmenities));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(query).getResultList();
    }
}
