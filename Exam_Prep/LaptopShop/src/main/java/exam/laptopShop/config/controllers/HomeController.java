package exam.laptopShop.config.controllers;

import exam.laptopShop.config.service.CustomerService;
import exam.laptopShop.config.service.LaptopService;
import exam.laptopShop.config.service.ShopService;
import exam.laptopShop.config.service.TownService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController extends BaseController {

    private final TownService townService;
    private final ShopService shopService;
    private final CustomerService customerService;
    private final LaptopService laptopService;

    public HomeController(TownService townService, ShopService shopService, CustomerService customerService, LaptopService laptopService) {
        this.townService = townService;
        this.shopService = shopService;
        this.customerService = customerService;
        this.laptopService = laptopService;
    }

    @GetMapping("/")
    public ModelAndView index() {
        boolean areImported = this.townService.areImported() &&
                this.shopService.areImported() &&
                this.customerService.areImported() &&
                this.laptopService.areImported();

        return super.view("index", "areImported", areImported);
    }
}
