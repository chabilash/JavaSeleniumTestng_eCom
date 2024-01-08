package com.webstar.testcases;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TC001_SearchAndCartPage {
	//Driver Initialization
	WebDriver driver = null;
	
	
	
	@BeforeTest
	public void setup() {
		WebDriverManager.chromedriver().setup();
		
		//Trigger Chrome Browser
		driver = new ChromeDriver();
		
		// Maximize the browser using maximize() method
		driver.manage().window().maximize();
		
		// Using Implicit Wait & PageLoad TimeOutfor Synchronization
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(35));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(35));
		driver.manage().deleteAllCookies();
		
		// Launching website
		driver.get("https://www.webstaurantstore.com/");
	}
	
	@Test
	public void TC_scenario() throws Exception {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(20));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		driver.findElement(By.id("searchval")).sendKeys("stainless work table");
		driver.findElement(By.xpath("//button[@Value='Search']")).click();
		List<WebElement> elements = driver.findElements(By.xpath("//div[@id='product_listing']/div/div/a/span")); 
		int count = elements.size();
		int flag = 0;
		System.out.println("Count of products is :" +count);
		for (WebElement element : elements) {
			flag++;
			if (element.getText().contains("Table")) {
				js.executeScript("arguments[0].scrollIntoView(true)", element);
				System.out.println("Table is available");
				//continue;
			} else {
				System.out.println("Table is not available");
			}
			if (flag == count) {
				element.click();
			}
		}
		System.out.println("Table verification completed");
		WebElement quantity = driver.findElement(By.xpath("//input[@id='qty']"));
		quantity.clear();
		quantity.sendKeys("2");
		driver.findElement(By.xpath("//input[@id='buyButton']")).click();
		System.out.println("Added to cart");
		WebElement elementViewCart = driver.findElement(By.xpath("//a[@class='btn btn-primary']/i"));
		elementViewCart.click();
		System.out.println("Clicked on View cart");
		Thread.sleep(2000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//button[@class='emptyCartButton btn btn-mini btn-ui pull-right']")));
		
		driver.findElement(By.xpath("//button[@class='emptyCartButton btn btn-mini btn-ui pull-right']")).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//button[text()='Empty']")));
		driver.findElement(By.xpath("//button[text()='Empty']")).click();
		
		//Assertion	
		String actualText = "Your cart is empty.";
		WebElement empEle = driver.findElement(By.xpath("//div[@class='empty-cart__text']/p"));
		String expectedText = empEle.getText();
		Assert.assertEquals(actualText, expectedText);
		Thread.sleep(2000);
		
		//Click Continue Shopping(Navigate to Home Page)
		driver.findElement(By.xpath("//ol[@class='breadcrumb']/li[1]")).click();
		Thread.sleep(2000);
		
		
	}
	
	@AfterTest
	public void teardown() {
		driver.quit();
	}

}
