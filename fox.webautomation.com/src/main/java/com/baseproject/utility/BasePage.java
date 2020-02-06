/**
* This Package contains BasePage class
*
* @author U1196286
*/
package com.baseproject.utility;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FilenameFilter;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Reporter;

import com.baseproject.webdriver.WebDriverFactory;

/**
* This class contains generic Page functions and extends WebDriverFactory class
*
* @author 10647421
*/
public class BasePage extends WebDriverFactory {

      /**
      * This function is to Initialize Page WebElements through
      * Pagefactory.initElements Method
      * 
       * @param page : page object
      * @exception Exception
      * 
       */
      protected static synchronized void initPageFactory(Object page) {
            try {
                  PageFactory.initElements(getWebDriver(), page);

            } catch (WebDriverException e) {
                  Log.error("WebDriver exception occurred at initPageFactory()", e);
                  screenshot("Failure");
            } catch (Exception e) {
                  Log.error("Exxception occurred at initPageFactory()", e);
                  screenshot("Failure");
            }

      }

      /**
      * This function is to Accept Alert
      * 
       * @return Boolean value True if Alert is Present else return False
      * @exception NoAlertPresentException, Exception
      */
      protected static synchronized void acceptAlertDialog() {
            try {
                  getWebDriver().switchTo().alert().accept();

            } catch (NoAlertPresentException e) {
                  Log.error("Alert is not present occurred at acceptAlertDialog()", e);
                  screenshot("Failure");
            } catch (WebDriverException e) {
                  Log.error("WebDriver exception occurred at acceptAlertDialog() ", e);
                  screenshot("Failure");
            } catch (Exception e) {
                  Log.error("Exception occurred at acceptAlertDialog()  ", e);
                  screenshot("Failure");
            }
      }

      /**
      * This function is to get text from Alert window
      * 
       * @return String Text Value of alert
      */
      protected synchronized static String alertGetText() {
            try {
                  return getWebDriver().switchTo().alert().getText();
            } catch (WebDriverException e) {
                  Log.error("WebDriver exception occurred at alertGetText() ", e);
                  screenshot("Failure");
                  throw e;
            } catch (Exception e) {
                  Log.error("Exception occurred at alertGetText() ", e);
                  screenshot("Failure");
                  throw e;
            }
      }

      /**
      * This function is to tick the checkbox
      * 
       * @param description : Description for the logger
      * @param checkbox    : WebElement as a parameter
      */
      protected synchronized static void checkboxCheck(String description, WebElement checkbox) {
            try {
                  if (checkbox.isSelected()) {
                        Log.info("Checkbox [" + description + "] is already checked");
                  } else {
                        checkbox.click();
                        Log.info("Checkbox [" + description + "] is checked");
                  }
            } catch (Exception e) {
                  Log.error("Unable to check the [" + description + "] check box", e);
                  screenshot("Failure");
                  throw e;
            }
      }

      /**
      * This function is to untick the checkbox
      * 
       * @param description : Description for the logger
      * @param checkbox    : WebElement as a parameter
      */
      protected synchronized static void checkboxUncheck(String description, WebElement checkbox) {
            try {
                  if (checkbox.isSelected()) {
                        checkbox.click();
                        Log.info("Checkbox [" + description + "] is unchecked");
                  } else {
                        Log.info("Checkbox [" + description + "] is already unchecked");
                  }

            } catch (Exception e) {
                  Log.error("Unable to check the [" + description + "] check box", e);
                  screenshot("Failure");
                  throw e;
            }
      }

      /**
      * This function is to get list of values from the Dropdown
      * 
       * @param description : Description for the logger
      * @param dropdown    : WebElement as a parameter
      */
      protected static synchronized List<String> getAllOptionsFromDropDown(String description, WebElement dropdown) {
            Log.info("Get all the options from the [" + description + "] Drop Down");
            try {
                  return new Select(dropdown).getOptions().stream().map(WebElement::getText).collect(Collectors.toList());
            } catch (Exception e) {
                  Log.error("Unable to get all the options from the [" + description + "] Drop Down", e);
                  screenshot("Failure");
                  throw e;
            }
      }

      /**
      * This function is to select the dropdown value through visible text
      * 
       * @param description : Description for the logger
      * @param dropdown    : WebElement as a parameter
      * @param visibleText : Value to be selected in a dropdown
      */
      protected synchronized static void selectDropDownByVisibleText(String description, WebElement dropdown,
                  String visibleText) {
            Log.info("Select [" + visibleText + "] from the [" + description + "] Drop Down");
            try {
                  new Select(dropdown).selectByVisibleText(visibleText);
            } catch (Exception e) {
                  Log.error("Unable to select [" + visibleText + "] from the [" + description + "] Drop Down", e);
                  screenshot("Failure");
                  throw e;
            }
      }

      /**
      * This function is to select the dropdown value through value
      * 
       * @param description : Description for the logger
      * @param checkbox    : WebElement as a parameter
      * @param value       : Value to be selected in a dropdown
      */
      protected synchronized static void selectDropDownByValue(String description, WebElement dropdown, String value) {

            Log.info("Select [" + value + "] from the [" + description + "] Drop Down");
            try {
                  new Select(dropdown).selectByValue(value);
            } catch (Exception e) {
                  Log.error("Unable to select [" + value + "] from the [" + description + "] Drop Down", e);
                  screenshot("Failure");
                  throw e;
            }
      }

      /**
      * This function is to select the dropdown value through index
      * 
       * @param description : Description for the logger
      * @param dropdown    : WebElement as a parameter
      * @param index       : Value to be selected in a dropdown
      */
      protected synchronized static void selectDropDownByIndex(String description, WebElement dropdown, String index) {

            Log.info("Select index [" + index + "] from the [" + description + "] Drop Down");
            try {
                  new Select(dropdown).selectByIndex(Integer.parseInt(index));
            } catch (Exception e) {
                  Log.error("Unable to select index [" + index + "] from the [" + description + "] Drop Down", e);
                  screenshot("Failure");
                  throw e;
            }
      }

      /**
      * This function is to get the selected option/Default option from the dropdown
      * 
       * @param description : Description for the logger
      * @param dropdown    : WebElement as a parameter
      * 
       */
      protected static synchronized String getDefaultDropDownValue(String description, WebElement dropdown) {
            Log.info("Get the default selected option from the [" + description + "] Drop Down");
            try {
                  return new Select(dropdown).getFirstSelectedOption().getText();
            } catch (Exception e) {
                  Log.error("Unable to get default the selected option from the [" + description + "] Drop Down", e);
                  screenshot("Failure");
                  throw e;
            }
      }

      /**
      * This function is to get the size of WebElements
      * 
       * @param description : Description for the logger
      * @param elements    : WebElement as a parameter
      * 
       */
      protected synchronized int sizeOfWebElements(String description, List<WebElement> elements) {
            Log.info("Get the size of elements list  [" + description + "]");
            try {
                  return elements.size();
            } catch (Exception e) {
                  Log.error("Unable to get the size of Web Elements occurred at sizeOfWebElements()", e);
                  screenshot("Failure");
                  throw e;
            }
      }

      /**
      * This function is to open the mentioned URL
      * 
       * @param url
      */
      protected static synchronized void openUrl(String url) {
            Log.info("Open the URL [" + url + "]");
            try {
                  getWebDriver().get(url);
            } catch (Exception e) {
                  Log.error("Unable to open the URL [" + url + "]", e);
                  screenshot("Failure");
                  throw e;
            }
      }

      /**
      * This function is to get the current page URL
      * 
       * @return : Current URL
      */
      protected static synchronized String getCurrentUrl() {
            String url = "";
            try {
                  url = getWebDriver().getCurrentUrl();
                  Log.info("The current URL is [" + url + "]");
                  return url;
            }

            catch (Exception e) {
                  Log.error("Unable to get the current URL", e);
                  screenshot("Failure");
                  throw e;
            }

      }

      /**
      * This function is to refresh the browser
      */
      protected static synchronized void refreshBrowser() {
            Log.info("Refresh the browser");
            try {
                  getWebDriver().navigate().refresh();
            } catch (Exception e) {
                  Log.error("Unable to get the current URL", e);
                  screenshot("Failure");
                  throw e;
            }
      }

      
      /**
       * This function is to Check on a particular WebElement is displayed or not
       * Saurabh Sharma 31/Jan/2020
        * @param description Description for the logger
       * @param element     WebElement
       * 
        */
       protected static synchronized void checkDisplayedElement(String description, WebElement element) {
             Log.info("Check [" + description + "]");
             try {
	            	 if (element.isDisplayed()) {
	                     Log.info("Text [" + description + "] is displayed on the page");
	               } else {
	                     Log.error("Text [" + description + "] is not displayed on the page");
	               }
             } catch (Exception e) {
                   Log.error("Unable to Check [" + description + "]", e);
                   screenshot("Failure");
                   throw e;
             }
       }
      
      
      /**
      * This function is to click on a particular WebElement
      * 
       * @param description Description for the logger
      * @param element     WebElement
      * 
       */
      protected static synchronized void clickOnElement(String description, WebElement element) {
            Log.info("Click on [" + description + "]");
            try {
                  element.click();
            } catch (Exception e) {
                  Log.error("Unable to click on [" + description + "]", e);
                  screenshot("Failure");
                  throw e;
            }
      }
      
      /**
       * This function is to click on a particular WebElement via Java script executor
       * 
        * @param description Description for the logger
       * @param element     WebElement
       * 
        */
      protected static synchronized void clickOnElementjse(String description, WebElement element) {
    	  
    	  Log.info("Click on [" + description + "]");
          try {
      JavascriptExecutor executor = (JavascriptExecutor)driver;
      executor.executeScript("arguments[0].click();", element);
          } catch (Exception e) {
              Log.error("Unable to click on [" + description + "]", e);
              screenshot("Failure");
              throw e;
        }
  }

      /**
      * This function is to clear the field and then enters the text in the field
      * 
       * @param description Description for the logger
      * @param element     WebElement
      * @param texToEnter  Text to enter
      * 
       */
      protected static synchronized void enterText(String description, WebElement element, String texToEnter) {
            Log.info("Enter text [" + texToEnter + "] in the [" + description + "] Text Box");
            try {
                  element.clear();
                  element.sendKeys(texToEnter);
            } catch (Exception e) {
                  Log.error("Unable to enter text [" + texToEnter + "] in the [" + description + "] Text Box", e);
                  screenshot("Failure");
                  throw e;
            }
      }

      /**
      * This function is to get the Text value from the Page WebElement
      * 
       * @param description : Description For the logger
      * @param element     : WebElement
      * @return Text of the Webelement
      */
      protected static synchronized String getText(String description, WebElement element) {
            Log.info("Get Text from the [" + description + "]");
            String text = "";
            try {
                  text = element.getText();
            } catch (Exception e) {
                  Log.error("Unable to get text from [" + description + "]", e);
                  screenshot("Failure");
                  throw e;
            }
            return text;
      }

      /**
      * To get the list of text from all the elements
      * 
       * @param description : Description For the logger
      * @param elements    : List of WebElements
      * @return List of WebElements
      */
      protected static synchronized List<String> getTextFromAllElements(String description, List<WebElement> elements) {
            Log.info("Get the List of Text from [" + description + "]");
            try {
                  return elements.stream().map(WebElement::getText).collect(Collectors.toList());
            } catch (Exception e) {
                  Log.error("Unable to get the List of Text from [" + description + "]", e);
                  screenshot("Failure");
                  throw e;
            }
      }

      /**
      * This function is to switch to Window that contains the urlText.
      * 
       * @param description : Description For the logger
      * @param urlText     : url text of the new window
      */
      protected static synchronized void switchToWindowWithUrlText(String description, String urlText) {
            Log.info("Switch to window with URL [" + description + "]");
            try {
                  Optional<WebDriver> window = getWebDriver().getWindowHandles().stream()
                              .map(getWebDriver().switchTo()::window).filter(driver -> driver.getCurrentUrl().contains(urlText))
                              .findFirst();
                  if (window.isPresent()) {
                        Log.info("Switch to [" + description + "] Window which contains [" + urlText + "] in URL");
                  } else {
                        Log.error("Unable to find [" + description + "] Window with Title [" + urlText + "] in URL");
                  }
            } catch (Exception e) {
                  Log.error("Unable to switch to window [" + description + "]", e);
                  screenshot("Failure");
                  throw e;
            }
      }

      /**
      * This function is to switch to new window that contains the title.
      * 
       * @param description : Description For the logger
      * @param title       : title text of the new window
      */
      protected static synchronized void switchToWindowWithTitle(String description, String titleText) {
            Log.info("Switch to window with Title [" + description + "]");
            try {
                  Optional<WebDriver> window = getWebDriver().getWindowHandles().stream()
                              .map(getWebDriver().switchTo()::window).filter(driver -> driver.getTitle().contains(titleText))
                              .findFirst();
                  if (window.isPresent()) {
                        Log.info("Switch to [" + description + "] Window which contains [" + titleText + "] in title");
                  } else {
                        Log.error("Unable to find [" + description + "] Window with Title [" + titleText + "] in title");
                  }
            } catch (Exception e) {
                  Log.error("Unable to switch to window [" + description + "]", e);
                  screenshot("Failure");
                  throw e;
            }

      }

      /**
      * This function is to switch to Parent window
      * 
       * @param description : Description For the logger
      */
      protected synchronized static void switchToParentWindow(String description) {
            Log.info("Switch to Parent Window [" + description + "]");
            try {
                  Optional<String> window = getWebDriver().getWindowHandles().stream().findFirst();
                  if (window.isPresent()) {
                        Log.info("Switch to [" + description + "] Window");
                        getWebDriver().switchTo().window(window.get());
                  } else {
                        Log.error("Unable to find [" + description + "] Window");
                  }
            } catch (Exception e) {
                  Log.error("Unable to switch to window [" + description + "]", e);
                  screenshot("Failure");
                  throw e;
            }
      }

      /**
      * This function is to switch to new window
      * 
       * @param description : Description For the logger
      */
      protected synchronized static void switchToWindow(String description) {
            Log.info("Switch to window  [" + description + "]");
            try {
                  String currentWindow = getCurrentWindow();
                  Optional<String> window = getWebDriver().getWindowHandles().stream()
                              .filter(handle -> !handle.equals(currentWindow)).findFirst();
                  if (window.isPresent()) {
                        Log.info("Switch to [" + description + "] Window");
                        getWebDriver().switchTo().window(window.get());
                  } else {
                        Log.error("Unable to find [" + description + "] Window");
                        getWebDriver().switchTo().window(currentWindow);
                  }
            } catch (Exception e) {
                  Log.error("Unable to switch to window [" + description + "]", e);
                  screenshot("Failure");
                  throw e;
            }
      }

      /**
      * This method is used to get the handle of the web-page
      * 
       * @return The current window handle
      */
      protected static synchronized String getCurrentWindow() {
            Log.info("Get Current window");
            try {
                  return getWebDriver().getWindowHandle();
            } catch (Exception e) {
                  Log.error("Unable to get current window handle", e);
                  screenshot("Failure");
                  throw e;
            }

      }

      /**
      * This method is to switch to frame using weblement
      * 
       * @param description : description
      * @param element     : Webelement
      */
      protected synchronized static void switchToFrameByWebelement(String description, WebElement element) {

            try {
                  Log.info("Switch to frame by Webelement [" + description + "]");
                  getWebDriver().switchTo().frame(element);
            } catch (NoSuchFrameException e) {
                  Log.error("Unable to switch to frame [" + description + "]", e);
                  screenshot("Failure");
                  throw e;
            } catch (Exception e) {
                  Log.error("Exception  [" + e.getMessage() + "]", e);
                  screenshot("Failure");
                  throw e;
            }
      }

      /**
      * This method is to switch to frame using index
      * 
       * @param description : description
      * @param index       : Index
      */
      protected synchronized static void switchToFrameByIndex(String description, int index) {

            try {
                  Log.info("Switch to frame by index [" + description + "]");
                  getWebDriver().switchTo().frame(index);
            } catch (NoSuchFrameException e) {
                  Log.error("Unable to switch to frame [" + description + "]", e);
                  screenshot("Failure");
                  throw e;
            } catch (Exception e) {
                  Log.error("Exception  [" + e.getMessage() + "]", e);
                  screenshot("Failure");
                  throw e;
            }
      }

      /**
      * This method is to switch to default frame
      * 
       * @param description : Description
      */
      protected synchronized static void switchToDefaultFrame(String description) {
            try {
                  Log.info("Switch to default frame[" + description + "]");
                  getWebDriver().switchTo().defaultContent();
            } catch (NoSuchFrameException e) {
                  Log.error("Unable to switch to frame [" + description + "]", e);
                  screenshot("Failure");
                  throw e;
            } catch (Exception e) {
                  Log.error("Exception  [" + e.getMessage() + "]");
                  screenshot("Failure");
                  throw e;
            }
      }

      /**
      * This function is to move to element using Actions utility
      * 
       * @param description : Description For the logger
      * @param element     : WebElement
      * @return
      */
      protected static synchronized Actions movetoElement(String description, WebElement element) {

            try {
                  Log.info("Move to Element [" + description + "]");
                  return new Actions(getWebDriver()).moveToElement(element);
            } catch (Exception e) {
                  Log.error("Unable to find element to move [" + description + "]", e);
                  screenshot("Failure");
                  throw e;
            }

      }

      /**
      * This method is used to wait for a particular element to appear in the
      * web-page
      * 
       * @param description : Description For the logger
      * @param element     : WebElement
      */
      protected static synchronized void waitForElementToBePresent(String description, WebElement element) {
            Log.info("Wait for [" + description + "] to become visible");
            try {
                  Wait<WebDriver> wait = new FluentWait<WebDriver>(getWebDriver()).withTimeout(Duration.ofSeconds(60))
                              .pollingEvery(Duration.ofMillis(250)).ignoring(NoSuchElementException.class);
                  wait.until(ExpectedConditions.visibilityOf(element));
                  Log.info("[" + description + "] is visible now");
            } catch (Exception e) {
                  Log.error("Unable to find [" + description + "]", e);
                  screenshot("Failure");
                  throw e;
            }
      }

      /**
      * This method is used to wait for a particular element to appear in the
      * web-page
      * 
       * @param description : Description For the logger
      * @param element     : WebElement
      * @param seconds     : Time in seconds
      */
      protected static synchronized void waitForElementToBePresent(String description, WebElement element, long seconds) {
            Log.info("Wait for [" + description + "] to become visible");
            try {
                  Wait<WebDriver> wait = new FluentWait<WebDriver>(getWebDriver()).withTimeout(Duration.ofSeconds(seconds))
                              .pollingEvery(Duration.ofMillis(2500)).ignoring(NoSuchElementException.class);
                  wait.until(ExpectedConditions.visibilityOf(element));
                  Log.info("[" + description + "] is visible now");
                  
            } catch (Exception e) {
                  Log.error("Unable to find [" + description + "]", e);
                  screenshot("Failure");
                  throw e;
            }
      }

      /**
      * The function is to check if the alert is present
      * 
       * @param description : Description For the logger
      * @return <b>true</b> if alert is present, <b>false</b> otherwise
      */
      protected static synchronized boolean checkIsAlertPresent(String description) {
            Log.info("Check for Alert [" + description + "] to be present");
            try {
                  Wait<WebDriver> wait = new FluentWait<WebDriver>(getWebDriver()).withTimeout(Duration.ofSeconds(60))
                              .pollingEvery(Duration.ofMillis(250)).ignoring(NoSuchElementException.class);
                  wait.until(ExpectedConditions.alertIsPresent());
                  return true;
            } catch (Exception e) {
                  Log.error("Unable to find the [" + description + "] alert", e);
                  return false;
            }
      }

      /**
      * The function is to manage the Alert basis on boolean condition
      * 
       * @param description : Description For the logger
      * @param condition   : <b>true</b> to accept alert, <b>false</b> to dismiss
      *                    alert
      */
      protected static synchronized void acceptRejectAlert(String description, boolean condition) {
            Log.info("Check for Alert [" + description + "] to be Accept/Reject");
            try {
                  if (condition) {
                        Log.info("Accept the [" + description + "] alert");
                        getWebDriver().switchTo().alert().accept();
                  } else {
                        Log.info("Dismiss the [" + description + "] alert");
                        getWebDriver().switchTo().alert().dismiss();
                  }
            } catch (Exception e) {
                  Log.error("Unable to Accept/Reject [" + description + "] alert", e);

            }
      }

      /**
      * The function uses Robot class to save PDF(displayed on Web page) on given
      * path
      * 
       * @param description   : PDF details
      * @param pathToSavePDF : Path to save PDF
      * @throws Exception Generic Exception
      */
      protected static synchronized void savePDFfromWebPage(String description, String pathToSavePDF) {
            try {
                  Log.info("Saving the PDF file from the Webpage [" + description + "] ");
                  robot = new Robot();
                  Thread.sleep(10000);
                  ((JavascriptExecutor) getWebDriver()).executeScript("window.focus();"); 
                  maximize();
                  robot.keyPress(KeyEvent.VK_CONTROL);
                  robot.keyPress(KeyEvent.VK_SHIFT);
                  robot.keyPress(KeyEvent.VK_S);
                  robot.keyRelease(KeyEvent.VK_CONTROL);
                  robot.keyRelease(KeyEvent.VK_SHIFT);
                  robot.keyRelease(KeyEvent.VK_S);
                  Thread.sleep(3000);
                  StringSelection stringSelection = new StringSelection(pathToSavePDF);
                  Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                  clipboard.setContents(stringSelection, stringSelection);
                  robot.keyPress(KeyEvent.VK_CONTROL);
                  robot.keyPress(KeyEvent.VK_V);
                  robot.keyRelease(KeyEvent.VK_V);
                  robot.keyRelease(KeyEvent.VK_CONTROL);
                  robot.keyPress(KeyEvent.VK_ENTER);
                  robot.keyRelease(KeyEvent.VK_ENTER);
                  Log.info("PDF file saved successfully on Path  [" + pathToSavePDF + "] ");
                  Thread.sleep(5000);
            } catch (Exception e) {
                  log.error("Unable to save PDF on " + pathToSavePDF + "  " + description);
            }
      }

      /**
      * The function deletes file from resources folder (Maven projet) through input
      * parameter of file extension
      * 
       * @param extension : File extension to delete the file from resources folder
      */
      protected void deleteFilefromResources(String extension) {

            String[] list = new File(RESOURCES_FOLDER_PATH).list(new GenericExtFilter(extension));

            if (new File(RESOURCES_FOLDER_PATH).list(new GenericExtFilter(extension)).length > 0) {

                  for (String file : list) {
                        String temp = new StringBuffer(RESOURCES_FOLDER_PATH).append(File.separator).append(file).toString();

                        boolean isdeleted = new File(
                                    new StringBuffer(RESOURCES_FOLDER_PATH).append(File.separator).append(file).toString())
                                                .delete();
                        log.info("file : " + temp + " is deleted : " + isdeleted);
                  }
            } else
                  log.error("No such file found");
            return;
      }

      
      /**
      * Instances of classes that implement this interface are used to filter
      * filenames. These instances are used to filter directory listings in the
      * <code>list</code> method of class <code>File</code>, and by the Abstract
      * Window Toolkit's file dialog component.
      */
      private class GenericExtFilter implements FilenameFilter {

            private String ext;

            public GenericExtFilter(String ext) {
                  this.ext = ext;
            }

            public boolean accept(File dir, String name) {
                  return (name.endsWith(ext));
            }
      }

      /**
      * @param description   : PDF description
      * @param textToCompare : Text to search in the PDF
      * @param pathToReadPDF : Path to read PDF
      * @return String : Text to compare and occurrences
      * @throws Exception Generic exception
      */
      protected static synchronized String readtextFromPDF(String description, String textToCompare,
                  String pathToReadPDF) {
            try {
                  Log.info("Saving the PDF file from the Webpage [" + description + "] ");

                  PDDocument document = PDDocument.load(new File(pathToReadPDF));

                  PDFTextStripper pdfStripper = new PDFTextStripper();
                  String lines[] = pdfStripper.getText(document).split("\\r?\\n");
                  List<String> pdfLines = new ArrayList<>();
                  int counter = 0;
                  Boolean textFound = false;
                  for (String line : lines) {
                        pdfLines.add(line);
                        if (line.contains(textToCompare)) {
                              textFound = true;
                              counter++;
                        }
                  }
                  if (textFound == true) {
                        Log.info("Expected value found in the PDF ");
                        Log.info("Number of occurrences are " + counter);
                        return textToCompare + counter;
                  } else {
                        Log.info("Expected value cannot be found in the PDF ");
                        return "Expected value cannot be found in the PDF";
                  }
            } catch (Exception e) {
                  Log.error("Expected value cannot be found in the PDF ");
                  return textToCompare + 0;
            }

      }

      /* Commented code for PDF read (direct code)
      * public String readDataFromPdf() { try { switchToWindow("PDF");
      * savePDFfromWebPage("PDF", System.getProperty("user.dir") +
      * "\\src\\test\\resources\\download.pdf"); getWebDriver().close();
      * switchToWindow(""); PDDocument document = PDDocument .load(new
      * File(System.getProperty("user.dir") +
      * "\\src\\test\\resources\\download.pdf")); PDFTextStripper pdfStripper = new
      * PDFTextStripper(); String data = pdfStripper.getText(document);
      * document.close();
      * Files.deleteIfExists(Paths.get(System.getProperty("user.dir") +
      * "\\src\\test\\resources\\download.pdf")); return data.replaceAll("\\r?\\n",
      * " "); } catch (Exception e) { e.printStackTrace(); return "Exception thrown";
      * } }
      */

      
      // Added on 5 Feb Saurabh Sharma
      protected static synchronized void reportlogtext(String description) {
    	  Reporter.log(description + "<br />");
    }
}


