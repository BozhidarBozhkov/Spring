package DBAppsIntroduction.Exercise;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class P04_AddMinion {

    public static void main(String[] args) throws SQLException {

        DBConnector.createConnection("minions_db");
        Connection connection = DBConnector.getConnection();

        Scanner scanner = new Scanner(System.in);
        String[] minionInfo = scanner.nextLine().split(" ");
        String minionName = minionInfo[1];
        int minionAge = Integer.parseInt(minionInfo[2]);
        String minionTown = minionInfo[3];

        String villainName = scanner.nextLine().split(" ")[1];

        PreparedStatement townStatement = connection.prepareStatement(Queries.GET_TOWN_BY_NAME);
        townStatement.setString(1, minionTown);
        ResultSet townSet = townStatement.executeQuery();

        if (!townSet.next()) {
            PreparedStatement insertTown = connection.prepareStatement(Queries.INSERT_TOWN);
            insertTown.setString(1, minionTown);
            insertTown.executeUpdate();
            System.out.printf(ConstantMessages.TOWN_ADDED, minionTown);
        }

        PreparedStatement villainStatement = connection.prepareStatement(Queries.GET_VILLAIN_BY_NAME);
        villainStatement.setString(1, villainName);
        ResultSet villainSet = villainStatement.executeQuery();

        if (!villainSet.next()) {
            PreparedStatement insertVillain = connection.prepareStatement(Queries.INSERT_INTO_VILLAINS);
            insertVillain.setString(1, villainName);
            insertVillain.executeUpdate();

            System.out.printf(ConstantMessages.VILLAIN_ADDED, villainName);
        }


        PreparedStatement minionStatement = connection.prepareStatement(Queries.INSERT_INTO_MINIONS);
        minionStatement.setString(1, minionName);
        minionStatement.setInt(2, minionAge);
        minionStatement.executeUpdate();

        System.out.printf(ConstantMessages.MINION_ADDED, minionName, villainName);

        connection.close();
    }

}
