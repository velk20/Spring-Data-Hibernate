package com.example.football.service.impl;

import com.example.football.models.dto.ImportTeamDTO;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.TeamService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements TeamService {
    private final Path path = Path.of("src", "main", "resources","files", "json", "teams.json");
    private final TeamRepository teamRepository;
    private final TownRepository townRepository;
    private final Gson gson;
    private final Validator validator;
    private final ModelMapper modelMapper;
    @Autowired

    public TeamServiceImpl(TeamRepository teamRepository, TownRepository townRepository,Validator validator,
                           Gson gson, ModelMapper modelMapper) {
        this.teamRepository = teamRepository;
        this.townRepository = townRepository;
        this.gson = gson;
        this.validator = validator;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.teamRepository.count() > 0;
    }

    @Override
    public String readTeamsFileContent() throws IOException {
        return Files.readString(path);
    }

    @Override
    public String importTeams() throws IOException {
        String json = readTeamsFileContent();
        ImportTeamDTO[] importTeamDTOS = gson.fromJson(json, ImportTeamDTO[].class);
        return Arrays.stream(importTeamDTOS)
                .map(this::importTeam)
                .collect(Collectors.joining("\n"));
    }

    private String importTeam(ImportTeamDTO importTeamDTO) {
        Set<ConstraintViolation<ImportTeamDTO>> errors = validator.validate(importTeamDTO);
        if (!errors.isEmpty()) {
            return "Invalid Team";
        }

        Optional<Team> isTeamInTheDB = this.teamRepository.findByName(importTeamDTO.getName());
        if (isTeamInTheDB.isPresent()) {
            return "Invalid Team";
        }

        Team team = modelMapper.map(importTeamDTO, Team.class);
        Optional<Town> byName = this.townRepository.findByName(importTeamDTO.getTownName());
        team.setTown(byName.get());

        this.teamRepository.save(team);
        return String.format("Successfully imported Team %s - %d", team.getName(), team.getFanBase());
    }
}
