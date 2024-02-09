package TestCases;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import PageObjects.UpcomingBikes;
import TestBase.BaseClass;

public class TC_001_UpcomingBikes extends BaseClass {

	UpcomingBikes homePage;

	@Test
	public void test_homePageURL() {
		homePage = new UpcomingBikes(driver);
		boolean actual = homePage.validateHomePageURL();
		Assert.assertTrue(actual, "Unable to load BaseURL!");
	}

	@Test(dependsOnMethods = "test_homePageURL")
	public void test_NewBikes() {

		boolean actual = homePage.validateNewBikes();
		Assert.assertTrue(actual, "New Bikes is unavailable!");
		homePage.newBikeHover();
		captureScreen("NewBikeHover");
	}

	@Test(dependsOnMethods = "test_NewBikes")
	public void test_UpcomingBikes() {
		boolean actual = false;
		if (homePage.validateUpcomingBikes()) {
			actual = homePage.clickUpcomingBikes();
		} else {
			actual = homePage.clickUpcoming();
		}
		Assert.assertTrue(actual, "Unable to find Upcoming Bikes Option!");

	}

	@Test(dependsOnMethods = "test_UpcomingBikes")
	public void test_Manufacturers() {
		boolean actual;
		String brand = rb.getString("manufacturer");
		if (homePage.brandElement(brand)) {
			actual = homePage.validateBrandURL();
			Assert.assertTrue(actual, "Brand URL not working!");
			homePage.selectBrand();
			captureScreen("Manufacturer");
		} else {
			actual = homePage.selectManufacturers(brand);
			captureScreen("Manufacturer");
			Assert.assertTrue(actual, "Invalid Manufacturer!");
		}
	}

	@Test(dependsOnMethods = "test_Manufacturers")
	public void test_BikeDetails() throws IOException {
		homePage.checkViewMore();

		List<String[]> bikeDetails = homePage.getDetails();

		String sheetName = "UpcomingBikes";

		excelUtilities.deleteExcel(fileName);

		int i = 0, j = 0;
		for (String[] bike : bikeDetails) {
			j = 0;
			for (String data : bike) {
				excelUtilities.setCellData(fileName, sheetName, i, j++, data);
			}
			i++;
		}
	}

}
