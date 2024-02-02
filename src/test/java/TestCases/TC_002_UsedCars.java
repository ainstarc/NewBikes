package TestCases;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import PageObjects.UsedCars;
import TestBase.BaseClass;

public class TC_002_UsedCars extends BaseClass {

	UsedCars usedCars;

	@Test(priority = 1)
	public void test_UsedCars() {
		usedCars = new UsedCars(driver);

		usedCars.scrollToTop();
		boolean actual = usedCars.validateUsedCars();

		Assert.assertEquals(actual, expected);

		usedCars.hoverUsedCars();

	}

	@Test(priority = 2, dependsOnMethods = "test_UsedCars")
	public void test_CarCity() {
		boolean actual = usedCars.validateCity(rb.getString("city"));

		Assert.assertEquals(actual, expected);

		usedCars.clickCity();
	}

	@Test(priority = 3, dependsOnMethods = "test_CarCity")
	public void test_PopularModels() throws IOException {

		String[] popularModels = usedCars.popularModelList();

		String sheetName = "PopularModels";

		for (int i = 0; i < popularModels.length; i++) {
			excelUtilities.setCellData(fileName, sheetName, i, 0, popularModels[i]);
		}

	}

	@Test(priority = 4)
	public void test_NavigateToHomePage() {
		usedCars.navigateToHomePage();
	}
}
