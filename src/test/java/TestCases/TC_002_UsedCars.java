package TestCases;

import java.io.IOException;

import org.testng.annotations.Test;

import PageObjects.UsedCars;
import TestBase.BaseClass;

public class TC_002_UsedCars extends BaseClass {

	UsedCars usedCars;

	@Test
	public void test_UsedCars() throws IOException {
		usedCars = new UsedCars(driver);

		usedCars.findUsedCars(rb.getString("city"));

		String[] popularModels = usedCars.popularModelList();

		String fileName = fileName();
		String sheetName = "PopularModels";

		for (int i = 0; i < popularModels.length; i++) {
			excelUtilities.setCellData(fileName, sheetName, i, 0, popularModels[i]);
		}

		usedCars.navigateToHomePage();

	}
}
