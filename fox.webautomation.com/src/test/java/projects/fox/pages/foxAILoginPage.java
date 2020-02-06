package projects.fox.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.baseproject.utility.BasePage;



public class foxAILoginPage extends BasePage{
      
	 public foxAILoginPage() {
            initPageFactory( this);
      }
      
      /*
     * open url
     */
     public void openLoginPage(String url) {
                       openUrl(url);
     }    
      
}
