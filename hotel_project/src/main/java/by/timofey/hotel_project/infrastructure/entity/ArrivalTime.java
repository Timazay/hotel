package by.timofey.hotel_project.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "arrival_time")
public class ArrivalTime {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @ManyToMany(mappedBy = "arrivalTimes")
    private List<Hotel> hotels;
    private String checkIn;
    private String checkOut;

    @Override
    public String toString() {
        return "ArrivalTime{" +
                "checkIn='" + checkIn + '\'' +
                ", checkOut='" + checkOut + '\'' +
                ", id=" + id +
                '}';
    }
}
