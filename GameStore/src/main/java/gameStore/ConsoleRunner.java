package gameStore;

import gameStore.services.game.GameService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import gameStore.services.user.UserService;

import java.util.Scanner;

import static gameStore.constants.Commands.*;
import static gameStore.constants.Validations.COMMAND_NOT_FOUND;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private static final Scanner scanner = new Scanner(System.in);
    private final UserService userService;
    private final GameService gameService;


    public ConsoleRunner(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    @Override
    public void run(String... args) throws Exception {
        String input = scanner.nextLine();
        while (!input.equals("close")) {
            final String[] arguments = scanner.nextLine().split("\\|");
            final String command = arguments[0];

            final String output = switch (command) {
                case REGISTER_USER -> userService.registerUser(arguments);
                case LOGIN_USER -> userService.loginUser(arguments);
                case LOGOUT_USER -> userService.logoutUser();
                case ADD_GAME -> gameService.addGame(arguments);
                default -> COMMAND_NOT_FOUND;
            };

            System.out.println(output);
            input = scanner.nextLine();
        }

    }
}
