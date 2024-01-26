package TestCases;

import org.testng.annotations.Test;

import PageObjects.UsedCars;
import TestBase.BaseClass;

public class TC_002_UsedCars extends BaseClass {

	UsedCars usedCars;

	@Test
	public void test_UsedCars() {
		usedCars = new UsedCars(driver);

		usedCars.findUsedCars(rb.getString("city"));

		String[] popularModels = usedCars.popularModelList();

		usedCars.navigateToHomePage();

	}
}
