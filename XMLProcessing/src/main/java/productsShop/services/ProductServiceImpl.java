package productsShop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import productsShop.domain.dtos.products.ProductDto;
import productsShop.domain.dtos.products.ProductInRangeWithNoBuyerDto;
import productsShop.domain.dtos.products.wrappers.ProductsInRangeWithNoBuyerWrapperDto;
import productsShop.repositories.ProductRepository;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static productsShop.constant.Paths.PRODUCTS_WITH_NO_BUYERS_IN_RANGE_XML_PATH;
import static productsShop.constant.Utils.MODEL_MAPPER;
import static productsShop.constant.Utils.writeXMLToFile;


@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public List<ProductInRangeWithNoBuyerDto> findAllByPriceBetweenAndBuyerIsNullOrderByPrice(BigDecimal low, BigDecimal high) throws IOException, JAXBException {

        final List<ProductInRangeWithNoBuyerDto> products = this.productRepository.findAllByPriceBetweenAndBuyerIsNullOrderByPrice(low, high)
                .orElseThrow(NoSuchElementException::new).stream()
                .map(product -> MODEL_MAPPER.map(product, ProductDto.class))
                .map(ProductDto::toProductInRangeWithNoBuyerDto).collect(Collectors.toList());

        final ProductsInRangeWithNoBuyerWrapperDto productsWrapper = new ProductsInRangeWithNoBuyerWrapperDto(products);

        writeXMLToFile(productsWrapper, PRODUCTS_WITH_NO_BUYERS_IN_RANGE_XML_PATH);

        return products;
    }
}
