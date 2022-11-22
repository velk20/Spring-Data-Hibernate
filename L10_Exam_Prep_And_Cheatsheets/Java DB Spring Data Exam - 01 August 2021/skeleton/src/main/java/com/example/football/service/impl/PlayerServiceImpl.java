package com.example.football.service.impl;

import com.example.football.models.dto.ExportBestPlayer;
import com.example.football.models.dto.ImportPlayersRootDTO;
import com.example.football.models.dto.ImportStatDTO;
import com.example.football.models.dto.PlayerDTO;
import com.example.football.models.entity.Player;
import com.example.football.models.entity.Stat;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.PlayerRepository;
import com.example.football.repository.StatRepository;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.PlayerService;
import com.google.gson.Gson;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final Path path = Path.of("src", "main", "resources", "files", "xml", "players.xml");
    private final PlayerRepository playerRepository;
    private final TownRepository townRepository;
    private final StatRepository statRepository;
    private final TeamRepository teamRepository;
    private final JAXBContext jaxbContext;
    private final Gson gson;
    private final Validator validator;
    private final ModelMapper mapper;

    public PlayerServiceImpl(PlayerRepository playerRepository, TownRepository townRepository, StatRepository statRepository, TeamRepository teamRepository, Gson gson, Validator validator, ModelMapper mapper) throws JAXBException {
        this.playerRepository = playerRepository;
        this.townRepository = townRepository;
        this.statRepository = statRepository;
        this.teamRepository = teamRepository;
        this.validator = validator;
        this.mapper = mapper;
        this.jaxbContext = JAXBContext.newInstance(ImportPlayersRootDTO.class);
        this.gson = gson;
    }


    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        return Files.readString(path);
    }

    @Override
    public String importPlayers() throws IOException, JAXBException {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        ImportPlayersRootDTO playersRootDTO = (ImportPlayersRootDTO) unmarshaller.unmarshal(Files.newBufferedReader(path));
        return playersRootDTO.getPlayers()
                .stream()
                .map(this::importPlayer)
                .collect(Collectors.joining("\n"));
    }

    private String importPlayer(PlayerDTO playerDTO) {
        Set<ConstraintViolation<PlayerDTO>> errors = validator.validate(playerDTO);
        if (!errors.isEmpty()) {
            return "Invalid Player";
        }

        if (this.playerRepository.findByEmail(playerDTO.getEmail()).isPresent()) {
            return "Invalid PLayer";
        }

        this.mapper.addConverter(ctx -> LocalDate.parse(ctx.getSource(), DateTimeFormatter.ofPattern("dd/MM/yyyy")), String.class, LocalDate.class);

        Player player = mapper.map(playerDTO, Player.class);

        Optional<Town> town = this.townRepository.findByName(playerDTO.getTown().getName());
        player.setTown(town.get());

        Optional<Team> team = this.teamRepository.findByName(playerDTO.getTeam().getName());
        player.setTeam(team.get());

        Optional<Stat> stat = this.statRepository.findById(playerDTO.getStat().getId());
        player.setStat(stat.get());

        this.playerRepository.save(player);

        return String.format("Successfully imported Player %s %s - %s", player.getFirstName(), player.getLastName(),
                player.getPosition().name());
    }

    @Override
    public String exportBestPlayers() {
        LocalDate after = LocalDate.of(1995, 1, 1);
        LocalDate before = LocalDate.of(2003, 1, 1);

        List<ExportBestPlayer> bestPlayers =
                this.playerRepository.findBestPlayers
                        (after, before);

        List<Player> players = this.playerRepository.findAllByBirthDateBetweenOrderByStatShootingDescStatPassingDescStatEnduranceDescLastNameAsc(after, before);

        return bestPlayers.stream().map(ExportBestPlayer::toString).collect(Collectors.joining("\n"));
    }

}
