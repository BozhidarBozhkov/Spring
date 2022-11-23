package productsShop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import productsShop.domain.dtos.products.ProductDto;
import productsShop.domain.dtos.products.ProductInRangeWithNoBuyerDto;
import productsShop.repositories.ProductRepository;


import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static productsShop.constant.Paths.PRODUCTS_WITH_NO_BUYERS_IN_RANGE_XML_PATH;
import static productsShop.constant.Utils.MODEL_MAPPER;
import static productsShop.constant.Utils.writeJsonToFile;


@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public List<ProductInRangeWithNoBuyerDto> findAllByPriceBetweenAndBuyerIsNullOrderByPrice(BigDecimal low, BigDecimal high) throws IOException {

        List<ProductInRangeWithNoBuyerDto> products = this.productRepository.findAllByPriceBetweenAndBuyerIsNullOrderByPrice(low, high)
                .orElseThrow(NoSuchElementException::new).stream()
                .map(product -> MODEL_MAPPER.map(product, ProductDto.class))
                .map(ProductDto::toProductInRangeWithNoBuyerDto).collect(Collectors.toList());

        writeJsonToFile(products, PRODUCTS_WITH_NO_BUYERS_IN_RANGE_XML_PATH);

        return products;
    }
}
