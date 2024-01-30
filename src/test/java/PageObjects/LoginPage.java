package PageObjects;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

	public LoginPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id = "forum_login_title_lg")
	WebElement loginSignup;

	@FindBy(xpath = "//span[text()='Google']")
	WebElement googleButton;

	public void clickLoginSignup() {
		loginSignup.click();

		sleep(3000);

		googleButton.click();
	}

	Set<String> windowHandles;

	public void navigateToGoogleLogin() {
		sleep(2000);
		windowHandles = driver.getWindowHandles();

//		System.out.println(windowHandles.size());

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
//		driver.findElement(By.id("identifierId")).sendKeys(email);
		emailBox.sendKeys(email);
		nextButton.click();

		sleep(2000);

		String msg = errorMessage.getText();

		return msg;

	}
}
