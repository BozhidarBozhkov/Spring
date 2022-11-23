package productsShop.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product extends BaseEntity{

    @Column(nullable = false)
    @Size(min = 3, message = "Name must be at least 3 characters.")
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne
    @Fetch(FetchMode.JOIN)
    private User buyer;

    @ManyToOne
    @Fetch(FetchMode.JOIN)
    private User seller;

    @ManyToMany
    @Fetch(FetchMode.JOIN)
    private Set<Category> categories;

}
