package DBAppsIntroduction.Exercise;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class P07_PrintAllMinionNames {
    public static void main(String[] args) throws SQLException {
        DBConnector.createConnection("minions_db");
        Connection connection = DBConnector.getConnection();

        PreparedStatement minionStatement = connection.prepareStatement(Queries.SELECT_MINIONS_BY_NAME);
        ResultSet minionSet = minionStatement.executeQuery();

        List<String> minions = new ArrayList<>();
        while (minionSet.next()) {
            minions.add(minionSet.getString("name"));
        }
        int start = 0;
        int end = minions.size() - 1;
        for (int i = 0; i < minions.size(); i++) {
            System.out.println(i % 2 == 0 ? minions.get(start++) : minions.get(end--));

        }
        connection.close();
    }

}
