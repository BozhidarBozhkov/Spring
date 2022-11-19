package productsShop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import productsShop.services.SeedService;

@Component
public class CommandRunner implements CommandLineRunner {

    private final SeedService seedService;

    @Autowired
    public CommandRunner(SeedService seedService) {
        this.seedService = seedService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.seedService.seedAll();
    }
}
