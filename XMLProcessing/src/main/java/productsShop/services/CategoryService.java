package productsShop.services;

import productsShop.domain.dtos.categories.CategoryProductSummaryDto;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

public interface CategoryService {

    List<CategoryProductSummaryDto> getCategorySummary() throws IOException, JAXBException;
}
