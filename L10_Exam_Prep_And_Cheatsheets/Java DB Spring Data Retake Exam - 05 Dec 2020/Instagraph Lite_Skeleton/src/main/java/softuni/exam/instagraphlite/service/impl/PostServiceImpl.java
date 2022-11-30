package softuni.exam.instagraphlite.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.pictures.Picture;
import softuni.exam.instagraphlite.models.posts.ImportPostDTO;
import softuni.exam.instagraphlite.models.posts.ImportPostsRootDTO;
import softuni.exam.instagraphlite.models.posts.Post;
import softuni.exam.instagraphlite.models.users.User;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.PostRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.PostService;
import softuni.exam.instagraphlite.util.ErrorMessages;
import softuni.exam.instagraphlite.util.FilesPaths;
import softuni.exam.instagraphlite.util.ValidationUtil;
import softuni.exam.instagraphlite.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.stream.Collectors;

import static softuni.exam.instagraphlite.util.ErrorMessages.INVALID_POST;
import static softuni.exam.instagraphlite.util.FilesPaths.POSTS_PATH;

@Service
public class PostServiceImpl implements PostService {
    private final XmlParser xmlParser;
    private final ModelMapper mapper;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;
    private final ValidationUtil validationUtil;

    public PostServiceImpl(XmlParser xmlParser, ModelMapper mapper, PostRepository postRepository, UserRepository userRepository, PictureRepository pictureRepository, ValidationUtil validationUtil) {
        this.xmlParser = xmlParser;
        this.mapper = mapper;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.postRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(POSTS_PATH);
    }

    @Override
    public String importPosts() throws IOException, JAXBException {
        ImportPostsRootDTO importPostsRootDTO = xmlParser.fromFile(POSTS_PATH.toAbsolutePath().toString(), ImportPostsRootDTO.class);
        return importPostsRootDTO.getPosts()
                .stream()
                .map(this::importPost)
                .collect(Collectors.joining("\n"));
    }

    private String importPost(ImportPostDTO importPostDTO) {
        if(!validationUtil.isValid(importPostDTO)) return INVALID_POST;

        Optional<Picture> pictureByPath = pictureRepository.findByPath(importPostDTO.getPicture().getPath());
        if (pictureByPath.isEmpty()) return INVALID_POST;
        Optional<User> userByUsername = userRepository.findByUsername(importPostDTO.getUser().getUsername());
        if (userByUsername.isEmpty()) return INVALID_POST;

        Post post = mapper.map(importPostDTO, Post.class);

        post.setPicture(pictureByPath.get());
        post.setUser(userByUsername.get());

        this.postRepository.save(post);

        return String.format("Successfully imported Post, made by %s", post.getUser().getUsername());
    }
}
