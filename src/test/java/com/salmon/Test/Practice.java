package com.salmon.Test;


import com.Salmon.Framework.BaseLoader;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Practice extends BaseLoaderTest {

    Practice() {
        super("taiwan");
    }

    private WebDriver driver;
    String appURL = "http://google.com";
    public Logger LOG         = null;
    static Date s_refDate;
    protected final String               LOG_FILE    = "salman.log";



    @BeforeSuite
    public void onTestStart(ITestContext context){

        /**
         * LOG4J setup
         */

        // generate log directory
        String username = System.getProperty( "USERNAME" );
        String m_logpath = "LogFiles" + File.separator + getReferenceTime("yyyyMM" + File.separator + "yyyyMMddHHmmss") + (StringUtils.isEmpty(username) ? "" : "_" + username) + File.separator + context.getName() + File.separator;

        // create log directory
        try
        {
            new File( m_logpath ).mkdirs();
        }
        catch( NullPointerException e )
        {
            throw e;
        }

        // initialize log4j
        System.setProperty( "LOG_PATH", m_logpath + LOG_FILE );
        PropertyConfigurator.configure( "src/main/resources/Properties/log4j.properties" );

        // initialize global logger
        LOG = Logger.getLogger( this.getClass() + m_logpath + LOG_FILE );

        String message ="@BeforeSuite";
        int asterisk = (110 - message.length()) / 2;
        String msg = StringUtils.repeat( "*", asterisk ) + message + StringUtils.repeat( "*", asterisk + ( (110 - message.length()) % 2) );
        LOG.info( msg );
        LOG.info( "onStart ISuite: " + this.getClass().getName() );
        LOG.info( "log directory: " + m_logpath+ LOG_FILE );
    }

    @BeforeTest
    public void testSetUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/Drivers/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
       // options.addArguments("lang=zh-tw");
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
    }

    @Test(priority = 1)
    public void verifyGooglePageTittle() throws UnsupportedEncodingException, InterruptedException {
        driver.navigate().to("https://www.rakuten.com.tw/");
        Thread.sleep(500);
        driver.findElement(By.className("expand_content_title_close")).click();
        String getTitle = driver.getTitle();
        Assert.assertEquals(getTitle, BaseLoaderTest.getValueFromLocalization("Homepagetitle"));
        //driver.navigate().back();
        //driver.navigate().forward();
    }

    @Test(priority = 2,enabled = false)
    public void EnterText() {
        driver.findElement(By.xpath("//input[@name='q']")).sendKeys("Covid Cases in India");
        driver.findElement(By.xpath("//input[@name='q']")).sendKeys(Keys.ENTER);
    }

    @Test(priority = 3,enabled = false)
    public void verifySVGGraph() {
        List<WebElement> rects = driver.findElements(By.xpath("(//*[name()='svg' and @class='uch-psvg'])[1]//*[name()='rect']"));
        for (WebElement rect: rects) {
            Actions act = new Actions(driver);
            act.moveToElement(rect).perform();
            System.out.println(""+driver.findElement(By.xpath("//*[@class='ExnoTd']")).getText());

        }

    }

    @Test(priority = 4,enabled = false)
    public void verifySVGGraph2() {
         WebElement graphLocation = driver.findElement(By.xpath("(//*[name()='svg' and @class='uch-psvg'])[3]"));
        Actions act = new Actions(driver);

         System.out.println("Location: "+graphLocation.getRect());
        Rectangle r = graphLocation.getRect();
        //act.moveToElement(graphLocation,r.width,0).perform();
        int i =301;
        //Point p = graphLocation.getSize();
        while(true){
            System.out.println("Width: "+i);
            act.moveToElement(graphLocation,i,0).perform();
            String Datess = driver.findElement(By.xpath("//table[@class='F9Gkq']")).getText();
            System.out.println(""+Datess);
            if(Datess.contains("16 Jan"))break;

            i--;

        }


         //        for (WebElement rect: rects) {
//            Actions act = new Actions(driver);
//            act.moveToElement(rect).perform();
//            System.out.println(""+driver.findElement(By.xpath("//*[@class='ExnoTd']")).getText());
//
//        }

    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

    /**
     * With reference to a certain start time
     * @param format
     * @return Reference time based from input String format
     */
    public static String getReferenceTime( String format )
    {
        return getReferenceTime( new SimpleDateFormat( format ) );
    }

    /**
     * With reference to a certain start time
     * @param format
     * @return Reference time based from input SimpleDateFormat format
     */
    public static String getReferenceTime( SimpleDateFormat format )
    {
        Date s_date    = new Date();
        s_refDate = s_date;
        return getTime( s_date, format );
    }

    public static String getTime( Date date, SimpleDateFormat format )
    {
        return format.format( date );
    }

}