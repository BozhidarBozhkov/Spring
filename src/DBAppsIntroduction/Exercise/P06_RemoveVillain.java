package DBAppsIntroduction.Exercise;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class P06_RemoveVillain {
    public static void main(String[] args) throws SQLException {

        DBConnector.createConnection("minions_db");
        Connection connection = DBConnector.getConnection();

        int villainId = Integer.parseInt(new Scanner(System.in).nextLine());

        PreparedStatement villainStatement = connection.prepareStatement(Queries.SELECT_VILLAIN_BY_NAME);
        villainStatement.setInt(1, villainId);
        ResultSet villainSet = villainStatement.executeQuery();

        if (!villainSet.next()) {
            System.out.println(ConstantMessages.VILLAIN_NOT_FOUND);
            return;
        }

        try {
            String villainName = villainSet.getString("name");

            PreparedStatement releasedMinions = connection.prepareStatement(Queries.RELEASED_MINIONS);
            releasedMinions.setInt(1, villainId);
            int countReleasedMinion = releasedMinions.executeUpdate();

            PreparedStatement removeVillainStatement = connection.prepareStatement(Queries.DELETE_VILLAIN);
            removeVillainStatement.setInt(1, villainId);
            removeVillainStatement.executeUpdate();


            System.out.printf(ConstantMessages.VILLAIN_DELETED, villainName);
            System.out.printf(ConstantMessages.MINION_COUNT_DELETED, countReleasedMinion);

        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }

        connection.close();

    }

}
