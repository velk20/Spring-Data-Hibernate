package softuni.exam.service;

import softuni.exam.models.entities.Apartment;

import javax.xml.bind.JAXBException;
import java.io.IOException;

// TODO: Implement all methods
public interface ApartmentService {
    
    boolean areImported();

    String readApartmentsFromFile() throws IOException;

    String importApartments() throws IOException, JAXBException;

    Apartment findById(long id);

}
