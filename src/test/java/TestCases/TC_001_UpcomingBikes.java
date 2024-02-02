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
	public void test_NewBikes() {
		homePage = new UpcomingBikes(driver);
		boolean actual = homePage.validateNewBikes();
		Assert.assertEquals(actual, expected);

		homePage.newBikeHover();
		captureScreen("NewBikeHover");
	}

	@Test(dependsOnMethods = "test_NewBikes")
	public void test_UpcomingBikes() {
		boolean actual = homePage.validateUpcomingBikes();
		Assert.assertEquals(actual, expected);

		homePage.clickUpcomingBikes();
	}

	@Test(dependsOnMethods = "test_UpcomingBikes")
	public void test_Manufacturers() {
		boolean actual = homePage.validateManufacturers();
		captureScreen("UpcomingBikes");
		Assert.assertEquals(actual, expected);

		homePage.selectManufacturers(rb.getString("manufacturer"));
		captureScreen("Manufacturer");
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
