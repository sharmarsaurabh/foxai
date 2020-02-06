package projects.fox.tests;

import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.baseproject.base.BaseTest;

import projects.fox.pages.foxAIPage;
import projects.fox.pages.foxAILoginPage;

public class foxaitest extends BaseTest {
      	
	String URL;
	String companyName = "Honda Motor Co";
	foxAIPage foxAIPage;
	foxAILoginPage foxAILoginPage;
    
      
      @BeforeClass
      public void test() throws InterruptedException {
            
    	  //With config file
    	  
	   foxAILoginPage= new foxAILoginPage();
       foxAILoginPage.openLoginPage(getURL("URL"));         
      }
      
      @Test(enabled=true,priority=0)
      public void test_tour_cashweekly() throws InterruptedException {
            
    	  foxAIPage=new foxAIPage();
    	  foxAIPage.wait_tour_cashweekly();
    	  foxAIPage.click_tour_cashweekly();
    	  
    	  foxAIPage.wait_cash_pacing();
    	  foxAIPage.report_log_text("Tour Cash weekly Icon Found");
      }
      
      @Test(enabled=true,priority=1)
      public void test_tour_revenue() throws InterruptedException {
    	  
    	  foxAIPage.click_tour_revenue();
    	  
    	  Thread.sleep(10000);
    	  foxAIPage.wait_entertext_revenue_company();
    	  foxAIPage.entertext_revenue_company(companyName);
    	  Thread.sleep(10000);
    	  foxAIPage.wait_tour_revenue_company();
    	  foxAIPage.click_tour_revenue_company();
    	  
    	  foxAIPage.wait_tour_revenue_company_name(companyName);
    	  foxAIPage.report_log_text("Tour Revenue Icon Found");
      }
      
      @Test(enabled=true,priority=2)
      public void test_tour_inventory() throws InterruptedException {
    	 
    	   
    	  foxAIPage.click_tour_inventory();
    	  foxAIPage.check_inventory_gross();
    	  foxAIPage.report_log_text("Tour Inventory Icon Found");
      }
      
      @Test(enabled=true,priority=3)
      public void test_target_audience_builder() throws InterruptedException {

    	  foxAIPage.click_target_audience_builder();
    	  foxAIPage.wait_target_audience_builder_text();
    	  foxAIPage.report_log_text("Target Audience Builder Icon Found");
    	  
      }
      
 
}

            
      
 
