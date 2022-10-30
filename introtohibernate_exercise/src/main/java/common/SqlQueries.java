package common;

public class SqlQueries {

    public static final String SELECT_ELIGIBLE_TOWNS = "SELECT t from Town t";
    public static final String CHECK_IF_EMPLOYEE_EXISTS_IN_DATABASE = "SELECT COUNT(e) FROM Employee e WHERE e.firstName = :fn AND e.lastName = :ln";
    public static final String SELECT_EMPLOYEE_WITH_SALARY_OVER_50000 = "SELECT e.firstName FROM Employee e WHERE e.salary > 50000";
    public static final String EXTRACT_ALL_EMPLOYEES_FROM_DEPARTMENT = "SELECT e FROM Employee e WHERE e.department.name = :dp ORDER BY e.salary, e.id";
    public static final String SET_NEW_ADDRESS_TO_EMPLOYEE = "UPDATE Employee e SET e.address = :newAddress WHERE e.lastName = :ln";
    public static final String FIND_ALL_ADDRESSES_ORDER_BY_NUMBER_OF_EMPLOYEE = "SELECT a FROM Address a ORDER BY a.employees.size DESC";
    public static final String GET_EMPLOYEE_WITH_PROJECT = "SELECT e FROM Employee e WHERE e.id = :id";
    public static final String FIND_LATEST_10_PROJECTS = "SELECT p FROM Project p ORDER BY p.startDate desc";
    public static final String UPDATE_EMPLOYEE_SALARY = "UPDATE Employee AS e SET e.salary = e.salary * 1.12 WHERE e.department.id in (1, 2, 14, 3)";
    public static final String SELECT_UPDATED_EMPLOYEE_SALARY = "SELECT e FROM Employee e WHERE e.department.name in ('Engineering', 'Tool Design', 'Marketing', 'Information Services')";
    public static final String SELECT_EMPLOYEE_BY_NAME_STARTING_WITH_PATTERN = "SELECT e FROM Employee e WHERE e.firstName LIKE :p";
    public static final String SELECT_MAX_SALARY_FOR_DEPARTMENT = "SELECT e FROM Employee e GROUP BY e.department HAVING MAX(e.salary) NOT BETWEEN 30000 AND 70000";
    public static final String SELECT_ADDRESS_IN_GIVEN_TOWN = "SELECT a FROM Address a WHERE a.town.name = :town";
    public static final String SELECT_GIVEN_TOWN = "SELECT t FROM Town t WHERE t.name = :town";
}
