package TestCases;

import org.testng.annotations.Test;

import PageObjects.UpcomingBikes;
import TestBase.BaseClass;

public class TC_001_UpcomingBikes extends BaseClass {

	UpcomingBikes homePage;

	@Test
	public void test_UpcomingBikes() {
		homePage = new UpcomingBikes(driver);

		homePage.upcomingBike();
		homePage.selectManufacturers(rb.getString("manufacturer"));
		String[][] bikeDetails = homePage.getDetails();

	}

}
