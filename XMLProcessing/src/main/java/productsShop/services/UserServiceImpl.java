package productsShop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import productsShop.domain.dtos.users.UserDto;
import productsShop.domain.dtos.users.UserWithProductsDto;
import productsShop.domain.dtos.users.wrappers.UsersWithProductsWrapperDto;
import productsShop.domain.dtos.users.UsersWithSoldProductsDto;
import productsShop.repositories.UserRepository;


import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static productsShop.constant.Paths.USERS_AND_PRODUCTS_XML_PATH;
import static productsShop.constant.Paths.USERS_WITH_SOLD_PRODUCTS_XML_PATH;
import static productsShop.constant.Utils.MODEL_MAPPER;
import static productsShop.constant.Utils.writeJsonToFile;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UsersWithSoldProductsDto> findAllByOrderByLastNameAscFirstNameAsc() throws IOException {

        final List<UsersWithSoldProductsDto> usersWithSoldProductsDtoList = this.userRepository.findAllByOrderByLastNameAscFirstNameAsc()
                .orElseThrow(NoSuchElementException::new)
                .stream().map(user -> MODEL_MAPPER.map(user, UsersWithSoldProductsDto.class))
                .collect(Collectors.toList());

        writeJsonToFile(usersWithSoldProductsDtoList, USERS_WITH_SOLD_PRODUCTS_XML_PATH);

        return usersWithSoldProductsDtoList;
    }

    @Override
    public UsersWithProductsWrapperDto usersWithProducts() throws IOException {
        final List<UserWithProductsDto> usersAndProducts = this.userRepository.findAllByOrderByLastNameAscFirstNameAsc()
                .orElseThrow(NoSuchElementException::new)
                .stream().map(user -> MODEL_MAPPER.map(user, UserDto.class))
                .map(UserDto::toUserWithProductsDto)
                .collect(Collectors.toList());

        final UsersWithProductsWrapperDto usersWithProductsWrapperDto = new UsersWithProductsWrapperDto(usersAndProducts);

        writeJsonToFile(usersWithProductsWrapperDto, USERS_AND_PRODUCTS_XML_PATH);

        return usersWithProductsWrapperDto;
    }

}
