package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.ImportUserDto;
import softuni.exam.instagraphlite.models.entities.Picture;
import softuni.exam.instagraphlite.models.entities.User;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.UserService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static softuni.exam.instagraphlite.constant.Messages.INVALID_USER;
import static softuni.exam.instagraphlite.constant.Messages.SUCCESSFULLY_IMPORTED_USER;
import static softuni.exam.instagraphlite.constant.Path.USERS_JSON_PATH;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;

    private final Gson gson;
    private final Validator validator;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PictureRepository pictureRepository) {
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;

        this.gson = new GsonBuilder().create();

        this.validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();

        this.modelMapper = new ModelMapper();
    }

    @Override
    public boolean areImported() {
        return this.userRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        Path path = Path.of(USERS_JSON_PATH);

        return Files.readString(path);
    }

    @Override
    public String importUsers() throws IOException {
        String json = this.readFromFileContent();

        ImportUserDto[] userDtos = this.gson.fromJson(json, ImportUserDto[].class);

        final List<String> result = new ArrayList<>();

        for (ImportUserDto importUser : userDtos) {
            final Set<ConstraintViolation<ImportUserDto>> validationErrors = this.validator.validate(importUser);

            if (validationErrors.isEmpty()) {

                final Optional<User> userExist = this.userRepository.findByUsername(importUser.getUsername());
                final Optional<Picture> pictureExist = this.pictureRepository.findByPath(importUser.getProfilePicture());

                boolean canAdded = userExist.isEmpty() && pictureExist.isPresent();

                if (canAdded) {

                    User user = this.modelMapper.map(importUser, User.class);

                    user.setProfilePicture(pictureExist.get());

                    this.userRepository.save(user);

                    final String msg = String.format(SUCCESSFULLY_IMPORTED_USER, user.getUsername());

                    result.add(msg);

                } else {
                    result.add(INVALID_USER);
                }

            } else {
                result.add(INVALID_USER);
            }

        }

        return String.join(System.lineSeparator(), result);
    }

    @Override
    public String exportUsersWithTheirPosts() {

        return this.userRepository.findAllByOrderByPostsDescIdAsc().stream().map(User::toString).collect(Collectors.joining(System.lineSeparator()));

    }
}
