package com.webstar.testcases;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TC002_GettingDataFromExcelDataProvider {
	
	WebDriver driver = null;
	
	@BeforeTest
	public void setup() {
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(35));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(35));
		driver.manage().window().maximize();
		
		
	}
	
	@Test (dataProvider = "getData" , dataProviderClass = DataProviderClass.class)
	public void launch_test(String userName, String password) throws Exception {
		driver.get("https://naveenautomationlabs.com/opencart/");
		String title = driver.getTitle();
		System.out.println("Title of the Page is: " +title);
		System.out.println(userName + " : " + password);
		Thread.sleep(5000);
	}
	
	@AfterTest
	public void teardown() {
		driver.quit();
	}

}
