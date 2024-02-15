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
		logger.info("Validating HomePageURL...");
		Assert.assertTrue(actual, "Unable to load BaseURL!");
	}

	@Test(priority = 2, groups = { "sanity", "regression" })
	public void test_ValidateNewBikes() {
		logger.info("*****Starting TEST-SCENARIO-001*****");
		boolean actual = homePage.validateNewBikes();
		logger.info("Validating presence of NewBikes Dropdown.");
		Assert.assertTrue(actual, "New Bikes is unavailable!");
	}

	@Test(groups = { "regression" }, dependsOnMethods = "test_ValidateNewBikes")
	public void test_HoverNewBikes() {
		homePage.newBikeHover();
		logger.info("Hovered on NewBikes.");
		captureScreen("NewBikeHover");
	}

	@Test(groups = { "regression" }, dependsOnMethods = "test_HoverNewBikes")
	public void test_ClickUpcomingBikes() {
		boolean actual = false;
		if (homePage.validateUpcomingBikes()) {
			actual = homePage.clickUpcomingBikes();
			logger.info("Clicked on UpcomingBikes.");
		} else {
			actual = homePage.clickUpcoming();
			logger.info("Clicked on NewBikes.");
			logger.info("Clicked on Upcoming.");
			logger.info("Clicked on All Upcoming Bikes.");
		}
		Assert.assertTrue(actual, "Unable to find Upcoming Bikes Option!");

	}

	@Test(groups = { "regression" }, dependsOnMethods = "test_ClickUpcomingBikes")
	public void test_SelectManufacturers() {
		boolean actual;
		String brand = rb.getString("manufacturer");
		if (homePage.brandElement(brand)) {
			actual = homePage.validateBrandURL();
			logger.info("Validating presence of {} option in Brand-Suggestions.", brand);
			captureScreen("Manufacturer");
			homePage.selectBrand();
			Assert.assertTrue(actual, "Brand URL not working!");
		} else {
			actual = homePage.selectManufacturers(brand);
			logger.info("Selected {String} manufacturer from Manufacturer dropdown.", brand);
			captureScreen("Manufacturer");
			Assert.assertTrue(actual, "Invalid Manufacturer!");
		}
	}

	@Test(groups = { "regression" }, dependsOnMethods = "test_SelectManufacturers")
	public void test_ValidateBikeNames() {

		homePage.checkViewMore();
		logger.info("Checking presence of View More.");

		String brand = rb.getString("manufacturer");
		boolean[] actual = homePage.checkBikeNames(brand);
		SoftAssert sa = new SoftAssert();
		for (boolean res : actual) {
			sa.assertTrue(res);
		}
		logger.info("Matching all Bike Names with selected Manufacturer Brand.");
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
				logger.info("Setting Cell Data of {}", data);
			}
			i++;
		}
		captureScreen("ExcelDataLoad");
		logger.info("*****Ended TEST-SCENARIO-001*****");
	}

}
