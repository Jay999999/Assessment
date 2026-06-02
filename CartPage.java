package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage {

	   WebDriver driver;
	    WebDriverWait wait;
	    Actions actions;

	    By addToCartBtn = By.xpath("//*[@value='Add to Cart']");
	    
	    // Cart count in header — confirms item was added
	    By cartCountHeader = By.xpath("(//*[contains(text(),'My Cart')])[1]");
	    
	    By cartProductName = By.xpath("//table//a[contains(@href,'/products/')]");

	    public CartPage(WebDriver driver) {
	        this.driver  = driver;
	        this.wait    = new WebDriverWait(driver, Duration.ofSeconds(20));
	        this.actions = new Actions(driver);
	    }

	    public void addToCart() {
	        wait.until(ExpectedConditions.urlContains("/products/"));
	        System.out.println("On product page: " + driver.getCurrentUrl());

	        WebElement btn = wait.until(
	            ExpectedConditions.elementToBeClickable(addToCartBtn)
	        );
	        System.out.println("Button found: " + btn.getAttribute("value"));

	        actions.scrollToElement(btn).perform();
	        actions.moveToElement(btn).click().perform();
	        System.out.println("Clicked Add to Cart");

	        // ✅ Wait until cart count changes from (0) to (1) — confirms item added
	        wait.until(ExpectedConditions.textToBePresentInElementLocated(
	            cartCountHeader, "(1)"
	        ));
	        System.out.println("Item confirmed added to cart!");
	    }

	    public void openCart() {
	        driver.get("https://sauce-demo.myshopify.com/cart");
	        wait.until(ExpectedConditions.urlContains("/cart"));
	        System.out.println("On cart page: " + driver.getCurrentUrl());
	    }

	    public String getCartProductName() {
	        WebElement product = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(
	                By.partialLinkText("Noir jacket")
	            )
	        );
	        return product.getText().trim();
	    }
	}