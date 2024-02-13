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
		boolean actual = usedCars.validateUsedCars();
		Assert.assertTrue(actual, "Used Cars Option not Available!");
	}

	@Test(groups = { "regression" }, dependsOnMethods = "test_ValidateUsedCars")
	public void test_HoverUsedCars() {
		usedCars.hoverUsedCars();
	}

	@Test(groups = { "regression" }, dependsOnMethods = "test_HoverUsedCars")
	public void test_SelectCarCity() {
		String city = rb.getString("city");
		boolean actual;
		if (usedCars.validateCity(city)) {
			usedCars.clickCity();
			actual = true;
		} else {
			actual = usedCars.searchCity(city);
		}
		Assert.assertTrue(actual, "Invalid City!");

	}

	@Test(groups = { "regression" }, dependsOnMethods = "test_SelectCarCity")
	public void test_GetPopularModels() throws IOException {

		String[] popularModels = usedCars.popularModelList();

		String sheetName = "PopularModels";

		for (int i = 0; i < popularModels.length; i++) {
			excelUtilities.setCellData(fileName, sheetName, i, 0, popularModels[i]);
		}

	}

//	@Test(dependsOnMethods = "test_GetPopularModels")
	public void test_NavigateToHomePage() {
		usedCars.navigateToHomePage();
	}
}
