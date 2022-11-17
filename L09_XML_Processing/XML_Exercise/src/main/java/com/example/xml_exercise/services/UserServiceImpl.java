package com.example.xml_exercise.services;

import com.example.xml_exercise.models.users.User;
import com.example.xml_exercise.models.users.UserSoldProductsDTO;
import com.example.xml_exercise.models.users.UsersSoldProductListDTO;
import com.example.xml_exercise.models.users.import_data.CreateUsersListDTO;
import com.example.xml_exercise.models.users.task4.UserAndProductsDTO;
import com.example.xml_exercise.models.users.task4.UsersWrapperDTO;
import com.example.xml_exercise.repositories.UserRepository;
import com.example.xml_exercise.utils.FilesPaths;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JAXBContext context;
    private final ModelMapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, @Qualifier("createUsers") JAXBContext context, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.context = context;
        this.mapper = mapper;
    }

    @Override
    public void seedUsers() throws JAXBException, IOException {
        Unmarshaller unmarshaller = context.createUnmarshaller();
        BufferedReader fileReader = Files.newBufferedReader(Paths.get(FilesPaths.USERS_FILE_PATH));
        CreateUsersListDTO users = (CreateUsersListDTO) unmarshaller.unmarshal(fileReader);
        users.getUsers()
                .stream()
                .map(userDTO -> mapper.map(userDTO, User.class))
                .forEach(this.userRepository::save);
    }

    @Override
    @Transactional
    public void successfullySoldProducts() throws JAXBException {
        List<User> users = this.userRepository.usersAndSoldProducts()
                .stream()
                .filter(u-> u.getSoldProducts().removeIf(p->p.getBuyer()==null))
                .toList();
        List<UserSoldProductsDTO> userSoldProductsDTOS = new ArrayList<>();
        for (User user : users) {
            UserSoldProductsDTO map = mapper.map(user, UserSoldProductsDTO.class);
            userSoldProductsDTOS.add(map);
        }
        UsersSoldProductListDTO usersSoldProductListDTO = new UsersSoldProductListDTO(userSoldProductsDTOS);

        JAXBContext jaxbContext = JAXBContext.newInstance(UsersSoldProductListDTO.class);

        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(usersSoldProductListDTO,new File(FilesPaths.USERS_SOLD_PRODUCTS_PATH));
    }

    @Override
    @Transactional
    public void usersAndProducts() throws JAXBException {
        List<User> users = this.userRepository.usersAndProducts();
        List<UserAndProductsDTO> userDTOs = new ArrayList<>();

        for (User user : users) {
            userDTOs.add(mapper.map(user, UserAndProductsDTO.class));
        }

        UsersWrapperDTO wrapperDTO = new UsersWrapperDTO(userDTOs);
        JAXBContext jaxbContext = JAXBContext.newInstance(UsersWrapperDTO.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(wrapperDTO, new File(FilesPaths.USERS_AND_PRODUCTS_PATH));
    }
}
