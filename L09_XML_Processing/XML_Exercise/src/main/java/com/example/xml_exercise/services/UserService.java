package com.example.xml_exercise.services;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface UserService {

    void seedUsers() throws JAXBException, IOException;

    void successfullySoldProducts() throws JAXBException;

    void usersAndProducts() throws JAXBException;
}
