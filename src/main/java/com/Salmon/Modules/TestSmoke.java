package com.Salmon.Modules;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestSmoke {

    private WebDriver driver;
    String appURL = "http://google.com";

    @BeforeClass
    public void testSetUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/Drivers/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
    }

    @Test
    public void verifyGooglePageTittle() {
        driver.navigate().to(appURL);
        String getTitle = driver.getTitle();
        Assert.assertEquals(getTitle, "Google");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

}