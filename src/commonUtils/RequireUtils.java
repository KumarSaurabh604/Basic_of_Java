package commonUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class RequireUtils {

	private static final String DB = "branddetails";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";
	static Statement stmt = null;
	static ResultSet rs = null;


	public static PreparedStatement preparedStatement = null;
	public static Connection Con = null;
	

	public static void createConnection() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DB, USERNAME, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void closeConnection() {
		
		try {
			rs.close();
			stmt.close();
			preparedStatement.close();
			Con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String randomVinGenarator() {
		Random objGenerator = new Random();
		int randomNumber = objGenerator.nextInt(1000, 9999);
		System.out.println("Random No : " + randomNumber);
		return String.valueOf(randomNumber);

	}

}
