package by.timofey.hotel_project.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "amenities")
public class Amenity {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @ManyToMany(mappedBy = "amenities", fetch = FetchType.EAGER)
    private List<Hotel> hotels;
    private String amenity;

    @Override
    public String toString() {
        return "Amenity{" +
                "amenity='" + amenity + '\'' +
                ", id=" + id +
                '}';
    }
}
