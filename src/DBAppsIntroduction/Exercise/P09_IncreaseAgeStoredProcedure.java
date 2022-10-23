package DBAppsIntroduction.Exercise;

import java.sql.*;
import java.util.Scanner;

public class P09_IncreaseAgeStoredProcedure {
    public static void main(String[] args) throws SQLException {
        DBConnector.createConnection("minions_db");
        Connection connection = DBConnector.getConnection();

        int minionId = Integer.parseInt(new Scanner(System.in).nextLine());

        CallableStatement callableStatement = connection.prepareCall("CALL usp_get_older(?)");
        callableStatement.setInt(1, minionId);
        callableStatement.executeUpdate();

        try {
            PreparedStatement minionInfo = connection.prepareStatement(Queries.SELECT_MINION_NAME_AND_AGE);
            ResultSet minionSet = minionInfo.executeQuery();

            minionSet.next();
            String minionName = minionSet.getString("name");
            int minionAge = minionSet.getInt("age");

            System.out.printf(ConstantMessages.MINION_NAME_AND_AGE, minionName, minionAge);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection.close();

    }

}
