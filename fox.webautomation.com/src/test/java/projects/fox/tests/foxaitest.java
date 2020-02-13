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
      
      @Test(enabled=true,priority=0,dataProvider="ExcelTestData",description = "Cash Weekly Data")
      public void test_tour_cashweekly(Hashtable<String, String> mainTabs) throws InterruptedException {
            
    	  foxAIPage=new foxAIPage();
    	  foxAIPage.wait_tour_cashweekly();
    	  foxAIPage.report_log_text("Tour Cash weekly Icon Found");
    	  Thread.sleep(10000);
    	  foxAIPage.click_tour_cashweekly();
    	  
    	  foxAIPage.wait_cash_pacing();
   	  
    	  assertEquals(foxAIPage.check_cy_booked(),mainTabs.get("TestData1"),"Verify CY Booked");
    	  foxAIPage.report_log_text("CY Booked value Verified");
    	
    	  assertEquals(foxAIPage.check_current_forecast_total(),mainTabs.get("TestData2"),"Verify Current Forecast Total");
    	  foxAIPage.report_log_text("Current Forecast Total value Verified");
    	  
    	  assertEquals(foxAIPage.check_py_booked_percentage(),mainTabs.get("TestData3"),"Verify PY Booked Percentage");
    	  foxAIPage.report_log_text("PY Booked Percentage value Verified");
    	  
    	  List<String> myData = foxAIPage.check_total_ad_sales();
    	  assertEquals(myData.get(5),mainTabs.get("TestData4"),"Verify Total Ad Sales Velocity");
    	  foxAIPage.report_log_text("Total Ad Sales Velocity value Verified");
    	  
    	  assertEquals(foxAIPage.check_weekly_gain_loss(),mainTabs.get("TestData5"),"Verify Weekly Gain/Loss By Property");
    	  foxAIPage.report_log_text(" Weekly Gain/Loss By Property value Verified");
    	  
      }
      
      @Test(enabled=true,priority=1)
      public void test_tour_revenue() throws InterruptedException {
    	  
    	  foxAIPage.click_tour_revenue();
    	  foxAIPage.report_log_text("Tour Revenue Icon Found");
    	  
    	  Thread.sleep(10000);
    	  foxAIPage.wait_entertext_revenue_company();
    	  foxAIPage.entertext_revenue_company(companyName);
    	  Thread.sleep(10000);
    	  foxAIPage.wait_tour_revenue_company();
    	  foxAIPage.click_tour_revenue_company();
    	  Thread.sleep(10000);
    	  foxAIPage.wait_tour_revenue_company_name(companyName);
    	  
      }
      
      @Test(enabled=true,priority=2)
      public void test_tour_inventory() throws InterruptedException {

    	  foxAIPage.click_tour_inventory();
    	  foxAIPage.report_log_text("Tour Inventory Icon Found");
    	  Thread.sleep(10000);
    	  foxAIPage.check_inventory_gross();
      }
      
      @Test(enabled=true,priority=3)
      public void test_target_audience_builder() throws InterruptedException {

    	  foxAIPage.click_target_audience_builder();
    	  foxAIPage.report_log_text("Target Audience Builder Icon Found");
    	  Thread.sleep(10000);
    	  foxAIPage.wait_target_audience_builder_text();

    	  
      }
      
      @DataProvider(name="ExcelTestData")
      public Object[][] hashdata(Method m) {


            return new Object[][]{  
                  {readTestDataByMethodName(m.getName())}
            };

      } 
}

            
      
 
