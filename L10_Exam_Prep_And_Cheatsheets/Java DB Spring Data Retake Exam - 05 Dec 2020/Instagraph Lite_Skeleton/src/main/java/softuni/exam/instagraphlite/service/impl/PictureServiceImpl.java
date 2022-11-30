package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.pictures.ImportJsonPictureDTO;
import softuni.exam.instagraphlite.models.pictures.Picture;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.service.PictureService;
import softuni.exam.instagraphlite.util.ErrorMessages;
import softuni.exam.instagraphlite.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.stream.Collectors;

import static softuni.exam.instagraphlite.util.ErrorMessages.INVALID_PICTURE;
import static softuni.exam.instagraphlite.util.FilesPaths.PICTURES_PATH;

@Service
public class PictureServiceImpl implements PictureService {
    private final Gson gson;
    private final ModelMapper mapper;
    private final PictureRepository pictureRepository;
    private final ValidationUtil validationUtil;

    @Autowired
    public PictureServiceImpl(Gson gson, ModelMapper mapper, PictureRepository pictureRepository, ValidationUtil validationUtil) {
        this.gson = gson;
        this.mapper = mapper;
        this.pictureRepository = pictureRepository;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(PICTURES_PATH);
    }

    @Override
    public String importPictures() throws IOException {
        ImportJsonPictureDTO[] importJsonPictureDTOS = gson.fromJson(readFromFileContent(), ImportJsonPictureDTO[].class);

        return Arrays.stream(importJsonPictureDTOS)
                .map(this::importPicture)
                .collect(Collectors.joining("\n"));
    }

    private String importPicture(ImportJsonPictureDTO importJsonPictureDTO) {
        if (!validationUtil.isValid(importJsonPictureDTO)) {
            return INVALID_PICTURE;
        }

        if (this.pictureRepository.findByPath(importJsonPictureDTO.getPath()).isPresent()) {
            return INVALID_PICTURE;
        }

        this.pictureRepository.save(mapper.map(importJsonPictureDTO, Picture.class));
        return String.format("Successfully imported Picture, with size %.2f", importJsonPictureDTO.getSize());
    }

    @Override
    public String exportPictures() {
        return null;
    }
}
