package productsShop.domain.dtos.users.wrappers;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import productsShop.domain.dtos.users.UserWithProductsDto;

import javax.xml.bind.annotation.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserWithProductsWrapperXmlDto {

    @XmlAttribute
    private Integer count;

    @XmlElement(name = "user")
    private List<UserWithProductsDto> users;

    public UserWithProductsWrapperXmlDto(List<UserWithProductsDto> users) {
        this.users = users;
        this.count = users.size();
    }

}
