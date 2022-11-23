package productsShop.services;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface SeedService {

    void seedUsers() throws IOException, JAXBException;

    void seedCategories() throws IOException, JAXBException;

    void seedProducts() throws IOException;

    default void seedAll() throws IOException, JAXBException {
        seedUsers();
        seedCategories();
        seedProducts();
    }
}
