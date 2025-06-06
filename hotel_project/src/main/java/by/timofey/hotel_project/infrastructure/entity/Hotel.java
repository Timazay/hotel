package by.timofey.hotel_project.infrastructure.entity;

import by.timofey.hotel_project.common.AmenityListSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringExclude;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hotels")
public class Hotel {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @JsonIgnore
    private String description;
    @Column()
    private String brand;
    @Embedded
    private Address address;
    @ToStringExclude
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Contact> contacts;
    @ManyToMany(fetch =FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "hotel_arrival_times",
            joinColumns = @JoinColumn(name = "hotel_id"),
            inverseJoinColumns = @JoinColumn(name = "arrival_time_id")
    )
    private List<ArrivalTime> arrivalTimes;
    @JsonSerialize(using = AmenityListSerializer.class)
    @ManyToMany(fetch =FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "hotel_amenities",
            joinColumns = @JoinColumn(name = "hotel_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id"))
    private List<Amenity> amenities;

}
