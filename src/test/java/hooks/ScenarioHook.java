package hooks;
import io.cucumber.java.*;
import io.qameta.allure.Allure;
import base.Base;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.*;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.remote.RemoteWebDriver;
public class ScenarioHook extends Base{
	
	@Before
	public void setUp()
	{
		try {
			System.out.println("reached hook1");

			RemoteWebDriver driver;
			System.out.println("reached hook2");
			String browser=getData("browser");
			System.out.println("browser:****"+browser);
			System.out.println("java -jar src/main/resources/selenium-server-4.51.0.jar standalone");
			if (browser.equals("chrome")) {
		      // driver = new ChromeDriver();  
				//System.out.println("***REACHED IF");
				
				  ChromeOptions options = new ChromeOptions();
				  options.setCapability("platformName", "Windows");
				  
				  driver = new RemoteWebDriver( new URL("http://localhost:4444"),options
				  
				  );
				 
		    }
			else if( browser.equals("edge"))
			{
//					 driver = new EdgeDriver();  
				//grid setup
				EdgeOptions options = new EdgeOptions();
				 options.setCapability("platformName", "Windows");

				 driver = new RemoteWebDriver(
	                    new URL("http://localhost:4444"),options
	                    
	            );
				 
			}

			else
			{
				FirefoxOptions options = new FirefoxOptions();
				 options.setCapability("platformName", "Windows");

				 driver = new RemoteWebDriver(
	                    new URL("http://localhost:4444"),options
	                    
	            );
			}
		    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
			setDriver(driver);

		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}	
	@After
	public void tearDown(Scenario scenario)
	{
		  if (!scenario.isFailed())
		  { byte[] screenshotBytes = ((TakesScreenshot)
				  getDriver()).getScreenshotAs(OutputType.BYTES);
				  
		Allure.addAttachment( "Screenshot", new ByteArrayInputStream(screenshotBytes) ); }
		getDriver().quit();
	}

}
