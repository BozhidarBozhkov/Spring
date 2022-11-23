package productsShop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import productsShop.domain.dtos.categories.CategoryProductSummaryDto;
import productsShop.domain.entities.Category;

import java.util.List;
import java.util.Optional;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "select * from `product_shop_xml`.categories order by RAND () LIMIT 1",nativeQuery = true)
    Optional<Category> getRandomEntity();

    @Query("select new productsShop.domain.dtos.categories.CategoryProductSummaryDto(c.name, count(p.id), avg(p.price), sum(p.price)) from Product " +
            "p join p.categories c group by c.id order by count(p.id)")
    Optional<List<CategoryProductSummaryDto>> getCategorySummary();
}
