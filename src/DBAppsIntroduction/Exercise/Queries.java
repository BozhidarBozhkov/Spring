package DBAppsIntroduction.Exercise;

public class Queries {

    public static final String INSERT_INTO_MINIONS = "INSERT INTO minions (name, age) VALUES (?, ?)";
    public static final String INSERT_INTO_VILLAINS = "INSERT INTO villains (name, evilness_factor) VALUES (?, 'evil')";
    public static final String GET_TOWN_BY_NAME = "SELECT t.id FROM towns t WHERE t.name = ?";
    public static final String INSERT_TOWN = "INSERT INTO towns (name) VALUES (?)";
    public static final String GET_VILLAIN_BY_NAME = "SELECT id FROM villains WHERE name = ? ";
    public static final String SELECT_VILLAIN_BY_NAME = "SELECT name FROM villains WHERE id = ?";
    public static final String SELECT_MINIONS_BY_NAME_AND_AGE =  "SELECT name, age FROM minions AS m JOIN minions_villains AS mv ON mv.minion_id = m.id WHERE mv.villain_id = ?";
    public static final String SELECT_ALL_VILLAIN_NAMES_AND_MINION_COUNT = "SELECT v.name, COUNT(DISTINCT m.id) AS number_of_minions from villains AS v\n" +
            " JOIN minions_villains AS mv ON mv.villain_id = v.id" +
            " JOIN minions AS m ON m.id = mv.minion_id" +
            " GROUP BY v.name" +
            " HAVING number_of_minions > 15" +
            " ORDER BY number_of_minions DESC";
    public static final String SELECT_TOWN_NAMES_BY_COUNTRY = "SELECT name FROM towns WHERE country = ?";
    public static final String UPDATE_TOWN_NAMES_UPPER_CASING = "UPDATE towns SET name = UPPER(name) WHERE country = ?";
    public static final String DELETE_VILLAIN = "DELETE FROM villains WHERE id = ?";
    public static final String RELEASED_MINIONS = "DELETE FROM minions_villains WHERE villain_id = ? ";
    public static final String SELECT_MINIONS_BY_NAME = "SELECT name FROM minions";

}
