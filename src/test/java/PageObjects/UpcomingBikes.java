package PageObjects;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import Utilities.Screenshots;

public class UpcomingBikes extends BasePage {

	public UpcomingBikes(WebDriver driver) {
		super(driver);
	}

	private String folderTimeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
	private String folderName = "UpcomingBikes" + folderTimeStamp;

	@FindBy(xpath = "//a[normalize-space()='New Bikes']")
	private WebElement newBikes;

	@FindBy(xpath = "//span[text()='Upcoming Bikes']")
	private WebElement upcomingBikes;

	public boolean validateHomePageURL() {
		String url = driver.getCurrentUrl();
		boolean homeURL = URLConnectionCheck(url);
		return homeURL;
	}

	public boolean validateNewBikes() {
		return checkVisible(newBikes);
	}

	public void newBikeHover() {
		borderElement(newBikes);
		actions.moveToElement(newBikes).perform();
		Screenshots.captureScreen(driver, "BikeHover", folderName);
	}

	public boolean validateUpcomingBikes() {
		return checkVisible(upcomingBikes);
	}

	@FindBy(xpath = "//li[text()='Upcoming']")
	private WebElement upcoming;

	@FindBy(xpath = "//a[text()='Upcoming Bikes']")
	private WebElement allUpcomingBikes;

	public boolean clickUpcomingBikes() {
		if (checkClickable(upcomingBikes)) {
			borderElement(upcomingBikes);
			Screenshots.captureScreen(driver, "UpcomingBikes", folderName);
			upcomingBikes.click();
			return true;
		}
		return false;
	}

	public boolean clickUpcoming() {
		borderElement(newBikes);
		newBikes.click();

		if (checkClickable(upcoming)) {
			borderElement(upcoming);
			upcoming.click();

			if (checkClickable(allUpcomingBikes)) {
				borderElement(allUpcomingBikes);
				jse.executeScript("arguments[0].click();", allUpcomingBikes);
				return true;
			}
		}
		return false;
	}

	@FindBy(id = "makeId")
	private WebElement manufacturers;
	private Select manufacturerSelect;

//	@FindBy(xpath = "//a[text()='Honda']")
	private WebElement brandElement;

	public boolean validateManufacturers() {
		return checkVisible(manufacturers);
	}

	public boolean brandElement(String brand) {
		try {
			String brandXpath = "//a[text()='" + brand + "']";
			brandElement = driver.findElement(By.xpath(brandXpath));
			jse.executeScript("arguments[0].scrollIntoView();", brandElement);
			actions.sendKeys(Keys.ARROW_UP).perform();
			borderElement(brandElement);
			Screenshots.captureScreen(driver, "SelectBrand", folderName);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean validateBrandURL() {
		String brandURL = brandElement.getAttribute("href");
		return URLConnectionCheck(brandURL);
	}

	public void selectBrand() {
//		brandElement.click();
		sleep(3000);
		jse.executeScript("arguments[0].click();", brandElement);
	}

	public boolean selectManufacturers(String brand) {
		jse.executeScript("arguments[0].scrollIntoView();", manufacturers);
		borderElement(manufacturers);
		Screenshots.captureScreen(driver, "SelectManufacturer", folderName);
		manufacturerSelect = new Select(manufacturers);
		try {
			manufacturerSelect.selectByVisibleText(brand);
			Screenshots.captureScreen(driver, brand, folderName);
			return true;
		} catch (Exception e) {
			System.out.println("Invalid Manufacturer!");
			return false;
		}
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

	private List<String[]> bikeDetails = new ArrayList<String[]>();
	private String[] bike;

	public void checkViewMore() {
		try {
			borderElement(viewMore);
//			jse.executeScript("arguments[0].scrollIntoView();", viewMore);
			jse.executeScript("window.scrollBy(0,1300);");
			Screenshots.captureScreen(driver, "ViewMore", folderName);

			sleep(2000);

			jse.executeScript("arguments[0].click();", viewMore);
		} catch (Exception e) {
			System.out.println("View More not available!");
		}
	}

	WebElement currentBike;

	public boolean[] checkBikeNames(String brand) {
		sleep(5000);
		int size = bikeNames.size();
		brand = brand.toLowerCase();
		boolean[] validBike = new boolean[size];
		int i = 0;
		for (WebElement bike : bikeNames) {
			String name = bike.getText();
			name = name.toLowerCase();
			if (name.contains(brand)) {
				validBike[i++] = true;
			} else {
				validBike[i++] = false;
			}
		}
		return validBike;

	}

	public List<String[]> getDetails() {

		int size = bikeNames.size();

		double priceDouble = 0.0;

		for (int i = 0; i < size; i++) {
			bike = new String[3];
			currentBike = bikeDiv.get(i);
//			borderElement(currentBike);
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
				borderElement(currentBike);
				bike[0] = bikeNames.get(i).getText();
				bike[1] = bikePrice.get(i).getText();
				bike[2] = bikeLaunchDate.get(i).getText();
				bikeDetails.add(bike);

				printBike(bike);
				Screenshots.captureScreen(currentBike, bike[0].trim(), folderName + "//Bikes");
				sleep(3000);
			}

		}

		return bikeDetails;

	}

	void printBike(String[] bike) {
		System.out.println("Bike Name: " + bike[0]);
		System.out.println("Bike Price: " + bike[1]);
		System.out.println("Bike " + bike[2]);
		System.out.println("-----------------------------");
	}

}
