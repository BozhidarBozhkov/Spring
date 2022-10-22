package DBAppsIntroduction.Exercise;

import java.sql.*;


public class P02_GetVillainsNames {
    public static void main(String[] args) throws SQLException {

        DBConnector.createConnection("minions_db");
        Connection connection = DBConnector.getConnection();

        PreparedStatement query = connection.prepareStatement(Queries.SELECT_ALL_VILLAIN_NAMES_AND_MINION_COUNT);

        ResultSet resultSet = query.executeQuery();
        while (resultSet.next()) {
            String villainName = resultSet.getString("name");
            int minion_count = resultSet.getInt("number_of_minions");
            System.out.printf("%s %d", villainName, minion_count);
        }

        connection.close();

    }

}
