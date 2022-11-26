package productsShop.services;

import productsShop.domain.dtos.users.wrappers.UsersWithProductsWrapperDto;
import productsShop.domain.dtos.users.UsersWithSoldProductsDto;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

public interface UserService {

    List<UsersWithSoldProductsDto> findAllByOrderByLastNameAscFirstNameAsc() throws IOException, JAXBException;

    UsersWithProductsWrapperDto usersWithProducts() throws IOException, JAXBException;
}
