package TestBase;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import Utilities.ExcelUtilities;

public class BaseClass {

	public static WebDriver driver;
	public static ResourceBundle rb = ResourceBundle.getBundle("config");
	public static ExcelUtilities excelUtilities = new ExcelUtilities();
	public static final boolean expected = true;
	static String folderTimeStamp;
	static ChromeOptions chromeOptions;
	static EdgeOptions edgeOptions;

	@BeforeClass(groups = { "smoke", "sanity", "regression" })
	@Parameters({ "browser", "os", "environment" })
	public void setUp(String br, String os, String environment) throws IOException {

		if (environment.equalsIgnoreCase("remote")) {
			DesiredCapabilities capabilities = new DesiredCapabilities();

			if (os.equalsIgnoreCase("windows")) {
				capabilities.setPlatform(Platform.WIN11);
			} else if (os.equalsIgnoreCase("mac")) {
				capabilities.setPlatform(Platform.MAC);
			} else {
				System.out.println("No matching os!");
				return;
			}

			switch (br.toLowerCase()) {
			case "chrome":
				capabilities.setBrowserName("chrome");
				fileName = fileNameChrome();
				break;
			case "edge":
				capabilities.setBrowserName("MicrosoftEdge");
				fileName = fileNameEdge();
				break;
			default:
				System.out.println("No matching browser..");
				return;
			}

			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);

		} else {
			if (br.equalsIgnoreCase("chrome")) {
				driver = chromeWithOptions();
				fileName = fileNameChrome();
			} else if (br.equalsIgnoreCase("edge")) {
				driver = edgeWithOptions();
				fileName = fileNameEdge();
			}
		}
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.get(getURL());
		driver.manage().window().maximize();
		folderTimeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
	}

	@AfterClass(groups = { "smoke", "sanity", "regression" })
	public void tearDown() {

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.quit();
	}

	static ChromeDriver chromeWithOptions() {
		chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("disable-notifications");
		ChromeDriver driver = new ChromeDriver(chromeOptions);
		return driver;
	}

	static EdgeDriver edgeWithOptions() {
		edgeOptions = new EdgeOptions();
		edgeOptions.addArguments("disable-notifications");
		EdgeDriver driver = new EdgeDriver(edgeOptions);
		return driver;

	}

	static String getURL() {
		return rb.getString("baseURL");
	}

	public static String fileName;

	static void fileName(String br) {
		if (br.equalsIgnoreCase("chrome"))
			fileName = fileNameChrome();
		else if (br.equalsIgnoreCase("edge"))
			fileName = fileNameEdge();
	}

	static String fileNameChrome() {
		return rb.getString("fileNameChrome");
	}

	static String fileNameEdge() {
		return rb.getString("fileNameEdge");
	}

	public static String captureScreen(String tname) {

		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "\\screenshots\\" + folderTimeStamp + "\\" + tname + "_"
				+ timeStamp + ".png";

		try {
			FileUtils.copyFile(source, new File(destination));
		} catch (Exception e) {
			e.getMessage();
		}
		return destination;

	}

}
