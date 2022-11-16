package com.example.gson_exercise.services;

import com.example.gson_exercise.models.dtos.CreateUserDTO;
import com.example.gson_exercise.models.dtos.UsersSoldProductsDTO;
import com.example.gson_exercise.models.dtos.task4.UserWithProductsDto;
import com.example.gson_exercise.models.dtos.task4.UsersWithProductsWrapperDto;
import com.example.gson_exercise.models.entities.User;
import com.example.gson_exercise.models.utils.FilesPaths;
import com.example.gson_exercise.models.utils.ReadJsonFromFile;
import com.example.gson_exercise.repositories.UserRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final Gson gson;
    private final ModelMapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, Gson gson, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.gson = gson;
        this.mapper = mapper;
    }

    @Override
    public CreateUserDTO[] readAllUsersFromFile() throws IOException {
        String json = ReadJsonFromFile.readFromFile(FilesPaths.USERS_FILE_PATH);
        return gson.fromJson(json, CreateUserDTO[].class);
    }

    @Override
    public void saveUser(CreateUserDTO createUserDTO) {
        User createUser = mapper.map(createUserDTO, User.class);
        this.userRepository.save(createUser);
    }

    @Override
    @Transactional
    public String successfullySoldProducts() {
        Gson outputGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
        List<User> allSuccessfullySoldProducts = this.userRepository.getAllSuccessfullySoldProducts();

        List<UsersSoldProductsDTO> dtos = allSuccessfullySoldProducts
                .stream()
                .map(user -> mapper.map(user, UsersSoldProductsDTO.class))
                .toList();

        return outputGson.toJson(dtos);
    }

    @Override
    @Transactional
    public String getUsersAndProducts() {
        List<User> usersAndProducts = this.userRepository.getUsersAndProducts();
        List<UserWithProductsDto> userWithProductsDtos = usersAndProducts
                .stream()
                .map(u -> mapper.map(u, UserWithProductsDto.class)).toList();

        UsersWithProductsWrapperDto result = new UsersWithProductsWrapperDto(userWithProductsDtos);

        Gson outputGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
        return outputGson.toJson(result);
    }


}
