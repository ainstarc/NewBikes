package TestBase;

import java.time.Duration;
import java.util.ResourceBundle;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import Utilities.ExcelUtilities;

public class BaseClass {

	public static WebDriver driver;
	public static ResourceBundle rb = ResourceBundle.getBundle("config");
	public static ExcelUtilities excelUtilities = new ExcelUtilities();

	@BeforeTest
	public void setUp() {
		driver = new ChromeDriver();

		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.get(getURL());
		driver.manage().window().maximize();
	}

	@AfterTest
	public void tearDown() {

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.quit();
	}

	public String getURL() {
		return rb.getString("baseURL");
	}

	public String fileName() {
		return rb.getString("fileName");
	}

}
