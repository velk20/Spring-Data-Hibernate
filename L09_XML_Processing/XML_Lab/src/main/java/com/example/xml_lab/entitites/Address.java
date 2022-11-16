package com.example.xml_lab.entitites;

import com.google.gson.annotations.Expose;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Expose
    private String country;
    @Expose
    private String city;

    public Address(String country, String city) {
        this.country = country;
        this.city = city;
    }
}