package PageObjects;

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
//			return clickElement;
			return true;
		} catch (Exception e) {
//			return null;
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
		jse.executeScript("arguments[0].style.border='2px solid red'",element);
	}
}
