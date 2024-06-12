package jdbc;

import java.sql.*;
import java.util.Formatter;

import commonUtils.*;

public class JDBCSelect {

	static Statement stmt = null;
	static ResultSet rs = null;

	static Formatter fmt = new Formatter();

	public static final String ANSI_CAYN = "\u001B[36m";
	public static final String ANSI_RESET = "\u001B[0m";



	public static void readDataFromDB(ResultSet rs) {
		try {
			
			System.out.format("\n%7s %35s %27s %8s %6s %9s %9s %10s\n", "ID", "NAME", "COLOUR", "GENDER", "SIZE",
					"PRICE", "RATING", "AVAILIBILITY");
			
			while (rs.next()) {
				String id = rs.getString("ID");
				String Name = rs.getString("NAME");
				String Colour = rs.getString("Colour");
				String Gender_Recommendation = rs.getString("Gender_Recommendation");
				String Size = rs.getString("Size");
				String Price = rs.getString("Price");
				String Rating = rs.getString("Rating");
				String Availability = rs.getString("Availability");

				System.out.format(ANSI_CAYN + "%15s %45s %9s %5s %7s %12s %6s %10s\n", id, Name, Colour,
						Gender_Recommendation, Size, Price, Rating, Availability + ANSI_RESET);
			}
			System.out.println("\n");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public static ResultSet returnColumns(String query) {
		try {
			stmt = RequireUtils.Con.createStatement();
			rs = stmt.executeQuery(query);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
}

