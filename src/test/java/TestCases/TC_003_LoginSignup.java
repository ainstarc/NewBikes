package TestCases;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import PageObjects.LoginPage;
import TestBase.BaseClass;

public class TC_003_LoginSignup extends BaseClass {

	LoginPage loginPage;

	@Test
	public void test_LoginSignup() {
		loginPage = new LoginPage(driver);

		boolean actual = loginPage.validateLoginSignup();
		Assert.assertTrue(actual, "Login-SignUp Option not Available!");

		loginPage.clickLoginSignup();
	}

	@Test(dependsOnMethods = "test_LoginSignup")
	public void test_GoogleButton() {
		boolean actual = loginPage.validateGoogleButton();
		Assert.assertTrue(actual, "Google Button not Available!");

		loginPage.clickGoogleButton();
	}

	@Test(dependsOnMethods = "test_GoogleButton")
	public void test_VerifyNavigation() {

		boolean navigate = loginPage.navigateToGoogleLogin();
		Assert.assertTrue(navigate, "Unable to Navigate to Google Login Page!");
	}

	@Test(dependsOnMethods = "test_VerifyNavigation")
	public void test_VerifyLogin() throws IOException {
		String errorMessage = loginPage.verifyNegativeLogin(rb.getString("email"));
		String sheetName = "ErrorMessage";
		excelUtilities.setCellData(fileName, sheetName, 0, 0, errorMessage);
		String[] expectedErrorMessage = { "Couldn’t find your Google Account", "Couldn’t sign you in" };

		for (String error : expectedErrorMessage) {
			if (error.equalsIgnoreCase(errorMessage)) {
				System.out.println("ErrorMessage: " + errorMessage);
				return;
			}
		}
		Assert.fail("Wrong Error Message or Error Message not Found!");
	}
}
