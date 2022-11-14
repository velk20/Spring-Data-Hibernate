package com.example.gson_lab.models.dtos;

import com.google.gson.annotations.Expose;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddressDTO {
    @Expose
    private String country;
    @Expose
    private String city;
}
