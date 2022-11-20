package productsShop.constant;

import java.nio.file.Path;

public enum Paths {
    ;

    public static final Path USER_JSON_PATH = Path.of("src/main/resources/dbContent/users.json");

    public static final Path CATEGORY_JSON_PATH = Path.of("src/main/resources/dbContent/categories.json");

    public static final Path PRODUCTS_JSON_PATH = Path.of("src/main/resources/dbContent/products.json");



    public static final Path PRODUCTS_WITH_NO_BUYERS_IN_RANGE_JSON_PATH = Path.of("src/main/resources/outputs/products-in-range.json");

    public static final Path USERS_WITH_SOLD_PRODUCTS_JSON_PATH = Path.of("src/main/resources/outputs/users-sold-products.json");

    public static final Path CATEGORIES_BY_PRODUCTS_JSON_PATH = Path.of("src/main/resources/outputs/categories-by-products.json");

    public static final Path USERS_AND_PRODUCTS_JSON_PATH = Path.of("src/main/resources/outputs/users-and-products.json");
}
