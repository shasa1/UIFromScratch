package com.salmon.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;



public class TestGoogleSearch {

	WebDriver driver;
	 String a;
	List<String> s = new ArrayList<String>();
	 
	@BeforeTest
	public void setUp(){
		
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://www.google.co.in");				
	}
	
	@AfterTest
	public void destroy(){
		
	}
	
	@Test
	public void testResult() throws Exception{
		
		
		driver.findElement(By.className("gsfi")).sendKeys("Testing");
		
		driver.findElement(By.xpath("//button[@class='lsb']")).click();
		
		List <WebElement> alllinks = driver.findElements(By.xpath("//div[@class='rc']/h3"));

	    for(int i=0;i<alllinks.size();i++){
	       a = alllinks.get(i).getText();
	       s.add(a);
	     }
	    
	    for (int i = 0; i <s.size(); i++) {
			System.out.println("the links text is: "+s.get(i));
		}
	
	
	    for(int i=0;i<s.size();i++){
	       
	    	WebElement link = driver.findElement(By.partialLinkText(""+s.get(i)));
	    	link.click();
	    	Thread.sleep(3000);
	        driver.navigate().back();
	        Thread.sleep(2000);
	    }
		
		
		
		
	}
}
