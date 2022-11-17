package com.example.xml_exercise.models.users;

import lombok.*;

import javax.xml.bind.annotation.*;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsersSoldProductListDTO {
    @XmlElement(name = "user")
    private List<UserSoldProductsDTO> usersAndProducts;

}
