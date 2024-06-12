package searchTshirt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import commonUtils.RequireUtils;
import jdbc.*;

public class FetchData extends Thread {
	public String Size = "";
	public String Gender = "";
	public String colour = "";
	public String output = "";
	public int resultSize = 0;

	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static ResultSet rs = null;

	Scanner sc = new Scanner(System.in);

	public void getTshirtColour() {

		System.out.print("Please enter the colour of Tshirt\n");
		colour = sc.next().trim();
		
		String namePattern = "[^\\p{P}|^\\d+]+";
		boolean result = colour.matches(namePattern);
		if(result) {
			getTshirtSize();
		}else {
			System.out.print(ANSI_RED + "Please enter a valid input string!\n" + ANSI_RESET);
			getTshirtColour();
		}
	}

	public void getTshirtSize() {
		
		int countSize = 0;

		rs = JDBCSelect.returnColumns("SELECT DISTINCT SIZE FROM clothes;");
		System.out.print("Please enter the size of Tshirt: ");
		try {
			while (rs.next()) {
				System.out.print(rs.getString("Size") + ", ");
			}

			System.out.print("or enter 'E' to exit\r\n");
			Size = sc.next().trim();

			rs = JDBCSelect.returnColumns(
					"SELECT * FROM clothes WHERE Size=" + "'" + Size.replaceAll("[^a-zA-Z0-9]", " ") + "';");
			while (rs.next()) {
				countSize++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (countSize > 0) {
			getGender();
		} else if (Size.equalsIgnoreCase("e")) {
			System.out.println(ANSI_GREEN + "Thanks for the shopping, Please visit again!\n" + ANSI_RESET);
			System.exit(0);
			RequireUtils.closeConnection();
		} else {
			System.out.print(ANSI_RED + "This " + Size
					+ " size don't exist, Please enter a valid size from the given options!\n" + ANSI_RESET);
			getTshirtSize();
		}
	}

	public void getGender() {
		int countGender = 0;
		rs = JDBCSelect.returnColumns("SELECT DISTINCT Gender_Recommendation FROM clothes;");
		System.out.print("Please enter the gender for Tshirt: ");
		try {
			while (rs.next()) {
				System.out.print(rs.getString("Gender_Recommendation") + ", ");
			}

			System.out.print(" i.e. M for Male, F for Female, U for Unisex or enter 'E' to exit\r\n");
			Gender = sc.next().trim();

			rs = JDBCSelect.returnColumns("SELECT * FROM clothes WHERE Gender_Recommendation=" + "'"
					+ Gender.replaceAll("[^a-zA-Z0-9]", " ") + "';");
			while (rs.next()) {
				countGender++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (countGender > 0) {
			getOutPutPreference();
		} else if (Gender.equalsIgnoreCase("e")) {
			System.out.println(ANSI_GREEN + "Thanks for the shopping, Please visit again!\n" + ANSI_RESET);
			System.exit(0);
			RequireUtils.closeConnection();
		} else {
			System.out.print(ANSI_RED + "This " + Size
					+ " size don't exist, Please enter a valid size from the given options!\n" + ANSI_RESET);
			getGender();
		}

	}

	public void getOutPutPreference() {
		
		String query = "";
		
		System.out.print(
				"Please enter the output preference: Price, Rating, Both i.e. (Price and Rating) or Enter 'E' to close the search\n");

		String output = sc.next().replaceAll("[^a-zA-Z0-9]", " ");

		if (output.equalsIgnoreCase("Price") || output.equalsIgnoreCase("Rating") || output.equalsIgnoreCase("Both")) {
			if (output.equalsIgnoreCase("Price")) {
				query = "Select * from clothes where COLOUR=" + "'" + colour + "'" + " AND SIZE=" + "'" + Size
						+ "'" + " AND GENDER_RECOMMENDATION=" + "'" + Gender + "'" + " ORDER BY cast(Price as SIGNED);";
			} else if (output.equalsIgnoreCase("Rating")) {
				query = "Select * from clothes where COLOUR=" + "'" + colour + "'" + " AND SIZE=" + "'" + Size
						+ "'" + " AND GENDER_RECOMMENDATION=" + "'" + Gender + "'"
						+ " ORDER BY cast(Rating as SIGNED);";	
			} else {
				query = "Select * from clothes where COLOUR=" + "'" + colour + "'" + " AND SIZE=" + "'" + Size
						+ "'" + " AND GENDER_RECOMMENDATION=" + "'" + Gender + "'"
						+ " ORDER BY cast(Price as SIGNED),Rating;";
			}
			
			rs = JDBCSelect.returnColumns(query);

			try {
				if (rs.isBeforeFirst()) {
					JDBCSelect.readDataFromDB(rs);
					selectCondition();
				} else {
					System.out.println(ANSI_YELLOW+"Sorry!, No data found for the given inputs"+ANSI_RESET);
					selectCondition();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (output.equalsIgnoreCase("e")) {
			System.out.println(ANSI_GREEN + "Thanks for the shopping, Please visit again!\n" + ANSI_RESET);
			System.exit(0);
			RequireUtils.closeConnection();
		} else {
			System.out.println(ANSI_RED + "Please enter a valid output preference!\n" + ANSI_RESET);
			getOutPutPreference();
		}
	}

	public void selectCondition() {
		System.out.println("Please enter: C to Continue the search or E to Exit the Search\n");
		String result = sc.next();
		if (result.equalsIgnoreCase("c")) {
			getTshirtColour();
		} else if (result.equalsIgnoreCase("e")) {
			System.out.println(ANSI_GREEN + "Thanks for the shopping, Please visit again!\n" + ANSI_RESET);
			System.exit(0);
			RequireUtils.closeConnection();
		} else {
			System.out.println(ANSI_RED + "Please enter a valid output preference!e" + ANSI_RESET);
			selectCondition();
		}
	}
}
