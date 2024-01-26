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
		// TODO Auto-generated constructor stub
	}

	@FindBy(xpath = "//a[normalize-space()='Used Cars']")
	WebElement usedCars;

	WebElement carCity;

	public void findUsedCars(String city) {
		actions = new Actions(driver);
//		jse.executeScript("arugments[0].scrollIntoView();",usedCars);
		jse.executeScript("window.scrollTo(0,0)");
		sleep(2000);
		actions.moveToElement(usedCars).perform();
		String xpathString = "//li/span[text()='" + city + "']";

		carCity = driver.findElement(By.xpath(xpathString));

		carCity.click();
	}

	@FindAll(@FindBy(xpath = "//div[text()='Popular Models']/following-sibling::div[1]//li/label"))
	List<WebElement> popularModelsElement;

	String[] popularModelsText;

	public String[] popularModelList() {
		int size = popularModelsElement.size();
		popularModelsText = new String[size];
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
