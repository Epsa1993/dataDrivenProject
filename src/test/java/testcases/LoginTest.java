package testcases;

import org.testng.annotations.DataProvider;
//import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.w2a.base.BaseTest;

//import testnglearning.ExcelReader;

public class LoginTest extends BaseTest {

	@Test(dataProvider="getData")
	public void doLogin(String username, String password) {
		type("user_xpath", username);
		type("password_xpath", username);
		click("login_xpath");
	}
	
	@DataProvider
	public Object[][] getData(){

		//com.w2a.utilities.ExcelReader excel = new com.w2a.utilities.ExcelReader("./src/test/resources/excel/logintest.xlsx");
		
		
		String sheetName = "doLogin";
		int rowNum = excel.getRowCount(sheetName);
		int colNum = excel.getColumnCount(sheetName);
		
		Object[][] data = new Object[rowNum-1][colNum];
		
		System.out.println(excel.getCellData(sheetName, 0, 2));
		
		System.out.println("Total rows are : "+rowNum+"  total cols are : "+colNum);
		
		for(int rows=2; rows<=rowNum; rows++) {
			
			for(int cols=0; cols<colNum; cols++) {
				
				
				//data[0][0] = excel.getCellData(sheetName, 0, 2);
				//data[0][1] = excel.getCellData(sheetName, 1, 2);
				
				data[rows-2][cols] = excel.getCellData(sheetName, cols, rows);
				
			}
			
		}
	
		
		return data;
		
	}
}
