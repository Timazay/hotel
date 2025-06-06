package by.timofey.hotel_project.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Address {
    @Column(name = "house_number")
    private String houseNumber;
    private String street;
    private String city;
    private String country;
    @Column(name = "post_code")
    private String postCode;

    @Override
    public String toString() {
        return houseNumber + " " + street + ", " + city + ", " + postCode + ", " + country;
    }
}
