package TestCases;

import java.io.IOException;

import org.testng.annotations.Test;

import PageObjects.LoginPage;
import TestBase.BaseClass;

public class TC_003_LoginSignup extends BaseClass {

	LoginPage loginPage;

	@Test
	public void test_GoogleLoginSignup() throws IOException {
		loginPage = new LoginPage(driver);

		loginPage.clickLoginSignup();
		loginPage.navigateToGoogleLogin();
		String errorMessage = loginPage.verifyNegativeLogin(rb.getString("email"));

		String fileName = fileName();
		String sheetName = "ErrorMessage";
		excelUtilities.setCellData(fileName, sheetName, 0, 0, errorMessage);
	}
}
