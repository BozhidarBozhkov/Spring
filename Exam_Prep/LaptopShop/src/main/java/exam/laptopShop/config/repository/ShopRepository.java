package exam.laptopShop.config.repository;

import exam.laptopShop.config.model.entities.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {

    Optional<Shop> findShopByName(String name);
    Optional<Shop> findByName(String name);
}