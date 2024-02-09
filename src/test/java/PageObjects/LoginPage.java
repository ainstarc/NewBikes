package PageObjects;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import Utilities.Screenshots;

public class LoginPage extends BasePage {

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	private String folderTimeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
	private String folderName = "Login" + folderTimeStamp;

	@FindBy(id = "forum_login_title_lg")
	private WebElement loginSignup;

	@FindBy(xpath = "//span[text()='Google']")
	private WebElement googleButton;

	public boolean validateLoginSignup() {
		borderElement(loginSignup);
		Screenshots.captureScreen(driver, "LoginSignup", folderName);
		return checkVisible(loginSignup);
	}

	public void clickLoginSignup() {
		loginSignup.click();
	}

	public boolean validateGoogleButton() {
		borderElement(googleButton);
		Screenshots.captureScreen(driver, "GoogleButton", folderName);
		return checkClickable(googleButton);
	}

	public void clickGoogleButton() {
		googleButton.click();
	}

	private Set<String> windowHandles;

	public boolean navigateToGoogleLogin() {
		sleep(2000);
		windowHandles = driver.getWindowHandles();
		for (String window : windowHandles) {
			driver.switchTo().window(window);
			String title = driver.getTitle();

			if (title.contains("Google")) {
//				driver.manage().window().maximize();
				return true;
			}
		}
		return false;
	}

	@FindBy(id = "identifierId")
	WebElement emailBox;

	@FindBy(xpath = "//span[text()='Next']")
	WebElement nextButton;

	@FindBy(xpath = "//*[@id='yDmH0d']//span//span/parent::div")
	WebElement wrongEmailErrorMessage;

	@FindBy(xpath = "//h1/span")
	WebElement rejectLoginErrorMessage;

	public String verifyNegativeLogin(String email) {
		borderElement(emailBox);
		Screenshots.captureScreen(driver, "EmailInputBox", folderName);
		emailBox.sendKeys(email);
		borderElement(nextButton);
		Screenshots.captureScreen(driver, "NextButton", folderName);
		nextButton.click();
		if (checkVisible(wrongEmailErrorMessage)) {
			borderElement(wrongEmailErrorMessage);
			Screenshots.captureScreen(driver, "WrongEmail", folderName);
			return wrongEmailErrorMessage.getText();
		} else if (checkVisible(rejectLoginErrorMessage)) {
			borderElement(rejectLoginErrorMessage);
			Screenshots.captureScreen(driver, "RejectEmail", folderName);
			return rejectLoginErrorMessage.getText();
		} else {
			return "";
		}

	}
}
