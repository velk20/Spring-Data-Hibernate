package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.part.ImportPartDto;
import softuni.exam.models.entity.Part;
import softuni.exam.repository.PartRepository;
import softuni.exam.service.PartService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.stream.Collectors;

import static softuni.exam.util.ErrorMessages.INVALID_PART;
import static softuni.exam.util.FilesPaths.PARTS_PATH;

@Service
public class PartServiceImpl implements PartService {
    private final PartRepository partRepository;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    private final ModelMapper mapper;

    @Autowired
    public PartServiceImpl(PartRepository partRepository, ValidationUtil validationUtil, Gson gson, ModelMapper mapper) {
        this.partRepository = partRepository;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.mapper = mapper;
    }

    @Override
    public boolean areImported() {
        return this.partRepository.count() > 0;
    }

    @Override
    public String readPartsFileContent() throws IOException {
        return Files.readString(PARTS_PATH);
    }

    @Override
    public String importParts() throws IOException {
        ImportPartDto[] importPartDtos = gson.fromJson(readPartsFileContent(), ImportPartDto[].class);
        return Arrays.stream(importPartDtos)
                .map(this::importPart)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public Part findByPartName(String partName) {
        return this.partRepository.findByPartName(partName).orElse(null);
    }

    @Override
    public Part findById(Long id) {
        return this.partRepository.findById(id).orElse(null);
    }

    private String importPart(ImportPartDto importPartDto) {
        if (!validationUtil.isValid(importPartDto)) return INVALID_PART;

        if (this.findByPartName(importPartDto.getPartName()) != null) return INVALID_PART;

        Part part = mapper.map(importPartDto, Part.class);
        this.partRepository.save(part);
        return String.format("Successfully imported part %s - %.2f", part.getPartName(), part.getPrice());
    }
}
