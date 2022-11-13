package gameStore.services.user;

import gameStore.domain.dtos.UserLogin;
import gameStore.domain.dtos.UserRegister;
import gameStore.domain.entities.User;
import gameStore.domain.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static gameStore.constants.Validations.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    private User loggedUser;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String registerUser(String[] args) {
        final String email = args[1];
        final String password = args[2];
        final String confirmPassword = args[3];
        final String fullName = args[4];

        UserRegister userRegister;

        try {
            userRegister = new UserRegister(email, password, confirmPassword, fullName);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }

        final User user = this.modelMapper.map(userRegister, User.class);
        //  final User user1 = userRegister.toUser();
        if (this.userRepository.count() == 0) {
            user.setAdmin(true);
        }

        boolean isUserFound = this.userRepository.findByEmail(userRegister.getEmail()).isPresent();

        if (isUserFound) {
            return EMAIL_EXISTS;
            // throw new IllegalArgumentException(EMAIL_EXISTS);
        }

        this.userRepository.save(user);

        return userRegister.successfulRegisterFormat();
    }

    @Override
    public String loginUser(String[] args) {
        final String email = args[1];
        final String password = args[2];

        final UserLogin userLogin = new UserLogin(email, password);

        boolean doesUserExists = this.userRepository.existsUserByEmail(userLogin.getEmail());

        if (doesUserExists && this.loggedUser == null) {
            this.loggedUser = this.userRepository.findByEmail(userLogin.getEmail()).get();
            return USER_LOGGED_SUCCESSFULLY + this.loggedUser.getFullName();
        }

        return PASSWORD_NOT_VALID_MESSAGE;
    }

    @Override
    public String logoutUser() {
        if (this.loggedUser == null) {
            return CANNOT_LOGOUT_USER;
        }

        String output = String.format(USER_SUCCESSFULLY_LOGOUT, this.loggedUser.getFullName());

        this.loggedUser = null;

        return output;
    }

    @Override
    public User getLoggedInUser() {
        return this.loggedUser;
    }
}
