package PageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import Utilities.Screenshots;

public class UpcomingBikes extends BasePage {

	public UpcomingBikes(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//a[normalize-space()='New Bikes']")
	private WebElement newBikes;

	@FindBy(xpath = "//span[text()='Upcoming Bikes']")
	private WebElement upcomingBikes;

	@FindBy(xpath = "//li[text()='Upcoming']")
	private WebElement upcoming;

	public boolean validateNewBikes() {
		return newBikes.isDisplayed();
	}

	public void newBikeHover() {
		actions.moveToElement(newBikes).perform();
	}

	public boolean validateUpcomingBikes() {
		return upcomingBikes.isDisplayed();
	}

	public void clickUpcomingBikes() {
		sleep(2000);
		upcomingBikes.click();
	}

	@FindBy(id = "makeId")
	private WebElement manufacturers;
	private Select manufacturerSelect;

	public boolean validateManufacturers() {
		return manufacturers.isDisplayed();
	}

	public void selectManufacturers(String manufacturer) {
		manufacturerSelect = new Select(manufacturers);
		manufacturerSelect.selectByVisibleText(manufacturer);
	}

	@FindBy(xpath = "//span[@class='zw-cmn-loadMore']")
	private WebElement viewMore;

	@FindAll(@FindBy(xpath = "//div[@class='p-15 pt-10 mke-ryt rel']/a"))
	private List<WebElement> bikeNames;

	@FindAll(@FindBy(xpath = "//div[@class='p-15 pt-10 mke-ryt rel']/div[1]"))
	private List<WebElement> bikePrice;

	@FindAll(@FindBy(xpath = "//div[@class='p-15 pt-10 mke-ryt rel']/div[2]"))
	private List<WebElement> bikeLaunchDate;

	@FindAll(@FindBy(xpath = "//div[@class='p-15 pt-10 mke-ryt rel']/parent::div"))
	private List<WebElement> bikeDiv;

	private List<String[]> bikeDetails = new ArrayList<>();
	private String[] bike;

	public void checkViewMore() {
		try {
			jse.executeScript("arguments[0].scrollIntoView();", viewMore);
			jse.executeScript("arguments[0].click();", viewMore);
		} catch (Exception e) {
			System.out.println("View More not available!");
		}
	}

	WebElement currentBike;

	public List<String[]> getDetails() {
		checkViewMore();

		sleep(5000);

		int size = bikeNames.size();

		double priceDouble = 0.0;

		for (int i = 0; i < size; i++) {
			bike = new String[3];
			currentBike = bikeDiv.get(i);

			jse.executeScript("arguments[0].scrollIntoView();", currentBike);

			String priceString = bikePrice.get(i).getText();
			String[] words = priceString.split(" ");

			if (priceString.contains("Lakh"))
				priceDouble = Double.parseDouble(words[1]);
			else {
				String notLakh = words[1].replaceAll(",", "");

				priceDouble = Double.parseDouble(notLakh);
				priceDouble /= 100000;
			}

			if (priceDouble <= 4.0) {
				bike[0] = bikeNames.get(i).getText();
				bike[1] = bikePrice.get(i).getText();
				bike[2] = bikeLaunchDate.get(i).getText();
				bikeDetails.add(bike);
				Screenshots.captureScreen(currentBike, bike[0].trim(), "Bikes");
				sleep(1000);
			}

		}

		return bikeDetails;

	}

}
