package productsShop.constant;

import java.nio.file.Path;

public enum Paths {
    ;

    public static final Path USER_XML_PATH = Path.of("src/main/resources/dbContent/users.xml");

    public static final Path CATEGORY_XML_PATH = Path.of("src/main/resources/dbContent/categories.xml");

    public static final Path PRODUCTS_XML_PATH = Path.of("src/main/resources/dbContent/products.xml");


    public static final Path PRODUCTS_WITH_NO_BUYERS_IN_RANGE_XML_PATH = Path.of("src/main/resources/outputs/products-in-range.xml");

    public static final Path USERS_WITH_SOLD_PRODUCTS_XML_PATH = Path.of("src/main/resources/outputs/users-sold-products.xml");

    public static final Path CATEGORIES_BY_PRODUCTS_XML_PATH = Path.of("src/main/resources/outputs/categories-by-products.xml");

    public static final Path USERS_AND_PRODUCTS_XML_PATH = Path.of("src/main/resources/outputs/users-and-products.xml");

}