package DBAppsIntroduction.Exercise;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class P08_IncreaseMinionsAge {
    public static void main(String[] args) throws SQLException {
        DBConnector.createConnection("minions_db");
        Connection connection = DBConnector.getConnection();

        List<String> minionIds = Arrays.stream(new Scanner(System.in).nextLine().split(" ")).collect(Collectors.toList());

        PreparedStatement minionUpdate = connection.prepareStatement(String.format(Queries.UPDATE_MINION_AGE_AND_NAME,
                minionIds.stream().map(m -> "?").collect(Collectors.joining(", "))));

        for (int i = 1; i <= minionIds.size(); i++) {
            minionUpdate.setInt(i, Integer.parseInt(minionIds.get(i - 1)));
        }
        minionUpdate.executeUpdate();

        PreparedStatement minionInfo = connection.prepareStatement(Queries.SELECT_MINION_NAME_AND_AGE);
        ResultSet minionSet = minionInfo.executeQuery();

        while (minionSet.next()) {
            String minionName = minionSet.getString("name");
            int minionAge = minionSet.getInt("age");

            System.out.printf(ConstantMessages.MINION_NAME_AND_AGE, minionName, minionAge);
        }
        connection.close();
    }

}
