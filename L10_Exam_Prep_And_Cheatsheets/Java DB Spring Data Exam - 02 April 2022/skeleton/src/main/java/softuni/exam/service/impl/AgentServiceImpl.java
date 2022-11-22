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
import softuni.exam.util.ErrorMessages;
import softuni.exam.util.FilesPaths;
import softuni.exam.util.ValidationUtil;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static softuni.exam.util.ErrorMessages.*;
import static softuni.exam.util.FilesPaths.*;

@Service
public class AgentServiceImpl implements AgentService {
    private final Gson gson;
    private final ValidationUtil validator;
    private final AgentRepository agentRepository;
    private final TownRepository townRepository;
    private final ModelMapper mapper;

    public AgentServiceImpl(Gson gson, ValidationUtil validator, AgentRepository agentRepository, TownRepository townRepository, ModelMapper mapper) {
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
        return Files.readString(AGENTS_PATH);
    }

    @Override
    public String importAgents() throws IOException {
        String json = readAgentsFromFile();
        ImportAgentDTO[] importAgentDTOS = gson.fromJson(json, ImportAgentDTO[].class);

        return Arrays.stream(importAgentDTOS)
                .map(this::importAgent)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public Optional<Agent> findAgentByFirstName(String firstName) {
        return this.agentRepository.findAgentByFirstName(firstName);
    }

    private String importAgent(ImportAgentDTO importAgentDTO) {
        if (!validator.isValid(importAgentDTO)) {
            return INVALID_AGENT;
        }

        Optional<Agent> agentByFirstName =
                this.agentRepository.findAgentByFirstName(importAgentDTO.getFirstName());
        if (agentByFirstName.isPresent()) {
            return INVALID_AGENT;
        }

        Optional<Town> byTownName = townRepository.findByTownName(importAgentDTO.getTown());
        if (byTownName.isEmpty()) {
            return INVALID_AGENT;
        }

        Agent agent = mapper.map(importAgentDTO, Agent.class);

        agent.setTown(byTownName.get());
        this.agentRepository.save(agent);

        return String.format("Successfully imported agent - %s %s", agent.getFirstName(), agent.getLastName());
    }
}
