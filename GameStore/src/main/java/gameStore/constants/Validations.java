package gameStore.constants;

import gameStore.domain.dtos.GameDTO;

public enum Validations {
    ;

    public static final String EMAIL_PATTERN = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";

    public static final String EMAIL_NOT_VALID_MESSAGE = "Incorrect email.";

    public static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$";

    public static final String PASSWORD_NOT_VALID_MESSAGE = "Incorrect username / password";

    public static final String PASSWORD_NOT_MATCH_MESSAGE = "Passwords are not matching";

    public static final String COMMAND_NOT_FOUND = "Command not found";

    public static final String EMAIL_EXISTS = "Email already exists";

    public static final String USER_LOGGED_SUCCESSFULLY = "Successfully logged in ";

    public static final String CANNOT_LOGOUT_USER = "Cannot log out. No user was logged in.";

    public static final String USER_SUCCESSFULLY_LOGOUT = "User %s successfully logged out";

    public static final String INVALID_GAME_TITLE = "Not a valid game title";

    public static final String PRICE_NOT_VALID = "Price must be a positive number";

    public static final String SIZE_NOT_VALID = "Size must be a positive number";

    public static final String INCORRECT_TRAILER_ID = "Trailer ID should be exactly 11 characters";

    public static final String URL_START = "URL should start with http...";

    public static final String DESCRIPTION_LENGTH = "Description must be at least 20 symbols";



}
