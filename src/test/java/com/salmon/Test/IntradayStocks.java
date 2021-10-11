package com.salmon.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.Salmon.Framework.BaseTestClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class IntradayStocks extends BaseTestClass {

	PrintWriter pw;
	StringBuilder sb;
	List<HashMap<String, WebElement>> userTable;
	String StockNames[] = { "BHARTIARTL",
			"INFRATEL",
			"TATASTEEL",
			"BOSCHLTD",
			"ULTRACEMCO",
			"KOTAKBANK",
			"HDFCBANK",
			"ASIANPAINT",
			"POWERGRID",
			"HCLTECH",
			"ICICIBANK",
			"TATAMOTORS",
			"YESBANK",
			"COALINDIA",
			"ADANIPORTS",
			"HINDALCO",
			"IBULHSGFIN",
			"HINDUNILVR",
			"HINDPETRO",
			"VEDL",
			"CIPLA",
			"RELIANCE",
			"AXISBANK",
			"HDFC",
			"TCS",
			"HEROMOTOCO",
			"SBIN",
			"INDUSINDBK",
			"EICHERMOT",
			"INFY",
			"TECHM",
			"LUPIN",
			"BAJFINANCE",
			"ONGC",
			"BAJAJ-AUTO",
			"IOC",
			"NTPC",
			"AMBUJACEM",
			"ITC",
			"MARUTI",
			"LT",
			"WIPRO",
			"AUROPHARMA",
			"UPL",
			"M&M",
			"DRREDDY",
			"SUNPHARMA",
			"ZEEL",
			"BPCL",
			"GAIL" };
	String schema[] = { "Symbol", "LTP", "Open","Low","High","% Chng" };

	@BeforeTest
	public void testSetUp() {
		System.setProperty("webdriver.chrome.driver", "src/main/resources/Drivers/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		// options.addArguments("lang=zh-tw");
		options.addArguments("start-maximized");
		HashMap<String,Object> chromePreference = new HashMap<>();
		chromePreference.put("profile.default_content_settings.popups", 0);
		chromePreference.put("download.default_directory", "src/main/resources/downloads");
		chromePreference.put("download.prompt_for_download", false);
		options.setExperimentalOption("prefs",chromePreference);
//		profile.setPreference("browser.download.folderList", 2);
//		profile.setPreference("browser.download.manager.showWhenStarting", false);
//		profile.setPreference( "pdfjs.disabled", true );
//		profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/csv");
		driver = new ChromeDriver(options);
	}

	@Test
	public void testLogin() throws FileNotFoundException {

		// OpenPage("http://demo.testfire.net/bank/login.aspx");
		// LoginPage loginpage = new LoginPage();
		// initPageObject(loginpage);
		//
		// loginpage.doLogin(ConstantVariables.username,ConstantVariables.password);
		//
		// UserHomePage uhp = new UserHomePage();
		// initPageObject(uhp);
		//
		// Assert.assertEquals(uhp.getUsername(),ConstantVariables.Welcometxt);

		//
		
		driver.get("https://www.nseindia.com/live_market/dynaContent/live_watch/equities_stock_watch.htm?cat=N#");

		// simplified: find table which contains the keyword
		
		getRequiredDeatilsForStock(getCurrentNifty50Data(), StockNames, schema);

	
	//	writeDataToCSVFile();
		// finally fetch the desired data
//		WebElement cellInSecondRowFourthColumn1 = userTable.get(13).get("Symbol");
//		WebElement cellInSecondRowFourthColumn = userTable.get(13).get("Open");
//		WebElement cellInSecondRowFirstColumn = userTable.get(13).get("LTP");
//
//		cellInSecondRowFirstColumn.getText();
//		System.out.println("Symbol: " + cellInSecondRowFourthColumn1.getText());
//		System.out.println("Open Value: " + cellInSecondRowFourthColumn.getText());
//		System.out.println("Current Value: " + cellInSecondRowFirstColumn.getText());

		// driver.findElement(By.xpath("//input[contains(@onclick,'refresh')]")).click();
		//
		// try {Thread.sleep(5000);} catch (InterruptedException e){}
		//
		// getCurrentNifty50Data();

		// cellInSecondRowFirstColumn.getText();
		//
		// System.out.println("Symbol:
		// "+cellInSecondRowFourthColumn1.getText());
		// System.out.println("Open Value:
		// "+cellInSecondRowFourthColumn.getText());
		// System.out.println("Current Value:
		// "+cellInSecondRowFirstColumn.getText());
		//

	}

	private void writeDataToCSVFile() throws FileNotFoundException {
	    pw = new PrintWriter(new File("CSV/test1.csv"));
        sb = new StringBuilder();
        sb.append("Stock");
        sb.append(',');
        sb.append("Open");
        sb.append(',');
        sb.append("LTP");
        sb.append('\n');
        sb.append('\n');

        sb.append("ITC");
        sb.append(',');
        sb.append("223.03");
        sb.append(',');
        sb.append("243.03");
        sb.append('\n');
        sb.append("TCS");
        sb.append(',');
        sb.append("2231.03");
        sb.append(',');
        sb.append("2433.03");
        sb.append('\n');

        pw.write(sb.toString());
        pw.close();
        System.out.println("done!");
			
}

	private void getRequiredDeatilsForStock(List<HashMap<String, WebElement>> StockTable, String[] StockNames,
			String[] schema) throws FileNotFoundException {

		pw = new PrintWriter(new File("src/test/resources/CSV/test1.csv"));
		sb = new StringBuilder();
		
		for (int i = 0; i < schema.length; i++) {
			sb.append(schema[i]);
			sb.append(',');
		}
		
		sb.append('\n');
		sb.append('\n');
		
		for (int i = 0; i < StockTable.size(); i++) {
			HashMap<String, WebElement> temp = StockTable.get(i);
			
			for(Entry<String, WebElement> Map : temp.entrySet()){
				
				for (int j2 = 0; j2 < StockNames.length; j2++) {
					if(Map.getValue().getText().equals(StockNames[j2])){
						for (int k2 = 0; k2 < schema.length; k2++) {
							//System.out.println("" + schema[k2] + ": " + temp.get(schema[k2]).getText().replace("," , ""));
							sb.append(""+temp.get(schema[k2]).getText().replace("," , ""));
							sb.append(',');
						}
						System.out.println("Stock name: "+StockNames[j2]);
						sb.append('\n');
					}
				}
			}
		}
		
		 pw.write(sb.toString());
	     pw.close();
	}
			

	public List<HashMap<String, WebElement>> getCurrentNifty50Data() {
		WebElement tableElement = driver.findElement(By.cssSelector("#dataTable"));

		// create empty table object and iterate through all rows of the found
		// table element
		userTable = new ArrayList<HashMap<String, WebElement>>();
		List<WebElement> rowElements = tableElement.findElements(By.cssSelector("tbody>tr"));

		// get column names of table from table headers
		List<String> columnNames = new ArrayList<String>();
		List<WebElement> headerElements = rowElements.get(0).findElements(By.cssSelector("th"));
		for (WebElement headerElement : headerElements) {
			columnNames.add(headerElement.getText());
		}

		// iterate through all rows and add their content to table array
		for (WebElement rowElement : rowElements) {
			HashMap<String, WebElement> row = new HashMap<String, WebElement>();

			// add table cells to current row
			int columnIndex = 0;
			List<WebElement> cellElements = rowElement.findElements(By.cssSelector("td"));
			for (WebElement cellElement : cellElements) {
				row.put(columnNames.get(columnIndex), cellElement);
				columnIndex++;
			}

			userTable.add(row);
		}
	//	Collections.synchronizedList(userTable);
		return userTable;
	}
}
