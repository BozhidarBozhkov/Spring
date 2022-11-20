package productsShop.domain.dtos.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import productsShop.domain.dtos.products.SoldProductsDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersWithSoldProductsDto {

    private String firstName;

    private String lastName;

    private List<SoldProductsDto> boughtProducts;
}
