package org.example.dataprovider;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DataProviderParallelTests {

	public WebDriver driver;

	// A new ThreadLocal is instantiated for each test class, since it’s in the
	// BeforeClass annotation.
	private static final ThreadLocal<WebDriver> WEB_DRIVER_THREAD_LOCAL = new ThreadLocal<WebDriver>();

	@BeforeMethod
	public void setUp() throws Exception {
		try {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "//src//test//resources//chromedriver");
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("--remote-allow-origins=*");
			driver = new ChromeDriver(chromeOptions);
			WEB_DRIVER_THREAD_LOCAL.set(driver);
			System.out.println("Before method Thread Id:" + Thread.currentThread().getId());
		} catch (Exception e) {
			throw e;
		}
	}

	@Test(dataProvider = "dataprovider", dataProviderClass = DataProviderDemo.class)
	public void invalidLoginTest(String userName, String password, String errorMessage) throws Exception {
		try {

			driver = WEB_DRIVER_THREAD_LOCAL.get();
			driver.manage().window().maximize();
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			driver.get("https://opensource-demo.orangehrmlive.com/");

			Thread.sleep(2000);
			driver.findElement(By.name("username")).sendKeys(userName);
			System.out.println("Username :" + userName);

			Thread.sleep(2000);
			driver.findElement(By.name("password")).sendKeys(password);
			System.out.println("password :" + password);

			driver.findElement(By.xpath("//button[@type='submit']")).click();

			Thread.sleep(2000);
			String expectedError = driver.findElement(By.xpath("//div[@class='orangehrm-login-error']/div[1]/div[1]/p"))
					.getText();
			System.out.println("Error Message :" + expectedError);
			Assert.assertTrue(expectedError.contains(errorMessage));

		} catch (Exception e) {
			throw e;
		}
	}

	@AfterMethod
	public void tearingDownDriver() {
		try {
			//WebDriver driver = WEB_DRIVER_THREAD_LOCAL.get();
			System.out.println("After method Thread Id:" + Thread.currentThread().getId());
			if (driver != null) {
				driver.quit();
			}
		} catch (Exception e) {
			throw e;
		}
	}
}