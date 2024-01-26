package TestCases;

import org.testng.annotations.Test;

import PageObjects.LoginPage;
import TestBase.BaseClass;

public class TC_003_LoginSignup extends BaseClass {

	LoginPage loginPage;

	@Test
	public void test_GoogleLoginSignup() {
		loginPage = new LoginPage(driver);

		loginPage.clickLoginSignup();
		loginPage.navigateToGoogleLogin();
		String errorMessage = loginPage.verifyNegativeLogin(rb.getString("email"));
	}
}
