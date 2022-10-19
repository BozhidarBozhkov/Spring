package DBAppsIntroduction.Exercise;

import java.sql.*;
import java.util.Properties;

public class P02_GetVillainsNames {
    public static void main(String[] args) throws SQLException {

        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "****");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", props);

        PreparedStatement query = connection.prepareStatement("SELECT v.name, COUNT(DISTINCT m.id) AS number_of_minions from villains AS v\n" +
                " JOIN minions_villains AS mv ON mv.villain_id = v.id" +
                " JOIN minions AS m ON m.id = mv.minion_id" +
                " GROUP BY v.name" +
                " HAVING number_of_minions > 15" +
                " ORDER BY number_of_minions DESC");

       ResultSet resultSet =  query.executeQuery();
       while (resultSet.next()) {
           String villainName = resultSet.getString("name");
           int minion_count = resultSet.getInt("number_of_minions");
           System.out.printf("%s %d", villainName, minion_count);
       }

       connection.close();

    }

}
