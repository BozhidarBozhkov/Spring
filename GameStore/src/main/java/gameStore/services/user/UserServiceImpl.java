package gameStore.services.user;

import gameStore.domain.dtos.UserRegister;
import gameStore.domain.entities.User;
import gameStore.domain.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static gameStore.constants.Validations.EMAIL_EXISTS;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper = new ModelMapper();

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

        final UserRegister userRegister = new UserRegister(email, password, confirmPassword, fullName);
        final User user = this.modelMapper.map(userRegister, User.class);
        //  final User user1 = userRegister.toUser();
        if (this.userRepository.count() == 0) {
            user.setAdmin(true);
        }

        boolean isUserFound = this.userRepository.findByEmail(userRegister.getEmail()).isPresent();

        if (isUserFound) {
            throw new IllegalArgumentException(EMAIL_EXISTS);
        }

        this.userRepository.save(user);

        return userRegister.successfulRegisterFormat();
    }
}
