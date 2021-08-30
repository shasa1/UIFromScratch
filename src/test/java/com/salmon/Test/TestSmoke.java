package com.salmon.Test;


import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestSmoke {

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
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
    }

    @Test
    public void verifyGooglePageTittle() {
        driver.navigate().to(appURL);
        String getTitle = driver.getTitle();
        Assert.assertEquals(getTitle, "Google");
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