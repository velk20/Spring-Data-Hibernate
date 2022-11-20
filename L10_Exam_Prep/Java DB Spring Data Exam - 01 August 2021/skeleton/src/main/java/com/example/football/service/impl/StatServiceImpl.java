package com.example.football.service.impl;

import com.example.football.models.dto.ImportStatDTO;
import com.example.football.models.dto.ImportStatRootDTO;
import com.example.football.models.entity.Stat;
import com.example.football.repository.StatRepository;
import com.example.football.service.StatService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StatServiceImpl implements StatService {
    private final Path path = Path.of("src", "main", "resources", "files", "xml", "stats.xml");
    private final StatRepository statRepository;
    private final JAXBContext jaxbContext;
    private final ModelMapper mapper;
    private final Validator validator;

    @Autowired
    public StatServiceImpl(StatRepository statRepository, ModelMapper mapper, Validator validator) throws JAXBException {
        this.statRepository = statRepository;
        this.validator = validator;
        this.jaxbContext = JAXBContext.newInstance(ImportStatRootDTO.class);
        this.mapper = mapper;
    }

    @Override
    public boolean areImported() {
        return this.statRepository.count() > 0;
    }

    @Override
    public String readStatsFileContent() throws IOException {
        return Files.readString(path);
    }

    @Override
    public String importStats() throws IOException, JAXBException {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        ImportStatRootDTO stats = (ImportStatRootDTO) unmarshaller.unmarshal(Files.newBufferedReader(path));

        return stats.getStats()
                .stream()
                .map(this::importStat)
                .collect(Collectors.joining("\n"));
    }

    private String importStat(ImportStatDTO statDTO) {
        Set<ConstraintViolation<ImportStatDTO>> errors = validator.validate(statDTO);
        if (!errors.isEmpty()) {
            return "Invalid Stat";
        }

        Optional<Stat> isStatExist =
                this.statRepository.findByPassingAndShootingAndEndurance(statDTO.getPassing(), statDTO.getShooting(), statDTO.getEndurance());
        if (isStatExist.isPresent()) {
            return "Invalid Stat";
        }

        Stat stat = mapper.map(statDTO, Stat.class);
        this.statRepository.save(stat);

        return String.format("Successfully imported Stat %.2f - %.2f - %.2f",stat.getPassing(),stat.getShooting(),
                stat.getEndurance());
    }
}
