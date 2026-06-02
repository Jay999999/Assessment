package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
public class BaseClass {
	  public static WebDriver driver;

	    @BeforeMethod
	    public void setup() {
	        driver = new ChromeDriver();
	        driver.manage().window().maximize();
	        driver.get("https://sauce-demo.myshopify.com/");
	    }

	    @AfterMethod
	    public void teardown() {
	        if (driver != null) {
	            driver.quit();
	        }
	    }
	}