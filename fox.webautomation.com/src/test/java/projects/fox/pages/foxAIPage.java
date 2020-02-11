package projects.fox.pages;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.baseproject.utility.BasePage;

public class foxAIPage extends BasePage {
      
      //Locators Webelement
	
	 // ICONS
      @FindBy(xpath = "//*[@id=\"tour-cashweekly\"]/a") WebElement click_tour_cashweekly;
      @FindBy(xpath = "//*[@id=\"tour-revenue\"]/a") WebElement click_tour_revenue;    
      @FindBy(xpath = "//*[@id=\"tour-inventory\"]/a") WebElement click_tour_inventory;
      @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/aside/nav/div/ul/li[5]/a") WebElement click_target_audience_builder;
      
     // Text on Cash Weekly Page
      @FindBy(xpath ="//*[@id=\"app\"]/div[2]/div/main/div/div/div/div[2]/div/div[1]/div/div[2]/div/h3") WebElement check_cy_booked;
      @FindBy(xpath ="//*[@id=\"app\"]/div[2]/div/main/div/div/div/div[2]/div/div[3]/div/div[1]/div/h3") WebElement check_current_forecast_total;
      @FindBy(xpath ="//*[@id=\"app\"]/div[2]/div/main/div/div/div/div[2]/div/div[4]/div/div[5]/div/h3") WebElement check_py_booked_percentage;
      @FindBy(className ="datalabel-line-total-ad-sales-quarter-to-date-sales-velocity-chart") List<WebElement> check_total_ad_sales ;
      				 
    
    
      
     // Other Elements 
      @FindBy(xpath = "/html/body/div/div[2]/div/main/div/div/div/div[2]/div/div[1]/h1") WebElement check_cash_pacing;
      @FindBy(xpath = "/html/body/div/div[2]/div/main/div/div/div/div/div[1]/div/div/input") WebElement enter_tour_revenue_company;
      @FindBy(xpath = "/html/body/div/div[2]/div/main/div/div/div/div/div[2]/div/div/div/div[2]/div") WebElement click_tour_revenue_company;
      @FindBy(xpath = "//*[@id='app']/div[2]/div/main/div/div/div/div/div/div[2]/div[1]/h1") WebElement check_tour_revenue_company_name;
      @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/main/div[2]/div/div[1]/div[2]/h1") WebElement check_inventory_gross;
      @FindBy(xpath = "//*[@id=\"printFullPage\"]/div[2]/div/section/div/div[1]/div[1]/div/h1") WebElement check_target_audience_builder_text;
      
	 
	 //Constructor
            public foxAIPage() {
                  initPageFactory( this);
            }
      
      
       //Properties 
            
            public void wait_tour_cashweekly() {
                
            	waitForElementToBePresent("Tour Cashweekly", click_tour_cashweekly,500000);
            	
          }
            
            public void click_tour_cashweekly() {
                
            	clickOnElement("Tour Cashweekly", click_tour_cashweekly );
            	
          }
            
            public void wait_cash_pacing() {
                
            	waitForElementToBePresent("Cash Pacing", check_cash_pacing,500000);
            	
          } 
            
            public String check_cy_booked() {
            	return getText("Checking CY Booked",check_cy_booked);
            	
            }
            
            public String check_current_forecast_total() {
            	return getText("Checking Current Forecast Total",check_current_forecast_total);
            	
            }
            
            public String check_py_booked_percentage() {
            	return getText("Checking PY Booked Percentage",check_py_booked_percentage);
            	
            }
            
            
            public List<String> check_total_ad_sales() {
            	return getTextFromAllElements("Checking Total Ad Sales Velocity",check_total_ad_sales);
            	
            }
            
            
            public void check_cash_pacing() {
                
            	checkDisplayedElement("Cash Pacing", check_cash_pacing );
            	
          }
            
            public void wait_tour_revenue() {
                
            	waitForElementToBePresent("Tour Revenue", click_tour_revenue,500000 );
            	
          }
            
            
            public void click_tour_revenue() {
                
            	clickOnElement("Tour Revenue", click_tour_revenue );
            	
          }
            
            public void wait_entertext_revenue_company() {
                
            	waitForElementToBePresent("Tour Revenue Company Text Box", enter_tour_revenue_company,500000);
            	
          } 
            
            public void entertext_revenue_company(String text) {
                
            	enterText("Tour Revenue Company", enter_tour_revenue_company, text);
         }
            
            public void wait_tour_revenue_company() {
                
            	waitForElementToBePresent("Tour Revenue Company Output", click_tour_revenue_company,500000 );
            	
          }
            
            public void click_tour_revenue_company() {
                
            	clickOnElement("Tour Revenue Company Output", click_tour_revenue_company );
            	
          }
            
            public void wait_tour_revenue_company_name(String text) {
	 
            	waitForElementToBePresent(text, check_tour_revenue_company_name,500000);
            	
          } 
            
            public void check_tour_revenue_company_page(String text) {
                
            	checkDisplayedElement(text, check_tour_revenue_company_name );
            	
          }
        
            public void wait_tour_inventory() {
                
            	waitForElementToBePresent("Tour Inventory", click_tour_inventory,500000 );
            	
          }
            public void click_tour_inventory() {
                
            	clickOnElement("Tour Inventory", click_tour_inventory );
            	
          }
            

            public void wait_check_inventory_gross() {
	 
            	waitForElementToBePresent("Inventory : Gross", check_inventory_gross,500000);
            	
          } 
            
            public void check_inventory_gross() {
                
            	checkDisplayedElement("Inventory : Gross", check_inventory_gross );
            	
          }
            
            public void wait_target_audience_builder() {
                
            	waitForElementToBePresent("Target Audience Builder", click_target_audience_builder,500000 );
            	
          }
            public void click_target_audience_builder() {
                
            	clickOnElement("Target Audience Builder", click_target_audience_builder );
            	
          }
            
            public void wait_target_audience_builder_text() {
           	 
            	waitForElementToBePresent("TARGET AUDIENCE BUILDER", check_target_audience_builder_text,500000);
            	
          } 
            
            public void check_target_audience_builder_text() {
                
            	checkDisplayedElement("TARGET AUDIENCE BUILDER", check_target_audience_builder_text);
            	
          }
            
            public void report_log_text(String str) {
            	reportlogtext(str);
            }
            //Methods
      
      
}
