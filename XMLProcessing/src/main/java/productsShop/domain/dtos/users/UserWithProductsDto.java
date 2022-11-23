package productsShop.domain.dtos.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import productsShop.domain.dtos.products.ProductsSoldWithCountDto;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserWithProductsDto {

    private String firstName;

    private String lastName;

    private Integer age;

    private ProductsSoldWithCountDto products;
}
