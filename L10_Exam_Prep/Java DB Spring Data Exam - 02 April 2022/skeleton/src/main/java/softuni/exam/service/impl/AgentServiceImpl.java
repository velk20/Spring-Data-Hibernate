package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.ImportAgentDTO;
import softuni.exam.models.entities.Agent;
import softuni.exam.models.entities.Town;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.AgentService;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AgentServiceImpl implements AgentService {
    private final Path path = Path.of("src", "main", "resources", "files", "json", "agents.json");

    private final Gson gson;
    private final Validator validator;
    private final AgentRepository agentRepository;
    private final TownRepository townRepository;
    private final ModelMapper mapper;

    public AgentServiceImpl(Gson gson, Validator validator, AgentRepository agentRepository, TownRepository townRepository, ModelMapper mapper) {
        this.gson = gson;
        this.validator = validator;
        this.agentRepository = agentRepository;
        this.townRepository = townRepository;
        this.mapper = mapper;
    }

    @Override
    public boolean areImported() {
        return this.agentRepository.count() > 0;
    }

    @Override
    public String readAgentsFromFile() throws IOException {
        return Files.readString(path);
    }

    @Override
    public String importAgents() throws IOException {
        String json = readAgentsFromFile();
        ImportAgentDTO[] importAgentDTOS = gson.fromJson(json, ImportAgentDTO[].class);

        return Arrays.stream(importAgentDTOS)
                .map(this::importAgent)
                .collect(Collectors.joining("\n"));
    }

    private String importAgent(ImportAgentDTO importAgentDTO) {
        Set<ConstraintViolation<ImportAgentDTO>> errors =
                validator.validate(importAgentDTO);
        if (!errors.isEmpty()) {
            return "Invalid agent";
        }

        Optional<Agent> agentByFirstName =
                this.agentRepository.findAgentByFirstName(importAgentDTO.getFirstName());

        if (agentByFirstName.isPresent()) {
            return "Invalid agent";
        }

        Agent agent = mapper.map(importAgentDTO, Agent.class);

        Optional<Town> byTownName = townRepository.findByTownName(importAgentDTO.getTown());
        if (byTownName.isEmpty()) {
            return "Invalid agent";
        }
        agent.setTown(byTownName.get());
        this.agentRepository.save(agent);

        return String.format("Successfully imported agent - %s %s", agent.getFirstName(), agent.getLastName());
    }
}
