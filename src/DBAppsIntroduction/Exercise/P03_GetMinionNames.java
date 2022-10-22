package DBAppsIntroduction.Exercise;

import java.sql.*;
import java.util.Scanner;

public class P03_GetMinionNames {
    public static void main(String[] args) throws SQLException {

        DBConnector.createConnection("minions_db");
        Connection connection = DBConnector.getConnection();

        Scanner scanner = new Scanner(System.in);
        int villainId = Integer.parseInt(scanner.nextLine());

        PreparedStatement villainStatement = connection.prepareStatement(Queries.SELECT_VILLAIN_BY_NAME);
        villainStatement.setInt(1, villainId);

        ResultSet villainSet = villainStatement.executeQuery();

        if (!villainSet.next()) {
            System.out.printf(ConstantMessages.NO_SUCH_VILLAIN, villainId);
            return;
        }

        String villainName = villainSet.getString("name");
        System.out.println("Villain: " + villainName);

        PreparedStatement minionStatement = connection.prepareStatement(Queries.SELECT_MINIONS_BY_NAME_AND_AGE);
        minionStatement.setInt(1, villainId);

        ResultSet minionSet = minionStatement.executeQuery();

        for (int i = 1; minionSet.next(); i++) {
            String name = minionSet.getString("name");
            int age = minionSet.getInt("age");

            System.out.printf("%d. %s %d%n", i, name, age);
        }
    }
}
