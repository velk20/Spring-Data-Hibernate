package exam.service.impl;

import exam.model.dtos.ImportTownsRoot;
import exam.model.dtos.TownInformationDTO;
import exam.model.entities.Town;
import exam.repository.TownRepository;
import exam.service.TownService;
import exam.util.ErrorMessages;
import exam.util.ValidationUtil;
import exam.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Collectors;

import static exam.util.ErrorMessages.INVALID_TOWN;
import static exam.util.FilesPaths.TOWNS_PATH;

@Service
public class TownServiceImpl implements TownService {
    private final ModelMapper mapper;
    private final TownRepository townRepository;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;

    @Autowired
    public TownServiceImpl(ModelMapper mapper, TownRepository townRepository, XmlParser xmlParser, ValidationUtil validationUtil) {
        this.mapper = mapper;
        this.townRepository = townRepository;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(TOWNS_PATH);
    }

    @Override
    public String importTowns() throws JAXBException, FileNotFoundException {
        ImportTownsRoot importTownsRoot = xmlParser.fromFile(TOWNS_PATH.toAbsolutePath().toString(), ImportTownsRoot.class);
        return importTownsRoot
                .getTowns()
                .stream()
                .map(this::importTown)
                .collect(Collectors.joining("\n"));
    }

    private String importTown(TownInformationDTO townInformationDTO) {
        boolean isValid = validationUtil.isValid(townInformationDTO);
        if (!isValid) return INVALID_TOWN;

        Town town = mapper.map(townInformationDTO, Town.class);
        this.townRepository.save(town);
        return String.format("Successfully imported Town %s", town.getName());
    }
}
