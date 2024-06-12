package FinalAppication;

import commonUtils.RequireUtils;
import readExcel.*;
import searchTshirt.*;

public class UserApplication {

	public static void main(String[] args){
		
		ReadFromExcel readExcel = new ReadFromExcel();
		FetchData dataSearch = new FetchData();
		
		RequireUtils.createConnection();
		readExcel.start();//Thread
		dataSearch.getTshirtColour();//fetch the data according to users search and print the result
	}

}
