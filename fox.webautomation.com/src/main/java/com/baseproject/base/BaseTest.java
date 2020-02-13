
/**
* This com.mmc.global package has a 'BaseTest' class for WebDriver utility  
 * @since 1.0
* @author U1196286
*/
package com.baseproject.base;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.impl.Log4JLogger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.baseproject.utility.Log;
import com.baseproject.webdriver.WebDriverFactory;

/**
* The class BaseTest contains Test utilities and extends WebDriverFactory
* class @BeforeSuite, @BeforeClass, @AfterSuite, TestNG Asserts(Hard, Soft),
* set/get login credentials
* 
 * @author 10647421
*
*/
public class BaseTest extends WebDriverFactory {

      /**
      * This function is to create folders (Logs, Screenshots, Reports) in Target
      * folder Also used to initialize Extent report
      */
      @BeforeSuite
      protected synchronized static void preinit() throws Exception {
            try {
                  DateTimeFormatter ft = DateTimeFormatter.ofPattern("MM.dd.yyyy.hh.mm.ss");
                  if (runTime == null) {
                        runTime = LocalDateTime.now().format(ft);
                  }

                  htmlReporter = new ExtentHtmlReporter(PROJECT_EXTENT_REPORT + "AutomationResults_" + runTime + ".html");
                  report = new ExtentReports();
                  report.attachReporter(htmlReporter);

                  createFolder("logs");
                  createFolder("screenshots");
                  createFolder("reports");
            } catch (WebDriverException e) {
                  Log.error("WebDriver exception occurred at preinit() ", e);
            } catch (Exception e) {
                  Log.error("Exception occurred at preinit()  ", e);
            }

      }

      /**
      * This function is to initialize Property file, initialize Excel, initialize
      * Webdriver
      * 
       * @param ctx : To capture suite name and test name from TestNG.xml(runner)
      * @throws Exception
      */
      @BeforeClass(alwaysRun = true)
      protected synchronized void init(ITestContext ctx) throws Exception {
            {
                  try {
                        setExtentReportThreadSafe();
                        htmlReporter.config().setDocumentTitle(getValueOrDefaultExtReportName(
                                    ctx.getCurrentXmlTest().getSuite().getName(), this.getClass().getSimpleName()));

                  htmlReporter.config().setReportName(getValueOrDefaultExtReportName(ctx.getCurrentXmlTest().getName(),
                                    this.getClass().getSimpleName()));

                        htmlReporter.config().setTheme(Theme.STANDARD);
                        initProperties();
                        initExcelConnection();
                        intiDriver();

                        report.setSystemInfo("OS Name :", System.getProperty("os.name"));
                        //report.setSystemInfo("Browser Name :", ieOptions.getBrowserName().toUpperCase());
                        report.setSystemInfo("Browser Name :", options.getBrowserName().toUpperCase());
                        report.setSystemInfo("User Name :", System.getProperty("user.name"));
                        report.setSystemInfo("Java Version :", System.getProperty("java.version"));
                        report.setSystemInfo("Machine Name :", InetAddress.getLocalHost().getHostName());
                        report.setSystemInfo("IP Address :", InetAddress.getLocalHost().getHostAddress());

                        Log.info("-----------Test Class [" + this.getClass().getSimpleName()
                                    + "] Execution Started-------------");
                  } catch (WebDriverException e) {
                        Log.error("WebDriver exception occurred at init() ", e);
                  } catch (Exception e) {
                        Log.error("Exception occurred at init()  ", e);
                  }
            }
      }

      /**
      * TestNG Before method is for setting up Extent tests
      * 
       * @param m : Object of Method(reflection) to capture @Test for Extent report
      * 
       */
      @BeforeMethod(alwaysRun = true)
      protected synchronized static void extent(Method m) {
            {
                  try {
                        setExtentTestThreadSafe(
                                    getExtentReportThreadSafe().createTest(m.getName(), m.getAnnotation(Test.class).description()));

                        Log.info("-----------Test Method [" + m.getName() + "] Execution Started-------------");
                  }

                  catch (Exception e) {
                        Log.error("Exception occurred at extent()  ", e);
                  }

            }
      }

      /**
      * TestNG After method for logging and screenshot capture Extent Reports
      * 
       * @param result : ITestResult for Extent Result capture
      * @param m      : Method object to capture @Test for Extent reports
      * @throws IOException
      */
      @AfterMethod(alwaysRun = true)
      protected synchronized static void getResult(ITestResult result, Method m) throws IOException {
            Log.info("-----------Test Method [" + m.getName() + "] Execution Completed-------------");

            if ((Arrays.asList(result.getParameters()).size() > 0)
                        && !(Arrays.asList(result.getParameters()).get(0) == null)) {
                  try {

                        getExtentTestThreadSafe().log(Status.INFO, Arrays.asList(result.getParameters()).stream()
                                    .map(p -> p.toString()).collect(Collectors.joining(", ")));
                  } catch (Exception e) {
                        e.printStackTrace();
                  }
            } else {
                  getExtentTestThreadSafe().log(Status.INFO, "This test doesn't have data");
            }
            Reporter.getOutput(result).forEach(info -> getExtentTestThreadSafe().log(Status.INFO, info));

            if (result.getStatus() == ITestResult.FAILURE) {

                  if (!Boolean.parseBoolean(capture(m))) {
                        getExtentTestThreadSafe().log(Status.FAIL, "Test Case Failed is " + m.getName());
                        getExtentTestThreadSafe().log(Status.FAIL, result.getThrowable());

                        getExtentTestThreadSafe().fail("", MediaEntityBuilder.createScreenCaptureFromPath(capture(m)).build());
                  } else {
                        getExtentTestThreadSafe().log(Status.FAIL, "Test Case Failed is " + m.getName());
                        getExtentTestThreadSafe().log(Status.FAIL, result.getThrowable());
                  }

            } else if (result.getStatus() == ITestResult.SKIP) {
                  getExtentTestThreadSafe().log(Status.SKIP, "Test Case Skipped is " + m.getName());
            } else if (result.getStatus() == ITestResult.SUCCESS) {
                  getExtentTestThreadSafe().log(Status.PASS, "Test Case Passed is " + m.getName());
            } else {
                  getExtentTestThreadSafe().log(Status.SKIP, "Test Case Skipped is " + m.getName());

            }

      }

      /**
      * This function captures screen shot used for extent report. Extension
      * of @After method
      * 
       * @param m : Reflection Method to capture Method name
      * @return
      * @throws IOException
      */
      protected synchronized static String capture(Method m) throws IOException {
            try {
                  File Dest = new File(PATH_SCREENSHOTS + "Failure screen shot of " + m.getName() + "_"
                              + LocalDateTime.now().format(dateTimeFormatter) + ".png");
                  FileUtils.copyFile(((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.FILE), Dest);
                  return Dest.getAbsolutePath();
            } catch (Exception e) {
                  Log.error("Failed to capture screenshot " + e.getMessage());
                  return "true";
            }
      }

      /**
      * This function is to getUsername through system.property or system.environment
      * 
       * @return string value
      */
      protected synchronized static String getUserName() {
            try {
                  return System.getProperty("username") == null ? System.getenv("username") : System.getProperty("username");
            } catch (Exception e) {
                  log.error("Username needs to be passed as a paramter - system.getProperty or getenv.getProperty");
                  throw e;
            }

      }

      /**
      * This function is to getPassword through system.property or system.environment
      * 
       * @return string value
      */
      protected synchronized static String getPassword() {
            try {
                  return System.getProperty("password") == null ? System.getenv("password") : System.getProperty("password");
            } catch (Exception e) {
                  log.error("Password needs to be passed as a paramter - system.getProperty or getenv.getProperty");
                  throw e;
            }
      }

      protected static synchronized String getURL(String propertyValue) {
            try {
                  return System.getProperty("env") == null ? getPropertyValue(propertyValue) : getPropertyValue(System.getProperty("env"));
            } catch (Exception e) {
                  log.error("URL needs to be passed as a paramter - system.getProperty or config.getProperty");
                  throw e;
            }
      }

      /**
      * This function is to assertEquals using TestNG Assert
      * 
       * @param actual      : actual value captured on the Page
      * @param description : Description For the logger
      */
      protected synchronized static <T extends Comparable<T>> void assertEquals(T actual, T expected,
                  String description) {
            try {
                  Log.info("[assertEquals]: " + description);
                  Assert.assertEquals(actual, expected);
                  Reporter.log("[assertEquals]: Expected [" + expected.toString() + "] Actual [" + actual.toString() + "]");
                  Log.info("[assertEquals]: Expected [" + expected.toString() + "] Actual [" + actual.toString() + "]");


            } catch (AssertionError e) {
            		Reporter.log("Assertion failed: expected [" + expected.toString() + "] but found [" + actual.toString() + "]");
            		Log.error("Assertion failed: expected [" + expected.toString() + "] but found [" + actual.toString() + "]");
                  throw e;
            }
      }

      /**
      * This function is to assertFalse using TestNG Assert
      * 
       * @param condition   : <b>True</b> or <b>False</b> conditions
      * @param description : Description For the logger
      */
      protected synchronized static void assertFalse(boolean condition, String description) {
            try {
                  Log.info("[assertFalse]: " + description);
                  Assert.assertFalse(condition);
            } catch (AssertionError e) {
                  Log.error("Assertion failed: expected [false] but found [true]");
                  throw e;
            }
      }

      /**
      * This function is to assertTrue using TestNG Assert
      * 
       * @param condition   : <b>True</b> or <b>False</b> conditions
      * @param description : Description For the logger
      * @param softAssert  : Object specific to @test
      */
      protected synchronized static void assertTrue(boolean condition, String description) {
            try {
                  Log.info("[assertTrue]: " + description);
                  Assert.assertTrue(condition, description);
            } catch (AssertionError e) {
                  Log.error("Assertion failed: expected [true] but found [false]");
                  throw e;
            }
      }

      /**
      * This function is to assertEquals using TestNG SoftAssert
      * 
       * @param actual      : actual value captured on the Page
      * @param expected    : expected value captured from TestData
      * @param description : Description For the logger
      * @param softAssert  : Object specific to @test
      */
      protected synchronized static <T extends Comparable<T>> void assertSoftEquals(T actual, T expected,
                  String description, SoftAssert softAssert) {

            try {
                  Log.info("[assertSoftEquals]:" + description);
                  Log.info("[assertEquals]: Expected [" + expected.toString() + "] Actual [" + actual.toString() + "]");

                  if (!actual.equals(expected)) {
                        Log.error("Assertion failed: expected [" + expected.toString() + "] but found [" + actual.toString()
                                    + "]");
                        screenshot("Failure");
                  }
                  softAssert.assertEquals(actual, expected, description);

            } catch (Exception e) {
                  Log.error("Exception occurred @assertSoftEquals: " + e.getMessage());
            }
      }

      /**
      * This function is to assertAll using TestNG SoftAssert
      * 
       * @param softAssert : Object specific to @test
      */
      protected synchronized static void assertSoftAll(SoftAssert softAssert) {
            try {
                  Log.info("[assert All]:");
                  softAssert.assertAll();
            } catch (AssertionError e) {
                  Log.error("Assertion exception occurred @assertSoftAll:" + e.getMessage());
                  throw e;
            } catch (Exception e) {
                  Log.error("Exception occurred @assertSoftAll: " + e.getMessage());
                  throw e;
            }
      }

      /**
      * This function is to assertTrue using TestNG SoftAssert
      * 
       * @param condition   : <b>True</b> or <b>False</b> conditions
      * @param description : Description For the logger
      * @param softAssert  : Object specific to @test
      */
      protected synchronized static void assertSoftTrue(boolean condition, String description, SoftAssert softAssert) {
            try {
                  Log.info("[SoftAssertTrue]: " + description);
                  if (condition == false) {
                        Log.error("Soft Assertion failed: expected [true] but found [false]");
                        screenshot("Failure");
                  }
                  softAssert.assertTrue(condition, description);
            } catch (Exception e) {
                  Log.error("Exception occurred @assertSoftTrue: " + e.getMessage());
            }
      }

      /**
      * This function is to assertFalse using TestNG SoftAssert
      * 
       * @param condition   : <b>True</b> or <b>False</b> conditions
      * @param description : Description For the logger
      * @param softAssert  : Object specific to @test
      */
      protected synchronized static void assertSoftFalse(boolean condition, String description, SoftAssert softAssert) {
            try {
                  Log.info("[SoftAssertFalse]: " + description);
                  if (condition == true) {
                        Log.error("Soft Assertion failed: expected [true] but found [false]");
                        screenshot("Failure");
                  }
                  softAssert.assertFalse(condition, description);
            } catch (Exception e) {
                  Log.error("Exception occurred @assertSoftFalse: " + e.getMessage());
            }

      }

      /**
      * This function is to verify tabs on a page
      * 
       * @param expected : expected List of expected values of Page tabs
      * @param actual   : actual List of actual values of Page tabs
      */
      protected synchronized static void verifyColumnsButtonsTabs(List<String> expected, List<String> actual) {
            try {
                  expected.forEach(e -> {
                        String actualString = actual.stream().filter(a -> a.equals(e)).findFirst().orElse("Not Present");
                        assertEquals(actualString, e, "Check whether [" + e + "] is present");
                  });
            } catch (Exception e) {
                  Log.error("Exception occurred @verifyColumnsButtonsTabs: " + e.getMessage());
            }
      }

      /**
      * This function is close WebDriver, flush extent report and Excel connection
      */
      @AfterClass(alwaysRun = true)
      protected synchronized void tearDown() {
            try {
                  Log.info("-----------Test Class [" + this.getClass().getSimpleName() + "] Execution Completed-----------");
                  // getExtentReportThreadSafe().removeTest(getExtentTestThreadSafe());
                  getExtentReportThreadSafe().flush();
                  closeExcelConnection();
                  if (getWebDriver() != null) {
                        getWebDriver().close();
                        getWebDriver().quit();
                  }
            } catch (Exception e) {
                  Log.error("Exception occurred @TearDown: " + e.getMessage());
            }
      }

}
