package TestCases;

import java.io.IOException;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import PageObjects.UpcomingBikes;
import TestBase.BaseClass;

public class TC_001_UpcomingBikes extends BaseClass {

	UpcomingBikes homePage;

	@BeforeMethod(groups = { "smoke", "sanity", "regression" })
	public void driverInit() {
		homePage = new UpcomingBikes(driver);
	}

	@Test(priority = 1, groups = { "smoke", "sanity" })
	public void test_ValidateHomePageURL() {
		boolean actual = homePage.validateHomePageURL();
		Assert.assertTrue(actual, "Unable to load BaseURL!");
	}

	@Test(priority = 2, groups = { "sanity", "regression" })
	public void test_ValidateNewBikes() {
		boolean actual = homePage.validateNewBikes();
		Assert.assertTrue(actual, "New Bikes is unavailable!");
	}

	@Test(groups = { "regression" }, dependsOnMethods = "test_ValidateNewBikes")
	public void test_HoverNewBikes() {
		homePage.newBikeHover();
		captureScreen("NewBikeHover");
	}

	@Test(groups = { "regression" }, dependsOnMethods = "test_HoverNewBikes")
	public void test_ClickUpcomingBikes() {
		boolean actual = false;
		if (homePage.validateUpcomingBikes()) {
			actual = homePage.clickUpcomingBikes();
		} else {
			actual = homePage.clickUpcoming();
		}
		Assert.assertTrue(actual, "Unable to find Upcoming Bikes Option!");

	}

	@Test(groups = { "regression" }, dependsOnMethods = "test_ClickUpcomingBikes")
	public void test_SelectManufacturers() {
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

	@Test(groups = { "regression" }, dependsOnMethods = "test_SelectManufacturers")
	public void test_ValidateBikeNames() {

		homePage.checkViewMore();

		String brand = rb.getString("manufacturer");
		boolean[] actual = homePage.checkBikeNames(brand);
		SoftAssert sa = new SoftAssert();
		for (boolean res : actual) {
			sa.assertTrue(res);
		}
		sa.assertAll("Different Brand Bikes found!");
	}

	@Test(groups = { "regression" }, dependsOnMethods = "test_ValidateBikeNames")
	public void test_GetBikeDetails() throws IOException {

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
