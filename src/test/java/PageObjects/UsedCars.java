package PageObjects;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import Utilities.Screenshots;

public class UsedCars extends BasePage {

	public UsedCars(WebDriver driver) {
		super(driver);
	}

	private String folderTimeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
	private String folderName = "UpcomingBikes" + folderTimeStamp;

	@FindBy(xpath = "//a[normalize-space()='Used Cars']")
	private WebElement usedCars;

	private WebElement carCity;

	public void scrollToTop() {
		jse.executeScript("window.scrollTo(0,0)");
	}

	public boolean validateUsedCars() {
		return checkVisible(usedCars);
	}

	public void hoverUsedCars() {
		borderElement(usedCars);
		actions.moveToElement(usedCars).perform();
		Screenshots.captureScreen(driver, "CarHover", folderName);
	}

	public boolean validateCity(String city) {
		String xpathString = "//li/span[text()='" + city + "']";

		try {
			carCity = driver.findElement(By.xpath(xpathString));
			borderElement(carCity);
			Screenshots.captureScreen(driver, city, folderName);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@FindBy(css = "input#usedCarCity")
	WebElement locationSearchBox;

	@FindBy(xpath = "//li[@class='ui-menu-item']/a")
	WebElement citySuggestion;

	public boolean searchCity(String city) {
		if (searchOtherCity(city)) {
			return true;
		} else {
			driver.navigate().back();
			return searchCityClick(city);

		}

	}

	@FindBy(xpath = "//span[text()=' Other Cities']")
	WebElement otherCities;

	@FindBy(id = "gs_input5")
	WebElement searchOtherCity;

	boolean searchOtherCity(String city) {
		try {
			if (checkClickable(otherCities))
				otherCities.click();
			sleep(10000);
			searchOtherCity.sendKeys(city);
			sleep(5000);
			String otherCityXpath = "//a[text()='" + city + "']";
			WebElement otherCity = driver.findElement(By.xpath(otherCityXpath));
			if (checkClickable(otherCity))
				otherCity.click();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	boolean searchCityClick(String city) {
		try {
			usedCars.click();
			locationSearchBox.clear();
			locationSearchBox.sendKeys(city);

			citySuggestion = wait.until(ExpectedConditions.elementToBeClickable(citySuggestion));
			citySuggestion.click();
			return true;

		} catch (Exception e) {
			return false;
		}

	}

	public void clickCity() {
		carCity.click();
	}

	@FindAll(@FindBy(xpath = "//div[text()='Popular Models']/following-sibling::div[1]//li/label"))
	private List<WebElement> popularModelsElement;

	private String[] popularModelsText;

	@FindBy(xpath = "//span[text()='Brand and Model']")
	private WebElement brandModel;

	@FindBy(xpath = "//span[text()='Brand and Model']//ancestor::li")
	private WebElement brandModelLi;

	public String[] popularModelList() {
		int size = popularModelsElement.size();
		popularModelsText = new String[size];
//		jse.executeScript("arguments[0].scrollIntoView();", brandModelLi);
		jse.executeScript("window.scrollBy(0,600);");
		borderElement(brandModelLi);
		bgColor(brandModelLi);
		Screenshots.captureScreen(driver, "PopularModels", folderName);

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
		jse.executeScript("arguments[0].scrollIntoView();", headerLogo);
		headerLogo.click();
	}

}
