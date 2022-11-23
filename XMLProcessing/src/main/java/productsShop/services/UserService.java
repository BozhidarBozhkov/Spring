package productsShop.services;

import productsShop.domain.dtos.users.UsersWithProductsWrapperDto;
import productsShop.domain.dtos.users.UsersWithSoldProductsDto;

import java.io.IOException;
import java.util.List;

public interface UserService {

    List<UsersWithSoldProductsDto> findAllByOrderByLastNameAscFirstNameAsc() throws IOException;

    UsersWithProductsWrapperDto usersWithProducts() throws IOException;
}
