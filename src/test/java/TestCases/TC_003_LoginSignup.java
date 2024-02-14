package TestCases;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import PageObjects.LoginPage;
import PageObjects.UsedCars;
import TestBase.BaseClass;

public class TC_003_LoginSignup extends BaseClass {

	LoginPage loginPage;

	@BeforeMethod(groups = { "smoke", "sanity", "regression" })
	public void driverInit() {
		loginPage = new LoginPage(driver);
	}

	@Test(groups = { "sanity", "regression" })
	public void test_ValidateLoginSignUp() {
		boolean actual = loginPage.validateLoginSignup();
		Assert.assertTrue(actual, "Login-SignUp Option not Available!");
	}

	@Test(groups = { "sanity", "regression" }, dependsOnMethods = "test_ValidateLoginSignUp")
	public void test_ClickLoginSignUp() {
		loginPage.clickLoginSignup();
	}

	@Test(groups = { "sanity", "regression" }, dependsOnMethods = "test_ClickLoginSignUp")
	public void test_ValidateGoogleButton() {
		boolean actual = loginPage.validateGoogleButton();
		Assert.assertTrue(actual, "Google Button not Available!");
	}

	@Test(groups = { "sanity", "regression" }, dependsOnMethods = "test_ValidateGoogleButton")
	public void test_ClickGoogleButton() {
		loginPage.clickGoogleButton();
	}

	@Test(groups = { "sanity", "regression" }, dependsOnMethods = "test_ClickGoogleButton")
	public void test_VerifyNavigation() {
		boolean navigate = loginPage.navigateToGoogleLogin();
		Assert.assertTrue(navigate, "Unable to Navigate to Google Login Page!");
	}

	@Test(groups = { "sanity", "regression" }, dependsOnMethods = "test_VerifyNavigation")
	public void test_VerifyLogin() throws IOException {
		String errorMessage = loginPage.verifyNegativeLogin(randomEmail(8));
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
