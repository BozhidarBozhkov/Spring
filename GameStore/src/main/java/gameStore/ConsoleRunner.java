package gameStore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import gameStore.services.user.UserService;

import java.util.Scanner;

import static gameStore.constants.Commands.REGISTER_USER;
import static gameStore.constants.Validations.COMMAND_NOT_FOUND;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private static final Scanner scanner = new Scanner(System.in);
    private final UserService userService;


    public ConsoleRunner(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        String input = scanner.nextLine();
        while (!input.equals("close")) {
            final String[] arguments = scanner.nextLine().split("\\|");
            final String command = arguments[0];

            String output = switch (command) {
                case REGISTER_USER -> userService.registerUser(arguments);
                //case LOGIN_USER ->
                default -> COMMAND_NOT_FOUND;
            };

            System.out.println(output);
            input = scanner.nextLine();
        }

    }
}
