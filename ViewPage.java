package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.BaseClass;

public class ViewPage {

    WebDriver driver;
    WebDriverWait wait;
    Actions actions;

    By greyJacketLink = By.xpath("//*[text()='Noir jacket']");

    // ✅ Constructor — only assign, never call findElement() here
    public ViewPage(WebDriver driver) {
        this.driver  = driver;
        this.wait    = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.actions = new Actions(driver);
    }

    public String selectProduct() {
        WebElement prod = wait.until(
            ExpectedConditions.visibilityOfElementLocated(greyJacketLink)
        );
        String productName = prod.getText().trim();
        actions.scrollToElement(prod).perform();
        actions.moveToElement(prod).click().perform();
        return productName;
    }
}