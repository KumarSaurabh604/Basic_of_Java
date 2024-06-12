package readExcel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdbc.JDBCInsert;

public class ReadFromExcel extends Thread{

	public void run() {
		while (true) {
			String fileLocation = ".\\ExcelFiles";
			ReadFromExcel read = new ReadFromExcel();
			List<String> account_files = new ArrayList<String>();
			File[] files = new File(System.getProperty("user.dir") + fileLocation).listFiles();
			try {
				for (File file : files) {
					if (file.isFile()) {
						account_files.add(file.getName());
					}
				}
				for (int i = 0; i < account_files.size(); i++) {

					read.readCSVUsingBuffer(fileLocation + "\\" + account_files.get(i));

				}
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void readCSVUsingBuffer(String file) throws ClassNotFoundException, SQLException {
		BufferedReader reader = null;
		String line = "";
		int count = 1;

		try {
			reader = new BufferedReader(new FileReader(file));
			while ((line = reader.readLine()) != null) {
				if (count != 1) {
					String[] strArray = null;
					strArray = line.split("\\|");
					// printing the converted string array
					JDBCInsert.insertDataInDB(strArray[0], strArray[1], strArray[2], strArray[3], strArray[4],
							strArray[5], strArray[6], strArray[7]);
				}
				count = 2;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
