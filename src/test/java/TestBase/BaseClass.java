package TestBase;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
	public static Logger logger;

	@BeforeClass(groups = { "smoke", "sanity", "regression" })
	@Parameters({ "browser", "os", "environment" })
	public void setUp(String br, String os, String environment) throws IOException {
		logger = LogManager.getLogger(this.getClass());

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
				break;
			case "edge":
				capabilities.setBrowserName("MicrosoftEdge");
				break;
			default:
				System.out.println("No matching browser..");
				return;
			}

			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);

		} else {
			if (br.equalsIgnoreCase("chrome")) {
				driver = chromeWithOptions();
			} else if (br.equalsIgnoreCase("edge")) {
				driver = edgeWithOptions();
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

	public static final String fileName = "data";

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

	public static String randomEmail(int size) {
		Random random = new Random();
		StringBuilder username = new StringBuilder();

		for (int i = 0; i < size; i++) {
			char randomChar = (char) ('a' + random.nextInt(26));
			username.append(randomChar);
		}

		String domain = "gmail.com";
		String email = username.toString() + "@" + domain;
		return email;
	}

}
