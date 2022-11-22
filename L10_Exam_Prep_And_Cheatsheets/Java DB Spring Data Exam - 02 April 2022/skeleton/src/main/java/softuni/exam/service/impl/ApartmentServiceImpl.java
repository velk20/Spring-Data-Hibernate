package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.ImportApartmentDTO;
import softuni.exam.models.dtos.ImportApartmentRootDTO;
import softuni.exam.models.entities.Apartment;
import softuni.exam.models.entities.Town;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.ApartmentService;
import softuni.exam.util.FilesPaths;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Collectors;

import static softuni.exam.util.ErrorMessages.INVALID_APARTMENT;
import static softuni.exam.util.FilesPaths.APARTMENTS_PATH;

@Service
public class ApartmentServiceImpl implements ApartmentService {
    private final ValidationUtil validationUtil;
    private final ModelMapper mapper;
    private final ApartmentRepository apartmentRepository;
    private final TownRepository townRepository;
    private final XmlParser xmlParser;

    public ApartmentServiceImpl(ValidationUtil validationUtil, ModelMapper mapper, ApartmentRepository apartmentRepository, TownRepository townRepository, XmlParser xmlParser) {
        this.validationUtil = validationUtil;
        this.mapper = mapper;
        this.apartmentRepository = apartmentRepository;
        this.townRepository = townRepository;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.apartmentRepository.count() > 0;
    }

    @Override
    public String readApartmentsFromFile() throws IOException {
        return Files.readString(APARTMENTS_PATH);
    }

    @Override
    public String importApartments() throws IOException, JAXBException {
        ImportApartmentRootDTO importApartmentRootDTO = xmlParser.fromFile(APARTMENTS_PATH.toAbsolutePath().toString(),
                ImportApartmentRootDTO.class);
        return importApartmentRootDTO.getApartments()
                .stream()
                .map(this::importApartment)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public Apartment findById(long id) {
        return this.apartmentRepository.findById(id);
    }

    private String importApartment(ImportApartmentDTO importApartmentDTO) {
        if (!validationUtil.isValid(importApartmentDTO)) {
            return INVALID_APARTMENT;
        }

        Optional<Town> townByName = this.townRepository.findByTownName(importApartmentDTO.getTownName());

        Optional<Apartment> apartmentByNameAndArea = this.apartmentRepository.findByTownAndArea(townByName.get(),
                importApartmentDTO.getArea());
        if (apartmentByNameAndArea.isPresent()) {
            return INVALID_APARTMENT;
        }

        Apartment apartment = this.mapper.map(importApartmentDTO, Apartment.class);
        apartment.setTown(townByName.get());
        this.apartmentRepository.save(apartment);
        return String.format("Successfully imported apartment %s - %.2f", apartment.getApartmentType().name(),
                apartment.getArea());
    }
}
