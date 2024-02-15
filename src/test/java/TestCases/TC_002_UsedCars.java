package TestCases;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import PageObjects.UpcomingBikes;
import PageObjects.UsedCars;
import TestBase.BaseClass;

public class TC_002_UsedCars extends BaseClass {

	UsedCars usedCars;

	@BeforeMethod(groups = { "smoke", "sanity", "regression" })
	public void driverInit() {
		usedCars = new UsedCars(driver);
	}

	@Test(groups = { "sanity", "regression" })
	public void test_ValidateUsedCars() {
		logger.info("*****Starting TEST-SCENARIO-002*****");
		boolean actual = usedCars.validateUsedCars();
		logger.info("Validating presence of UsedCars Dropdown.");
		Assert.assertTrue(actual, "Used Cars Option not Available!");
	}

	@Test(groups = { "regression" }, dependsOnMethods = "test_ValidateUsedCars")
	public void test_HoverUsedCars() {
		usedCars.hoverUsedCars();
		logger.info("Hovered on UsedCars Dropdown.");
		captureScreen("HoverUsedCars");
	}

	@Test(groups = { "regression" }, dependsOnMethods = "test_HoverUsedCars")
	public void test_SelectCarCity() {
		String city = rb.getString("city");
		boolean actual;
		if (usedCars.validateCity(city)) {
			captureScreen("CityFound");
			usedCars.clickCity();
			logger.info("Clicked on {} city.",city);
			actual = true;
		} else {
			actual = usedCars.searchCity(city);
			logger.info("Clicked on Other City.");
			logger.info("Searching {} city.",city);
		}
		Assert.assertTrue(actual, "Invalid City!");

	}

	@Test(groups = { "regression" }, dependsOnMethods = "test_SelectCarCity")
	public void test_GetPopularModels() throws IOException {

		String[] popularModels = usedCars.popularModelList();

		String sheetName = "PopularModels";
		captureScreen("PopularModels");

		for (int i = 0; i < popularModels.length; i++) {
			excelUtilities.setCellData(fileName, sheetName, i, 0, popularModels[i]);
			logger.info("Setting cell data as {}",popularModels[i]);
		}
		logger.info("*****Ended TEST-SCENARIO-002*****");

	}

//	@Test(dependsOnMethods = "test_GetPopularModels")
	public void test_NavigateToHomePage() {
		usedCars.navigateToHomePage();
	}
}
