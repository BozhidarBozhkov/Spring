package productsShop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import productsShop.domain.dtos.categories.CategoryProductSummaryDto;
import productsShop.repositories.CategoryRepository;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import static productsShop.constant.Paths.CATEGORIES_BY_PRODUCTS_JSON_PATH;
import static productsShop.constant.Utils.writeJsonToFile;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryProductSummaryDto> getCategorySummary() throws IOException {

        final List<CategoryProductSummaryDto> categoryProductSummaryDtos = this.categoryRepository.getCategorySummary().orElseThrow(NoSuchElementException::new);

        writeJsonToFile(categoryProductSummaryDtos, CATEGORIES_BY_PRODUCTS_JSON_PATH);

        return categoryProductSummaryDtos;
    }
}
