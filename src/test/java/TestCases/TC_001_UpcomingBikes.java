package TestCases;

import java.io.IOException;
import java.util.List;

import org.testng.annotations.Test;

import PageObjects.UpcomingBikes;
import TestBase.BaseClass;

public class TC_001_UpcomingBikes extends BaseClass {

	UpcomingBikes homePage;

	@Test
	public void test_UpcomingBikes() throws IOException {
		homePage = new UpcomingBikes(driver);

		homePage.upcomingBike();
		homePage.selectManufacturers(rb.getString("manufacturer"));
		List<String[]> bikeDetails = homePage.getDetails();

		String sheetName = "UpcomingBikes";
		String fileName = fileName();

		excelUtilities.deleteExcel(fileName());

		int i = 0, j = 0;
		for (String[] bike : bikeDetails) {
			j = 0;
			for (String data : bike) {
//				System.out.println(data);
				excelUtilities.setCellData(fileName, sheetName, i, j++, data);
			}
			i++;
		}
	}

}
