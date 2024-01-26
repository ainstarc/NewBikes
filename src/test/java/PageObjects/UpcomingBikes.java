package PageObjects;

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

	String[][] bikeDetails;

	public String[][] getDetails() {
//		viewMore.click();
		jse.executeScript("arguments[0].scrollIntoView();", viewMore);
		jse.executeScript("arguments[0].click();", viewMore);

		int size = bikeNames.size();
//		System.out.println(size);

		bikeDetails = new String[size][3];

		for (int i = 0; i < size; i++) {
//			System.out.println(bikeNames.get(i).getText());
//			System.out.println(bikePrice.get(i).getText());
//			System.out.println(bikeLaunchDate.get(i).getText());
			bikeDetails[i][0] = bikeNames.get(i).getText();
			bikeDetails[i][1] = bikePrice.get(i).getText();
			bikeDetails[i][2] = bikeLaunchDate.get(i).getText();
//			System.out.println("**********************************");
		}

		return bikeDetails;

	}

}
