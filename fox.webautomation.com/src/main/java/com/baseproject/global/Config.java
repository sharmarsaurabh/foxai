
/**
* This com.mmc.global package has a 'Config' class for global variables and configuration functions  
 * @since 1.0
* @author U1196286
*/
package com.baseproject.global;

import static org.openqa.selenium.ie.InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS;

import java.awt.FileDialog;
import java.awt.Robot;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Hashtable;
import java.util.Properties;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.STSourceType;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.baseproject.utility.Log;

/**
* @author 10647421 The Config class contains <br>
*         Global variables <br>
*         Config functions </br>
*/
public class Config {

      protected final static int DEFAULT_EXPLICIT_WAIT = 180;
      protected final static int DEFAULT_IMPLICIT_TIMEOUT_IN_SECONDS = 60;
      protected final static int DEFAULT_PAGE_LOAD_TIME_OUT_SECONDS = 180;
      protected final static int DEFAULT_SCRIPT_TIMEOUT_IN_SECONDS = 60;
      protected final static String RESOURCES_FOLDER_PATH = "./src/test/resources/";
      protected final static String PATH_LOGS_SCREENSHOTS_REPORTS = "./target/";
      protected final static String PATH_SCREENSHOTS = "./target/screenshots/";
      protected final static String IE_EXE_PATH = "./src/test/resources/";
      protected final static String CHROME_EXE_PATH = "./src/test/resources/";
      protected final static String PROJECT_CONFIG_PROPERTIES_PATH = "./src/test/resources/Config.properties";
      protected final static String PROJECT_TESTCASES_EXCEL_PATH = "./src/test/resources/Test.xlsx";
      protected final static String PROJECT_EXTENT_REPORT = "./target/Reports/";
      protected static InternetExplorerOptions ieOptions = null;
      protected static ChromeOptions options = null;
      protected static Properties prop = null;
      protected static String browser = null;
      protected static String headless = null;
      protected static ThreadLocal<WebDriver> driverThreadSafe = new ThreadLocal<WebDriver>();
      protected static WebDriver driver = null;
      protected static Logger log = null;
      protected static Robot robot;
      protected static WebDriverWait wait = null;

      protected static Fillo fillo = null;
      protected static Connection connection = null;
      protected static ThreadLocal<Fillo> filloThreadSafe = new ThreadLocal<Fillo>();
      protected static ThreadLocal<Connection> connectionThreadSafe = new ThreadLocal<Connection>();

      protected static String runTime;
      protected static Hashtable<String, Logger> loggers = new Hashtable<String, Logger>();
      protected static String strCheckTestToRunbyMethodName = "Select *  from TestCases where TestName =";
      protected static String strReadTestDataByMethodName = "Select * from TestData where TestName =";
      protected static Hashtable<String, String> testData;
      protected static Object[][] data;
      protected static Recordset recordset;
      protected static Recordset recordsettwo;

      protected static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM.dd.yyyy.hh.mm.ss.SSS");
      protected static Path source, target;
      protected static ThreadLocal<ExtentReports> extentReportThreadSafe = new ThreadLocal<ExtentReports>();
      protected static ThreadLocal<ExtentTest> extentTestThreadSafe = new ThreadLocal<ExtentTest>();
      protected static ExtentReports report;
      protected static ExtentTest test;
      protected static ExtentHtmlReporter htmlReporter;

      /**
      * The method is to set Internet Explorer Capabilities <br>
      * Sets INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS <br>
      * Sets WINDOWS platform <br>
      * Sets ACCEPT_SSL_CERTS true <br>
      * Sets NativeEvents True <br>
      * Sets ignoreProtectedModeSettings true <br>
      * Sets EnablePersistentHover true <br>
      * 
       * Sets ignore Zoom settings <br>
      * 
       * @return IEOoptions
      * 
       */
      protected static synchronized InternetExplorerOptions setIEcapabilities() {
            ieOptions = new InternetExplorerOptions();
            ieOptions.setCapability(INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
            // ieOptions.setCapability("browserFocus",true);
            ieOptions.setCapability("unexpectedAlertBehaviour", "accept");
            ieOptions.ignoreZoomSettings();
            ieOptions.setCapability(CapabilityType.PLATFORM_NAME, Platform.WINDOWS);
            ieOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            ieOptions.setCapability("nativeEvents", true);
            ieOptions.setCapability("ignoreProtectedModeSettings", true);
            ieOptions.setCapability("EnablePersistentHover", true);
            // ieOptions.setCapability("requireWindowFocus", true);
            System.setProperty("webdriver.ie.driver", IE_EXE_PATH + "IEDriverServer.exe");
            return ieOptions;
      }

      /**
      * The method is to set Chrome Capabilities <br>
      * Sets argument start-maximize <br>
      * Sets argument disable-default-apps <br>
      * Sets argument disable-gpu <br>
      * Sets argument disable-dev-shm-usage <br>
      * 
       * @param useHeadlessMode as system argument or config.property
      * @return Chrome Options
      */
      protected static synchronized ChromeOptions setChromecapabilities(Boolean useHeadlessMode) {
            options = new ChromeOptions();
            options.setCapability(CapabilityType.BROWSER_NAME, BrowserType.CHROME);
            options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
            options.addArguments("--disable-default-apps");
            options.addArguments("--disable-gpu");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("user-data-dir=C:\\Users\\10645475\\AppData\\Local\\Google\\Chrome\\User Data\\Profile 4");
            options.addArguments("--start-maximized");
            options.setHeadless(useHeadlessMode);
 //START
 // Added on 3rd Feb by Saurabh Sharma
 //        	DesiredCapabilities capabilities = DesiredCapabilities.chrome();
 //           capabilities.setCapability(ChromeOptions.CAPABILITY, options);
 //           options.merge(capabilities);
            
//END           

            System.setProperty("webdriver.chrome.driver", CHROME_EXE_PATH + "chromedriver.exe");
            return options;
      }

      /**
      * The function is to Get Property value from Project specific Config.properties
      * file <br>
      * 
       * @param propertykey as parameter to fetch corresponding value <br>
      * @return String value for key of Config.properties file (Project specific)
      *         else returns null
      */
      protected static synchronized String getPropertyValue(String propertykey) {
            try {
                  return prop.getProperty(propertykey);
            } catch (Exception e) {
                  return null;
            }
      }

      /**
      * The function is to Initialize Config.properties file
      * 
       * @throws IOException
      * @throws FileNotFoundException
      */
      protected static synchronized void initProperties() throws IOException, FileNotFoundException {

            try {
                  prop = new Properties();
                  prop.load(new FileInputStream(PROJECT_CONFIG_PROPERTIES_PATH));
            } catch (FileNotFoundException e) {
                  e.printStackTrace();
            } catch (IOException e) {
                  e.printStackTrace();
            }

      }

      /**
      * The function is to create folders inside Target folder for Project
      * 
       * @param folderName
      * @throws IOException
      * @throws FileNotFoundException
      */
      protected static synchronized void createFolder(String folderName) {

            try {
                  if (Files.notExists(Paths.get(PATH_LOGS_SCREENSHOTS_REPORTS + folderName))) {
                        Files.createDirectory(Paths.get(PATH_LOGS_SCREENSHOTS_REPORTS + folderName));
                  }
            } catch (FileNotFoundException e) {
                  e.printStackTrace();
            } catch (IOException e) {
                  e.printStackTrace();
            }

      }

      /**
      * This is a sub-routine call from initlogs function.Used for Log initilization
      * 
       * @param name   - type of log
      * @param path   - path of log file to be saved
      * @param remove - boolean value true
      */
      protected static synchronized Logger initializeLogger(String name, String path, boolean remove) {
            Logger thisLogger = Logger.getLogger(name);
            try {
                  thisLogger.removeAllAppenders();
                  Logger.getRootLogger().removeAllAppenders();

            } catch (Exception ex) {
                  ex.printStackTrace();
            }
            FileAppender logAppender = null;
            Layout layout = new PatternLayout("%d [%M]: %m%n");

            try {
                  File f = new File(path);
                  if (f.exists() && remove) {
                        f.delete();
                  }
                  logAppender = new FileAppender(layout, path, !remove);
            } catch (IOException e) {
                  e.printStackTrace();
            }
            thisLogger.addAppender(logAppender);

            ConsoleAppender consoleAppender = new ConsoleAppender(layout);
            thisLogger.addAppender(consoleAppender);

            thisLogger.setLevel((Level) Level.DEBUG);
            return thisLogger;
      }

      /**
      * This should be ran to initialize the logger with the correct class. The
      * Logger will not work if this isn't ran
      *
      * @param type - append the type of logger you are creating
      */

      protected static synchronized void initLogs(String type) {

            DateTimeFormatter ft = DateTimeFormatter.ofPattern("MM.dd.yyyy.hh.mm.ss.SSS");
            if (runTime == null) {
                  runTime = LocalDateTime.now().format(ft);
            }
            String appendType = "." + type;
            if (type == "info") {
                  appendType = "";
            }
            String path = "./target/logs/" + runTime + appendType + ".log";
            loggers.put(type, initializeLogger(type, path, true));
      }

      /**
      * This is a log funciton to return particular logger type
      * 
       * @param type of a logger
      * @return logger with above type
      */
      protected static synchronized Logger getLogger(String type) {

            if (loggers.containsKey(type)) {
                  return loggers.get(type);

            } else {
                  initLogs(type);
                  return loggers.get(type);
            }
      }

      /**
      * This function is to initialize threadSafe fillo connection
      */
      protected static synchronized void initExcelConnection() {

            try {
                  setThreadSafeFilo();
                  setThreadSafeFilloConn();

            } catch (FilloException e) {

                  e.printStackTrace();
            }
      }

      /**
      * This function is a setter for threadSafe fillo instance
      */
      protected static synchronized void setThreadSafeFilo() {
            fillo = new Fillo();
            filloThreadSafe.set(fillo);
      }

      /**
      * This function is a getter for threadSafe fillo instance
      * 
       * @return threadSafe fillo instance
      */
      protected static synchronized Fillo getThreadSafeFilo() {
            try {

                  if (!(filloThreadSafe.get() == null)) {
                        return filloThreadSafe.get();
                  } else
                        throw new Exception();
            } catch (Exception e) {
                  return null;
            }

      }

      /**
      * This function is a setter for threadSafe fillo connection
      * 
       * @throws FilloException
      */
      protected static synchronized void setThreadSafeFilloConn() throws FilloException {
      connectionThreadSafe.set(getThreadSafeFilo().getConnection(PROJECT_TESTCASES_EXCEL_PATH));
      }

      /**
      * This function is a getter for threadSafe fillo connection
      * 
       * @return
      */
      protected static synchronized Connection getThreadSafeFilloConn() {
            try {
                  if (!(connectionThreadSafe.get() == null)) {
                        return connectionThreadSafe.get();
                  } else
                        throw new Exception();
            } catch (Exception e) {
                  return null;
            }

      }

      /**
      * This function is a setter for threadSafe fillo instance
      */
      protected static synchronized void setExtentReportThreadSafe() {

            extentReportThreadSafe.set(report);
      }

      /**
      * This function is a getter for threadSafe fillo instance
      * 
       * @return threadSafe fillo instance
      */
      protected static synchronized ExtentReports getExtentReportThreadSafe() {
            try {
                  if (!(extentReportThreadSafe.get() == null)) {
                        return extentReportThreadSafe.get();
                  } else
                        throw new Exception();
            } catch (Exception e) {
                  return null;
            }

      }

      /**
      * This function is a setter for threadSafe fillo instance
      */
      protected static synchronized void setExtentTestThreadSafe(ExtentTest t) {

            extentTestThreadSafe.set(t);
      }

      /**
      * This function is a getter for threadSafe fillo instance
      * 
       * @return threadSafe fillo instance
      */
      protected static synchronized ExtentTest getExtentTestThreadSafe() {
            try {

                  if (!(extentTestThreadSafe.get() == null)) {
                        return extentTestThreadSafe.get();
                  } else
                        throw new Exception();
            } catch (Exception e) {
                  return null;
            }

      }

      /**
      * This function is to read test data for the testCases through Excel
      * 
       * @param methodname - methodname of @test
      * @return testdata - Hashtable
      * @throws FilloException
      */
      protected static synchronized Hashtable<String, String> readTestDataByMethodName(String methodname) {
            try {
                  recordset = getThreadSafeFilloConn().executeQuery(strReadTestDataByMethodName + "'" + methodname + "'");
                  // data = new Object[recordset.getCount()][1];
                  // System.out.println(data.toString());
                  // int cell = 0;
                  while (recordset.next()) {
                        testData = new Hashtable<String, String>();
                        for (String strColumns : recordset.getFieldNames()) {

                              if (!(recordset.getField(strColumns.toString()).isEmpty())) {
                                    testData.put(strColumns, recordset.getField(strColumns.toString()));
                              }
                        }
                        // data[cell][0] = testData;
                        // System.out.println(data[cell][0]);
                        // cell++;
                  }
            } catch (FilloException e) {
                  Log.info("Excel read test data failure. Unable to read data for test " + methodname);
                  e.printStackTrace();
            }

            return testData;
      }

      /**
      * This function is to check flag TestToRun for the testCases through Excel
      * 
       * @param methodname - methodname of @test
      * @return boolean - <b>True</b> for TestToRun equals 'Y' else return
      *         <b>False<b>
      * @throws FilloException
      */
      protected static synchronized boolean checkTestToRunbyMethodName(String methodname) throws FilloException {
            try {
                  recordsettwo = getThreadSafeFilloConn()
                              .executeQuery(strCheckTestToRunbyMethodName + "'" + methodname + "'");

                  if (!recordsettwo.getField("TestTorun").isEmpty()) {
                        if (recordsettwo.getField("TestTorun").equalsIgnoreCase("Y")) {
                              return true;
                        }
                  } else {
                        Log.info("Excel read test data failure. No 'Y' or 'N' value found for TestToRun flag for test "
                                    + methodname);
                  }
            } catch (FilloException e) {
                  Log.info("Excel read test data failure. Unable to read data for test " + methodname);
            } catch (Exception e) {
                  Log.info(e.getMessage());
            }
            return false;
      }

      /**
      * This function is to close the fillo connection for excel
      */
      protected static synchronized void closeExcelConnection() {

            getThreadSafeFilloConn().close();

      }

}


