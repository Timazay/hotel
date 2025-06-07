package com.hotel_project.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringExclude;
import java.util.List;

@Data
@Entity
@Table(name = "amenities")
public class Amenity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToStringExclude
    @ManyToMany(mappedBy = "amenities", fetch = FetchType.EAGER)
    private List<Hotel> hotels;

    private String amenity;
}
