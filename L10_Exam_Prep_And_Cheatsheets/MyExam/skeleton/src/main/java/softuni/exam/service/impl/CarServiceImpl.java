package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.car.ImportCarDto;
import softuni.exam.models.dto.car.ImportCarsRootDto;
import softuni.exam.models.entity.Car;
import softuni.exam.repository.CarRepository;
import softuni.exam.service.CarService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Collectors;

import static softuni.exam.util.ErrorMessages.INVALID_CAR;
import static softuni.exam.util.FilesPaths.CARS_PATH;

@Service
public class CarServiceImpl implements CarService {
    private final XmlParser xmlParser;
    private final ModelMapper mapper;
    private final CarRepository carRepository;
    private final ValidationUtil validationUtil;

    @Autowired
    public CarServiceImpl(XmlParser xmlParser, ModelMapper mapper, CarRepository carRepository, ValidationUtil validationUtil) {
        this.xmlParser = xmlParser;
        this.mapper = mapper;
        this.carRepository = carRepository;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.carRepository.count() > 0;
    }

    @Override
    public String readCarsFromFile() throws IOException {
        return Files.readString(CARS_PATH);
    }

    @Override
    public String importCars() throws IOException, JAXBException {
        ImportCarsRootDto importCarsRootDto = xmlParser.fromFile(CARS_PATH.toAbsolutePath().toString(), ImportCarsRootDto.class);
        return importCarsRootDto.getCars()
                .stream()
                .map(this::importCar)
                .collect(Collectors.joining("\n"));
    }

    private String importCar(ImportCarDto importCarDto) {
        if (!validationUtil.isValid(importCarDto)) return INVALID_CAR;

        if (findByPlateNumber(importCarDto.getPlateNumber())!=null) return INVALID_CAR;

        Car car = mapper.map(importCarDto, Car.class);
        this.carRepository.save(car);
        return String.format("Successfully imported car %s - %s", car.getCarMake(), car.getCarModel());
    }

    @Override
    public Car findByPlateNumber(String plateNumber) {
        return this.carRepository.findByPlateNumber(plateNumber).orElse(null);
    }

    @Override
    public Car findById(Long id) {
        return this.carRepository.findById(id).orElse(null);
    }

}
