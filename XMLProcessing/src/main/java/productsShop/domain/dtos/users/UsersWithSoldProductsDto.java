package productsShop.domain.dtos.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import productsShop.domain.dtos.products.SoldProductsDto;
import productsShop.domain.dtos.products.wrappers.SoldProductsWrapperDto;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersWithSoldProductsDto {

    private String firstName;

    private String lastName;

    private List<SoldProductsDto> boughtProducts;

    public static List<UserWithSoldProductsXmlDto> toUsersWithSoldProductsDto(List<UsersWithSoldProductsDto> input) {
        return input.stream().map(user -> new UserWithSoldProductsXmlDto(user.getFirstName(), user.getLastName(), new SoldProductsWrapperDto(user.getBoughtProducts())))
                .toList();
    }
}

