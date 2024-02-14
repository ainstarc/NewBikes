package PageObjects;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

	WebDriver driver;
	static JavascriptExecutor jse;
	static Actions actions;
	static WebDriverWait wait;

	public BasePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		jse = (JavascriptExecutor) driver;
		actions = new Actions(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	public static boolean checkClickable(WebElement element) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean checkVisible(WebElement element) {
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void borderElement(WebElement element) {
		try {
			jse.executeScript("arguments[0].style.border='2px solid green'", element);
		} catch (Exception e) {

		}
	}

	public static void bgColor(WebElement element) {
		try {
			jse.executeScript("arguments[0].style.backgroundColor = 'yellow'", element);
		} catch (Exception e) {

		}
	}

	public static boolean URLConnectionCheck(String urlToCheck) {
		try {
			URL url = new URL(urlToCheck);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			;
			int responseCode = connection.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) {
				System.out.println("URL is reachable with status code " + responseCode);
				return true;
			} else {
				System.out.println("URL is not reachable with status code " + responseCode);
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
