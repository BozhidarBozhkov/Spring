package softuni.exam.instagraphlite.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.ImportPostDto;
import softuni.exam.instagraphlite.models.dto.ImportPostWrapperDto;
import softuni.exam.instagraphlite.models.entities.Picture;
import softuni.exam.instagraphlite.models.entities.Post;
import softuni.exam.instagraphlite.models.entities.User;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.PostRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.PostService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static softuni.exam.instagraphlite.constant.Messages.INVALID_POST;
import static softuni.exam.instagraphlite.constant.Messages.SUCCESSFULLY_IMPORTED_POST;
import static softuni.exam.instagraphlite.constant.Path.POSTS_XML_PATH;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;

    private final Unmarshaller unmarshaller;
    private final Validator validator;
    private final ModelMapper modelMapper;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, PictureRepository pictureRepository) throws JAXBException {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;

        JAXBContext context = JAXBContext.newInstance(ImportPostWrapperDto.class);
        this.unmarshaller = context.createUnmarshaller();

        this.validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();

        this.modelMapper = new ModelMapper();
    }

    @Override
    public boolean areImported() {
        return this.postRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        Path path = Path.of(POSTS_XML_PATH);

        return Files.readString(path);
    }

    @Override
    public String importPosts() throws IOException, JAXBException {
        Path path = Path.of(POSTS_XML_PATH);
        ImportPostWrapperDto postDtos = (ImportPostWrapperDto) this.unmarshaller.unmarshal(new FileReader(path.toAbsolutePath().toString()));

        return postDtos.getPosts().stream().map(this::importPost).collect(Collectors.joining(System.lineSeparator()));

    }

    private String importPost(ImportPostDto dto) {

        Set<ConstraintViolation<ImportPostDto>> errors = this.validator.validate(dto);
        if (!errors.isEmpty()) {
            return INVALID_POST;

        }

        Optional<User> optUser = this.userRepository.findByUsername(dto.getUsernames().getUsername());
        Optional<Picture> optPath = this.pictureRepository.findByPath(dto.getPictures().getPath());

        if (optUser.isEmpty() || optPath.isEmpty()) {
            return INVALID_POST;
        }

        Post post = this.modelMapper.map(dto, Post.class);
        post.setUser(optUser.get());
        post.setPicture(optPath.get());
        this.postRepository.save(post);

        return String.format(SUCCESSFULLY_IMPORTED_POST, post.getUser().getUsername());
    }
}
