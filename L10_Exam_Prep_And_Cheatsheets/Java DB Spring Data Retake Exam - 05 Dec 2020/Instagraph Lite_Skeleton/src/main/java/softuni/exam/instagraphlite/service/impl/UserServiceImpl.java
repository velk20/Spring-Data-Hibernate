package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.pictures.Picture;
import softuni.exam.instagraphlite.models.users.ImportJsonUserDTO;
import softuni.exam.instagraphlite.models.users.User;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.UserService;
import softuni.exam.instagraphlite.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static softuni.exam.instagraphlite.util.ErrorMessages.INVALID_USER;
import static softuni.exam.instagraphlite.util.FilesPaths.USERS_PATH;

@Service
public class UserServiceImpl implements UserService {
    private final Gson gson;
    private final ModelMapper mapper;
    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;
    private final ValidationUtil validationUtil;

    @Autowired
    public UserServiceImpl(Gson gson, ModelMapper mapper, UserRepository userRepository, PictureRepository pictureRepository, ValidationUtil validationUtil) {
        this.gson = gson;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.userRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(USERS_PATH);
    }

    @Override
    public String importUsers() throws IOException {
        ImportJsonUserDTO[] importJsonUserDTOS = gson.fromJson(readFromFileContent(), ImportJsonUserDTO[].class);

        return Arrays.stream(importJsonUserDTOS)
                .map(this::importUser)
                .collect(Collectors.joining("\n"));
    }

    private String importUser(ImportJsonUserDTO importJsonUserDTO) {
        if(!validationUtil.isValid(importJsonUserDTO)) return INVALID_USER;

        Optional<Picture> pictureByPath = pictureRepository.findByPath(importJsonUserDTO.getProfilePicture());
        if (pictureByPath.isEmpty()) return INVALID_USER;

        User user = mapper.map(importJsonUserDTO, User.class);
        user.setProfilePicture(pictureByPath.get());

        this.userRepository.save(user);
        return String.format("Successfully imported User: %s", importJsonUserDTO.getUsername());
    }

    @Override
    public String exportUsersWithTheirPosts() {
        return null;
    }
}
