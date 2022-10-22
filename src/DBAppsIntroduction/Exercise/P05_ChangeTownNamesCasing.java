package DBAppsIntroduction.Exercise;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class P05_ChangeTownNamesCasing {
    public static void main(String[] args) throws SQLException {

        DBConnector.createConnection("minions_db");
        Connection connection = DBConnector.getConnection();

        List<String> towns = new ArrayList<>();

        String countryName = new Scanner(System.in).nextLine();

        int updatedTownSet = updateTownNames(connection, countryName);
        if (updatedTownSet == 0) {
            System.out.println(ConstantMessages.NO_TOWNS_AFFECTED);
            return;
        }
        System.out.printf(ConstantMessages.NUMBER_OF_TOWNS_AFFECTED, updatedTownSet);

        PreparedStatement townStatement = connection.prepareStatement(Queries.SELECT_TOWN_NAMES_BY_COUNTRY);
        townStatement.setString(1, countryName);
        ResultSet townSet = townStatement.executeQuery();


        while (townSet.next()) {
            towns.add(townSet.getString("name"));
        }
        System.out.println(towns);

        connection.close();
    }

    private static int updateTownNames(Connection connection, String countryName) throws SQLException {
        PreparedStatement updatedTownsStatement = connection.prepareStatement(Queries.UPDATE_TOWN_NAMES_UPPER_CASING);
        updatedTownsStatement.setString(1, countryName);

        return updatedTownsStatement.executeUpdate();
    }

}
