package productsShop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import productsShop.services.CategoryService;
import productsShop.services.ProductService;
import productsShop.services.SeedService;
import productsShop.services.UserService;


import javax.transaction.Transactional;
import java.math.BigDecimal;

@Component
public class CommandRunner implements CommandLineRunner {

    private final SeedService seedService;
    private final ProductService productService;
    private final UserService userService;
    private final CategoryService categoryService;

    @Autowired
    public CommandRunner(SeedService seedService, ProductService productService, UserService userService, CategoryService categoryService) {
        this.seedService = seedService;
        this.productService = productService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        this.seedService.seedAll();
      //   Query 1
      //  this.productService.findAllByPriceBetweenAndBuyerIsNullOrderByPrice(BigDecimal.valueOf(500), BigDecimal.valueOf(1000));

////        // Query 2
//        this.userService.findAllByOrderByLastNameAscFirstNameAsc();
////
//        // Query 3
//        this.categoryService.getCategorySummary();

        // Query 4
        this.userService.usersWithProducts();
    }
}
