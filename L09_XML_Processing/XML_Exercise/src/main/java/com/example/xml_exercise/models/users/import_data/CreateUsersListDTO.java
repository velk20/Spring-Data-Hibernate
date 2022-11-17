package com.example.xml_exercise.models.users.import_data;

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
public class CreateUsersListDTO {
    @XmlElement(name = "user")
    private List<CreateUserDTO> users;
}
