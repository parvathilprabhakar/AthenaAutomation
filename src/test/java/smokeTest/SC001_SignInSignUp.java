package smokeTest;

import org.testng.annotations.Test;
import utility.ExcelUtility;

public class SC001_SignInSignUp extends BaseClass {
	ExcelUtility x = new ExcelUtility("Login");

	@Test(enabled = true, priority=0)
	public void validateSignUpMandatoryErrors() throws Exception {
		u.launchUrl(prop.getPropData().getProperty("URL"));
		objLogin.clickOnSignUp();
		objSignUp.clickShortCourses();
		objSignUp.clickOnSignUpButton();
		objSignUp.validateMandatoryErrors();
	}
	
	@Test(enabled = true)
	public void validateSignUp() throws Exception {
		u.launchUrl(prop.getPropData().getProperty("URL"));
		objLogin.clickOnSignUp();
		objSignUp.clickShortCourses();
		email = objSignUp.signUpForNewUser(x.readData("FirstName"), x.readData("LastName"), x.readData("Country"), 
				x.readData("MobileNo"), x.readData("Email"), x.readData("Password"));
		objDashboard.verifyIfDashboardDisplayed();
	}

	
}
