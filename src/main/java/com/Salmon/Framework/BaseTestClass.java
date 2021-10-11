package com.Salmon.Framework;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTestClass {

	public static WebDriver driver;

	private static WebDriver getDriver( ) {
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("browser.download.folderList", 2);
		profile.setPreference("browser.download.manager.showWhenStarting", false);
		profile.setPreference("browser.download.dir","D:\\folder\\workspace\\Demo Test Fire\\downloads");
		profile.setPreference( "pdfjs.disabled", true );
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/csv");
		if (driver == null)
			driver = new FirefoxDriver((Capabilities) profile);

		return driver;
	}

	public void OpenPage(String urllink) {
		getDriver().get(urllink);
	}

	public static void initPageObject(Object page) {
		PageFactory.initElements(getDriver(), page);
	}
	@BeforeMethod
	public void init(){
		getDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@AfterMethod
	public void destroy(){
		driver.close();
		driver.quit();

	}
}
