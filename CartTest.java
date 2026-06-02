package tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.BaseClass;
import pages.CartPage;
import pages.ViewPage;

public class CartTest extends BaseClass {

    CartPage cart;
    ViewPage view;

    @BeforeMethod
    public void setupPages() {
        cart = new CartPage(driver);
        view = new ViewPage(driver);
    }

    @Test
    public void verifyCartProductName() {

        // Step 1: Select product from home page & capture name
        String selectedProduct = view.selectProduct();
        System.out.println("Selected Product: " + selectedProduct);

        // Step 2: Add to cart
        cart.addToCart();

        // Step 3: Open cart
        cart.openCart();

        // Step 4: Get product name from cart
        String cartProduct = cart.getCartProductName();
        System.out.println("Product in Cart: " + cartProduct);

        // Step 5: Assert both names match
        Assert.assertTrue(cartProduct.contains(selectedProduct),
        	    "Product name in cart does not match selected product!");
    }
}