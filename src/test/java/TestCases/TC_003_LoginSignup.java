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
		logger.info("*****Starting TEST-SCENARIO-003*****");
		boolean actual = loginPage.validateLoginSignup();
		logger.info("Validating presence of LoginSignUp Button.");
		Assert.assertTrue(actual, "Login-SignUp Option not Available!");
	}

	@Test(groups = { "sanity", "regression" }, dependsOnMethods = "test_ValidateLoginSignUp")
	public void test_ClickLoginSignUp() {
		loginPage.clickLoginSignup();
		logger.info("Clicked on LoginSignUp Button.");
	}

	@Test(groups = { "sanity", "regression" }, dependsOnMethods = "test_ClickLoginSignUp")
	public void test_ValidateGoogleButton() {
		boolean actual = loginPage.validateGoogleButton();
		logger.info("Validating presence of Google Button..");
		Assert.assertTrue(actual, "Google Button not Available!");
	}

	@Test(groups = { "sanity", "regression" }, dependsOnMethods = "test_ValidateGoogleButton")
	public void test_ClickGoogleButton() {
		captureScreen("GoogleButton");
		loginPage.clickGoogleButton();
		logger.info("Clicked on Google Button.");
	}

	@Test(groups = { "sanity", "regression" }, dependsOnMethods = "test_ClickGoogleButton")
	public void test_VerifyNavigation() {
		boolean navigate = loginPage.navigateToGoogleLogin();
		logger.info("Navigating to Google Login Page.");
		Assert.assertTrue(navigate, "Unable to Navigate to Google Login Page!");
	}

	@Test(groups = { "sanity", "regression" }, dependsOnMethods = "test_VerifyNavigation")
	public void test_VerifyLogin() throws IOException {
		captureScreen("AfterNavigation");
		String errorMessage = loginPage.verifyNegativeLogin(randomEmail(8));
		logger.info("Generating Random Email and Getting Error Message.");
		String sheetName = "ErrorMessage";
		captureScreen("ErrorMessage");
		excelUtilities.setCellData(fileName, sheetName, 0, 0, errorMessage);
		logger.info("Setting Error Message data into Excel.");
		String[] expectedErrorMessage = { "Couldn’t find your Google Account", "Couldn’t sign you in" };

		for (String error : expectedErrorMessage) {
			if (error.equalsIgnoreCase(errorMessage)) {
				System.out.println("ErrorMessage: " + errorMessage);
				logger.info("Validating Error Message.");
				return;
			}
		}
		logger.info("*****Ended TEST-SCENARIO-003*****");
		Assert.fail("Wrong Error Message or Error Message not Found!");
	}
}
