package com.Salmon.Modules;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Provider;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestSmoke {

    private WebDriver driver;
    String appURL = "http://google.com";
    public Logger LOG         = null;
    static Date s_refDate;
    protected final String               LOG_FILE    = "console.log";
    int total=0;
    int fail=0;
    protected String                     m_logpath;

    public Boolean tearDownPass = true;

    @BeforeSuite
    public void onTestStart(ITestContext context){

        /**
         * LOG4J setup
         */

        // generate log directory
        String username = System.getProperty("USERNAME");
         m_logpath = "LogFiles" + File.separator + getReferenceTime("yyyyMM" + File.separator + "yyyyMMddHHmmss") + (StringUtils.isEmpty(username) ? "" : "_" + username) + File.separator + context.getName() + File.separator;

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
    @Parameters({"browser"})
    public void testSetUp(@Optional("Chrome") String browser) throws MalformedURLException {
        if(!browser.equalsIgnoreCase("Remote")) {
            System.setProperty("webdriver.chrome.driver", "src/main/resources/Drivers/chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("start-maximized");
            driver = new ChromeDriver(options);
        }else{
            ChromeOptions options2 = new ChromeOptions();
            //FirefoxOptions options2 = new FirefoxOptions();

            options2.addArguments("--disable-dev-shm-usage");
            options2.addArguments("--no-sandbox");
            options2.addArguments("start-maximized");

            //c.setPlatform(Platform.WINDOWS);
            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options2);
        }
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
    @AfterSuite
    public void onSuiteEnd(){
        onSuiteEndMethod();
    }

    @AfterMethod()
    public void afterMethod(ITestResult result) throws Exception {
        afterMet(result);
    }

    public void onSuiteEndMethod(){
        String message ="@AfterSuite";
        int asterisk = (110 - message.length()) / 2;
        String msg = StringUtils.repeat( "*", asterisk ) + message + StringUtils.repeat( "*", asterisk + ( (110 - message.length()) % 2) );
        LOG.info( msg );
        LOG.info( "onExecutionFinish" );
        LOG.info( "Total Test Cases executed :" +total);
        LOG.info( "Total Test Cases PASS :" +(total-fail));
        LOG.info( "Total Test Cases FAILED :" +fail);
        LOG.info( "Suite Finished : " + getTime(new SimpleDateFormat("yyyyMMddHHmmss") ) );
        LOG.info( "Suite Execution Duration : " + DurationFormatUtils.formatDuration( new Date().getTime() - s_refDate.getTime(), "HH:mm:ss:SS" ) );

        LOG.info( "Please check console log file at " + m_logpath + LOG_FILE );
        LOG.info( "Please check HTML file at " + m_logpath + LOG_FILE );
        LOG.info("");

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

    public static String getTime( SimpleDateFormat format )
    {
        return format.format( new Date() );
    }

    public void afterMet(ITestResult result) throws Exception {

        byte[] capturePage = null;

        if (!result.isSuccess()) {

            System.out.println("***** Error " + result.getName()
                    + " test has failed *****");
            String methodName = result.getName().toString().trim();
            fail+=1;

            // screenRecorder.stop();
            if (!driver.toString().contains("(null)")) {
                System.out.println("the driver is " + driver.toString());
//                if (isScreenshotEnabled == true) {
//                    Library.takeScreenShot(driver, methodName, folderLocation);
//                    capturePage = ((TakesScreenshot) driver)
//                            .getScreenshotAs(OutputType.BYTES);
//                }
            }
            // if one test fails, mark tearDown to fail as well (this is for
            // test case rerun)
            tearDownPass = false;
        }

        String message ="@AfterTest";
        String pass="PASS";
        String fail="FAIL";
        int val=1;
        int asterisk = (100 - message.length()) / 2;
        String msg = StringUtils.repeat( "*", asterisk ) + message + StringUtils.repeat( "*", asterisk + ( (100 - message.length()) % 2) );
        total+=1;
        //LOG.info( "OnFinish Test " + result.getName() + ":");
        LOG.info("OnFinish Test: "+ result.getName() + ": \t" +((result.getStatus()==1 )? pass : fail ));
        LOG.info( msg );
       // updateTestRail(result);
    }
}