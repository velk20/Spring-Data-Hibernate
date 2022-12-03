package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ExportHighestPriceTasksDto;
import softuni.exam.models.dto.task.ImportTaskDto;
import softuni.exam.models.dto.task.ImportTaskRootDto;
import softuni.exam.models.entity.Car;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.models.entity.Part;
import softuni.exam.models.entity.Task;
import softuni.exam.models.entity.enums.CarType;
import softuni.exam.repository.TaskRepository;
import softuni.exam.service.CarService;
import softuni.exam.service.MechanicService;
import softuni.exam.service.PartService;
import softuni.exam.service.TaskService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import static softuni.exam.util.ErrorMessages.INVALID_TASK;
import static softuni.exam.util.FilesPaths.TASKS_PATH;

@Service
public class TaskServiceImpl implements TaskService {
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final TaskRepository taskRepository;
    private final PartService partService;
    private final MechanicService mechanicService;
    private final CarService carService;

    public TaskServiceImpl(ModelMapper mapper, ValidationUtil validationUtil, XmlParser xmlParser, TaskRepository taskRepository, PartService partService, MechanicService mechanicService, CarService carService) {
        this.mapper = mapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.taskRepository = taskRepository;
        this.partService = partService;
        this.mechanicService = mechanicService;
        this.carService = carService;
    }

    @Override
    public boolean areImported() {
        return this.taskRepository.count() > 0;
    }

    @Override
    public String readTasksFileContent() throws IOException {
        return Files.readString(TASKS_PATH);
    }

    @Override
    public String importTasks() throws IOException, JAXBException {
        ImportTaskRootDto importTaskRootDto = xmlParser.fromFile(TASKS_PATH.toAbsolutePath().toString(), ImportTaskRootDto.class);
        return importTaskRootDto
                .getTasks()
                .stream()
                .map(this::importTask)
                .collect(Collectors.joining("\n"));
    }

    private String importTask(ImportTaskDto importTaskDto) {
        if (!validationUtil.isValid(importTaskDto)) return INVALID_TASK;

        Mechanic mechanicByFirstName = this.mechanicService.findByFirstName(importTaskDto.getMechanic().getFirstName());
        if (mechanicByFirstName == null) return INVALID_TASK;

        Car carById = this.carService.findById(importTaskDto.getCar().getId());
        if (carById == null) return INVALID_TASK;

        Part partById = this.partService.findById(importTaskDto.getPart().getId());
        if (partById == null) return INVALID_TASK;

        Task task = mapper.map(importTaskDto, Task.class);
        task.setCar(carById);
        task.setMechanic(mechanicByFirstName);
        task.setPart(partById);

        this.taskRepository.save(task);
        return String.format("Successfully imported task %.2f", task.getPrice());
    }

    @Override
    public String getCoupeCarTasksOrderByPrice() {
        List<ExportHighestPriceTasksDto> highestPriceTasks = this.taskRepository.findHighestPriceTasks(CarType.coupe);
        return highestPriceTasks
                .stream()
                .map(ExportHighestPriceTasksDto::toString)
                .collect(Collectors.joining("\n"));
    }

}
