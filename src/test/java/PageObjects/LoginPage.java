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
	private WebElement loginSignup;

	@FindBy(xpath = "//span[text()='Google']")
	private WebElement googleButton;

	public boolean validateLoginSignup() {
		return checkVisible(loginSignup);
	}

	public void clickLoginSignup() {
		loginSignup.click();
	}

	public boolean validateGoogleButton() {
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
//	@FindBy(xpath = "//input[@id='identifierId']//ancestor::div[3]//following-sibling::div//span/parent::div")
	@FindBy(xpath = "//*[@id='yDmH0d']//span//span/parent::div")
	WebElement wrongEmailErrorMessage;
	@FindBy(xpath = "//h1/span")
	WebElement rejectLoginErrorMessage;

	public String verifyNegativeLogin(String email) {
		emailBox.sendKeys(email);
		nextButton.click();
		if (checkVisible(wrongEmailErrorMessage))
			return wrongEmailErrorMessage.getText();
		else if (checkVisible(rejectLoginErrorMessage))
			return rejectLoginErrorMessage.getText();
		else {
			return "";
		}

	}
}
