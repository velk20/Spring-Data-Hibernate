package com.example.xml_exercise.models.users.task4;

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
public class UsersWrapperDTO {
    @XmlAttribute(name = "count")
    private int count;
    @XmlElement(name = "user")
    private List<UserAndProductsDTO> users;

    public UsersWrapperDTO(List<UserAndProductsDTO> users) {
        this.users = users;
        this.count = users.size();
    }

    public void setUsers(List<UserAndProductsDTO> users) {
        this.users = users;
        this.count = users.size();
    }
}
