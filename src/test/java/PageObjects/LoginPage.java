package PageObjects;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "forum_login_title_lg")
	WebElement loginSignup;

	@FindBy(xpath = "//span[text()='Google']")
	WebElement googleButton;

	public boolean validateLoginSignup() {
		sleep(2000);
		return loginSignup.isDisplayed();
	}

	public void clickLoginSignup() {
		loginSignup.click();
	}

	public boolean validateGoogleButton() {
		sleep(2000);
		return googleButton.isDisplayed();
	}

	public void clickGoogleButton() {
		googleButton.click();
	}

	Set<String> windowHandles;

	public void navigateToGoogleLogin() {
		sleep(2000);
		windowHandles = driver.getWindowHandles();

		if (windowHandles.size() == 1) {
			clickGoogleButton();
			navigateToGoogleLogin();
		}

		for (String window : windowHandles) {
			driver.switchTo().window(window);
			String title = driver.getTitle();

			if (title.contains("Google")) {
				driver.manage().window().maximize();
				break;
			}

		}

	}

	@FindBy(id = "identifierId")
	WebElement emailBox;
	@FindBy(xpath = "//span[text()='Next']")
	WebElement nextButton;
	@FindBy(xpath = "//input[@id='identifierId']//ancestor::div[3]//following-sibling::div//span/parent::div")
	WebElement errorMessage;

	public String verifyNegativeLogin(String email) {
		emailBox.sendKeys(email);
		nextButton.click();

		sleep(2000);

		String msg = errorMessage.getText();

		return msg;

	}
}
