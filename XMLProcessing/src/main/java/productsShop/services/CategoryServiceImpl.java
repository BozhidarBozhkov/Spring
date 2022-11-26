package productsShop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import productsShop.domain.dtos.categories.CategoryProductSummaryDto;
import productsShop.domain.dtos.categories.wrappers.CategoriesImportWrapperDto;
import productsShop.domain.dtos.categories.wrappers.CategoriesProductSummaryWrapperDto;
import productsShop.repositories.CategoryRepository;


import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import static productsShop.constant.Paths.CATEGORIES_BY_PRODUCTS_XML_PATH;
import static productsShop.constant.Utils.writeJsonToFile;
import static productsShop.constant.Utils.writeXMLToFile;


@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryProductSummaryDto> getCategorySummary() throws IOException, JAXBException {

        final List<CategoryProductSummaryDto> categoryProductSummaryDtos = this.categoryRepository.getCategorySummary().orElseThrow(NoSuchElementException::new);

        final CategoriesProductSummaryWrapperDto categoriesWrapper =
                new CategoriesProductSummaryWrapperDto(categoryProductSummaryDtos);

        writeXMLToFile(categoriesWrapper, CATEGORIES_BY_PRODUCTS_XML_PATH);

        return categoryProductSummaryDtos;
    }
}
