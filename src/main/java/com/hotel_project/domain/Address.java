package com.hotel_project.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Address {
    private String houseNumber;
    private String street;
    private String city;
    private String country;
    @Column(name = "post_code")
    private String postCode;
}
