package PageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class UpcomingBikes extends BasePage {

	public UpcomingBikes(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(xpath = "//a[normalize-space()='New Bikes']")
	WebElement newBikes;

	@FindBy(xpath = "//span[text()='Upcoming Bikes']")
	WebElement upcomingBikes;

	@FindBy(xpath = "//li[text()='Upcoming']")
	WebElement upcoming;

	public void upcomingBike() {
		upcomingBikeHover();
//		upcomingBikeClick();
	}

	public void upcomingBikeHover() {
		actions = new Actions(driver);
		actions.moveToElement(newBikes).perform();
//		newBikes.click();

		sleep(2000);

		upcomingBikes.click();
	}

	public void upcomingBikeClick() {
		newBikes.click();

		sleep(3000);

		upcoming.click();
	}

	@FindBy(id = "makeId")
	WebElement manufacturers;
	Select manufacturerSelect;

	public void selectManufacturers(String manufacturer) {
		manufacturerSelect = new Select(manufacturers);
		manufacturerSelect.selectByVisibleText(manufacturer);
	}

	@FindBy(xpath = "//span[@class='zw-cmn-loadMore']")
	WebElement viewMore;

	@FindAll(@FindBy(xpath = "//div[@class='p-15 pt-10 mke-ryt rel']/a"))
	List<WebElement> bikeNames;

	@FindAll(@FindBy(xpath = "//div[@class='p-15 pt-10 mke-ryt rel']/div[1]"))
	List<WebElement> bikePrice;

	@FindAll(@FindBy(xpath = "//div[@class='p-15 pt-10 mke-ryt rel']/div[2]"))
	List<WebElement> bikeLaunchDate;

	List<String[]> bikeDetails = new ArrayList<>();
	String[] bike;

	public List<String[]> getDetails() {
//		viewMore.click();
		jse.executeScript("arguments[0].scrollIntoView();", viewMore);
		jse.executeScript("arguments[0].click();", viewMore);

		sleep(5000);

		int size = bikeNames.size();

		double priceDouble = 0.0;

		for (int i = 0; i < size; i++) {
			bike = new String[3];
			String priceString = bikePrice.get(i).getText();
			String[] words = priceString.split(" ");

			if (priceString.contains("Lakh"))
				priceDouble = Double.parseDouble(words[1]);
			else {
//				System.out.println("NOT LAKH! " + words[1]);
				String notLakh = words[1].replaceAll(",", "");

				priceDouble = Double.parseDouble(notLakh);
				priceDouble /= 100000;
			}

			if (priceDouble <= 4.0) {
				System.out.println(priceString + " " + priceDouble);
				bike[0] = bikeNames.get(i).getText();
				bike[1] = bikePrice.get(i).getText();
				bike[2] = bikeLaunchDate.get(i).getText();
				bikeDetails.add(bike);
				System.out.println(bikeDetails.size());
			}

		}

		return bikeDetails;

	}

}
