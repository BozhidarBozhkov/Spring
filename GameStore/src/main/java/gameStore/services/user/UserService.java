package gameStore.services.user;

import gameStore.domain.entities.User;

public interface UserService {

    String registerUser(String[] args);

    String loginUser(String[] args);

    String logoutUser();

    User getLoggedInUser();
}
