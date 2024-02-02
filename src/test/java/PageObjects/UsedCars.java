package PageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

public class UsedCars extends BasePage {

	public UsedCars(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//a[normalize-space()='Used Cars']")
	private WebElement usedCars;

	private WebElement carCity;

	public void scrollToTop() {
		jse.executeScript("window.scrollTo(0,0)");
		sleep(2000);
	}

	public boolean validateUsedCars() {
		return usedCars.isDisplayed();
	}

	public void hoverUsedCars() {
		actions.moveToElement(usedCars).perform();
	}

	public boolean validateCity(String city) {
		String xpathString = "//li/span[text()='" + city + "']";

		try {
			carCity = driver.findElement(By.xpath(xpathString));
			return true;
		} catch (Exception e) {
			System.out.println("City not found!");
			return false;
		}
	}

	public void clickCity() {
		carCity.click();
	}

	@FindAll(@FindBy(xpath = "//div[text()='Popular Models']/following-sibling::div[1]//li/label"))
	private List<WebElement> popularModelsElement;

	private String[] popularModelsText;

	public String[] popularModelList() {
		int size = popularModelsElement.size();
		popularModelsText = new String[size];
		jse.executeScript("arguments[0].scrollIntoView();", popularModelsElement.get(0));

		int i = 0;

		for (WebElement model : popularModelsElement) {
			popularModelsText[i++] = model.getText();
			System.out.println(model.getText());
		}

		return popularModelsText;
	}

	@FindBy(xpath = "//img[@data-track-label='zw-header-logo']")
	WebElement headerLogo;

	public void navigateToHomePage() {
		headerLogo.click();
	}

}
