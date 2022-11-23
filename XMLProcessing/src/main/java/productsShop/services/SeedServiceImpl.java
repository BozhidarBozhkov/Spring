package productsShop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import productsShop.domain.dtos.users.UsersImportWrapperDto;
import productsShop.domain.entities.Category;
import productsShop.domain.entities.Product;
import productsShop.domain.entities.User;
import productsShop.repositories.CategoryRepository;
import productsShop.repositories.ProductRepository;
import productsShop.repositories.UserRepository;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.IntStream;

import static productsShop.constant.Paths.*;
import static productsShop.constant.Utils.MODEL_MAPPER;

@Service
public class SeedServiceImpl implements SeedService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public SeedServiceImpl(UserRepository userRepository,
                           CategoryRepository categoryRepository,
                           ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void seedUsers() throws IOException, JAXBException {
        if (this.userRepository.count() == 0) {
            final FileReader fileReader = new FileReader(USER_XML_PATH.toFile());

            final JAXBContext context = JAXBContext.newInstance(UsersImportWrapperDto.class);
            final Unmarshaller unmarshaller = context.createUnmarshaller();

            final UsersImportWrapperDto usersDto = (UsersImportWrapperDto) unmarshaller.unmarshal(fileReader);

            final List<User> users = usersDto.getUsers().stream().map(userDto -> MODEL_MAPPER.map(userDto, User.class)).toList();

            this.userRepository.saveAllAndFlush(users);

            fileReader.close();
        }
    }

    @Override
    public void seedCategories() throws IOException {
//        if (this.categoryRepository.count() == 0) {
//            final FileReader fileReader = new FileReader(CATEGORY_XML_PATH.toFile());
//
//            List<Category> categories = Arrays.stream(GSON.fromJson(fileReader, CategoryImportDto[].class))
//                    .map(categoryImportDto -> MODEL_MAPPER.map(categoryImportDto, Category.class))
//                    .collect(Collectors.toList());
//
//            this.categoryRepository.saveAllAndFlush(categories);
//            fileReader.close();
//        }

    }

    @Override
    public void seedProducts() throws IOException {
//        if (this.productRepository.count() == 0) {
//            final FileReader fileReader = new FileReader(PRODUCTS_XML_PATH.toFile());
//
//            List<Product> products = Arrays.stream(GSON.fromJson(fileReader, ProductImportDto[].class))
//                    .map(productImportDto -> MODEL_MAPPER.map(productImportDto, Product.class))
//                    .map(this::setRandomSeller)
//                    .map(this::setRandomBuyer)
//                    .map(this::setRandomCategories)
//                    .collect(Collectors.toList());
//
//            this.productRepository.saveAllAndFlush(products);
//            fileReader.close();
//        }
    }

    private Product setRandomCategories(Product product) {

        final Random random = new Random();

        final int numberOfCategories = random.nextInt((int) this.categoryRepository.count());

        Set<Category> categories = new HashSet<>();

        IntStream.rangeClosed(0, numberOfCategories).forEach(number -> {
            Category category = this.categoryRepository.getRandomEntity().orElseThrow(NoSuchElementException::new);
            categories.add(category);
        });

        product.setCategories(categories);

        return product;
    }

    private Product setRandomBuyer(Product product) {
        if (product.getPrice().compareTo(BigDecimal.valueOf(700L)) > 0) {
            User buyer = this.userRepository.getRandomEntity().orElseThrow(NoSuchElementException::new);

            while (buyer.equals(product.getSeller())) {
                buyer = this.userRepository.getRandomEntity().orElseThrow(NoSuchElementException::new);

            }

            product.setBuyer(buyer);
        }
        return product;
    }

    private Product setRandomSeller(Product product) {
        final User seller = this.userRepository.getRandomEntity().orElseThrow(NoSuchElementException::new);

        product.setSeller(seller);

        return product;
    }
}
