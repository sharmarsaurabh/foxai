package com.baseproject.webdriver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.baseproject.global.Config;

/**
* This class contains generic WebDriver utility functions
*
* @author 10647421
*/
public class WebDriverFactory extends Config {

      /**
      * The function is to get defaut value if parameterized value is null
      * @param value
      * @param defaultValue
      * @return default value or parameterized value
      */
      protected static synchronized <T> T getValueOrDefault(T value, T defaultValue) {
            return value == null ? defaultValue : value;
      }

      /**
      * The function is to fetch value from TestNG Suite or TestName
      * @param value
      * @param defaultValue
      * @return TestNG Suite name or TestName
      */
      protected static synchronized <T> T getValueOrDefaultExtReportName(T value, T defaultValue) {
            return value.toString().contains("Default") ? defaultValue : value;
      }

      /**
      * The function creates thread safe Webdriver instance for IE and Chrome browser
      * Parameter "browser" to get either from arguments or config.properties
      * 
       * @throws Exception Catches driver exceptions
      */
      protected static synchronized void createWebDriver() throws Exception {
            try {

                  switch (getValueOrDefault(System.getProperty("browser"), getPropertyValue("browser"))) {

                  case "IE":
                  case "ie":
                  case "InternetExplorer":
                        driver = new InternetExplorerDriver(setIEcapabilities());

                        break;

                  case "CHROME":
                  case "chrome":
                  case "Chrome":

                        driver = new ChromeDriver(setChromecapabilities(Boolean.parseBoolean(
                                    (String) getValueOrDefault(System.getProperty("headless"), (getPropertyValue("headless"))))));

                        break;

                  default:
                        throw new InvalidParameterException();
                  }

                  driverThreadSafe.set(driver);
            } catch (Exception e) {
                  e.getMessage();

            }
      }

      /**
      * The function creates thread safe Webdriver instance for IE and Chrome browser
      * Parameter "browser" to get either from arguments or config.properties Set
      * Implicit_timeout, Page_timeout, Script_timeout for Webdriver instance
      * 
       * @throws Exception Throws generic exception for Webdriver
      */
      protected static synchronized void intiDriver() throws Exception {
            try {

                  createWebDriver();
                  setImplicitTimeout(DEFAULT_IMPLICIT_TIMEOUT_IN_SECONDS);
                  setPageLoadTimeout(DEFAULT_PAGE_LOAD_TIME_OUT_SECONDS);
                  setScriptTimeout(DEFAULT_SCRIPT_TIMEOUT_IN_SECONDS);
            } catch (Exception e) {
                  e.getMessage();

            }
      }

      /**
      * The functions quits Webdriver instance
      */
      protected static synchronized void quitDriver() {

            getWebDriver().quit();
      }

      /**
      * The function returns thread safe Webdriver instance
      * 
       * @return
      */
      protected static synchronized WebDriver getWebDriver() {
            return driverThreadSafe.get();
      }

      /**
      * The function is used to maximize the browser window
      */
      protected static synchronized void maximize() {
            getWebDriver().manage().window().maximize();
      }

      /**
      * The function sets Webdriver to Implicitly wait
      * 
       * @param timeInSec Global variable DEFAULT_IMPLICIT_TIMEOUT_IN_SECONDS
      */
      protected static synchronized void setImplicitTimeout(long timeInSec) {

            getWebDriver().manage().timeouts().implicitlyWait(timeInSec, TimeUnit.SECONDS);
      }

      /**
      * The function sets Webdriver for PageLoadTimeout
      * 
       * @param timeInsec Global variable DEFAULT_PAGE_TIMEOUT_IN_SECONDS
      */
      protected static synchronized void setPageLoadTimeout(long timeInsec) {
            getWebDriver().manage().timeouts().pageLoadTimeout(timeInsec, TimeUnit.SECONDS);

      }

      /**
      * The function sets Webdriver for ScriptTimeout
      * 
       * @param timeInSec Global variable DEFAULT_SCRIPT_TIMEOUT_IN_SECONDS
      */
      protected static synchronized void setScriptTimeout(long timeInSec) {

            getWebDriver().manage().timeouts().setScriptTimeout(timeInSec, TimeUnit.SECONDS);
      }

      /**
      * The function to capture screenshot without description
      * 
       * @throws Exception Throws IO exception
      * 
       */
      protected static synchronized void screenshot() {

            try {

                  Files.copy(((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.FILE).toPath(),
                              Paths.get("./target/screenshots/Failure_Screenshot_" + LocalDateTime.now().format(dateTimeFormatter)
                                          + ".png"),
                              StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                  e.printStackTrace();
            } catch (Exception e) {
                  e.printStackTrace();
            }
      }

      /**
      * Commented code for screen shot capture. Handled in @After Method along with
      * Extent report The function to capture screenshot with description
      * 
       * @param description -
      * @throws Exception Throws IO exception
      * 
       */
      protected static synchronized void screenshot(String description) {

            try {
                  Files.copy(((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.FILE).toPath(),
                              Paths.get("./target/screenshots/" + description + "_Screenshot_"
                                          + LocalDateTime.now().format(dateTimeFormatter) + ".png"),
                              StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                  e.printStackTrace();
            } catch (Exception e) {
                  e.printStackTrace();
            }
      }
}


