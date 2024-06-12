package jdbc;

import java.sql.SQLException;

import commonUtils.RequireUtils;

public class JDBCInsert {

	public static void insertDataInDB(String id, String Name, String Colour, String Gender_Recommendation, String Size,
			String Price, String Rating, String Availability) {

		try {
			RequireUtils.preparedStatement = RequireUtils.Con.prepareStatement("insert into clothes values(?,?,?,?,?,?,?,?)"
					+ "ON DUPLICATE KEY UPDATE " + "id = VALUES(id), " + "name = VALUES(name)");
			RequireUtils.preparedStatement.setString(1, id);
			RequireUtils.preparedStatement.setString(2, Name);
			RequireUtils.preparedStatement.setString(3, Colour);
			RequireUtils.preparedStatement.setString(4, Gender_Recommendation);
			RequireUtils.preparedStatement.setString(5, Size);
			RequireUtils.preparedStatement.setString(6, Price);
			RequireUtils.preparedStatement.setString(7, Rating);
			RequireUtils.preparedStatement.setString(8, Availability);
			
			RequireUtils.preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


}
