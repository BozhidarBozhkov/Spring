package gameStore.services.game;

import gameStore.domain.dtos.GameDTO;
import gameStore.domain.entities.Game;
import gameStore.domain.repositories.GameRepository;
import gameStore.services.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

import static gameStore.constants.Validations.COMMAND_NOT_FOUND;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final UserService userService;

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, UserService userService) {
        this.gameRepository = gameRepository;
        this.userService = userService;
    }

    @Override
    public String addGame(String[] args) {
        if (this.userService.getLoggedInUser() != null && this.userService.getLoggedInUser().isAdmin()) {
            final String title = args[1];
            final BigDecimal price = new BigDecimal(args[2]);
            final float size = Float.parseFloat(args[3]);
            final String trailer = args[4];
            final String thumbnailURL = args[5];
            final String description = args[6];
            final LocalDate releaseDate = LocalDate.now();

            final GameDTO gameDTO = new GameDTO(title, trailer, thumbnailURL, size, price, description, releaseDate);

            //final Game gameToSave = this.modelMapper.map(gameDTO, Game.class);
            final Game gameToSave = gameDTO.toGame();

            this.gameRepository.save(gameToSave);

            return "Added " + title;
        }

        return COMMAND_NOT_FOUND;
    }

    @Override
    public String editGame(String[] args) {
        return null;
    }

    @Override
    public String deleteGame(Long id) {
        return null;
    }
}
