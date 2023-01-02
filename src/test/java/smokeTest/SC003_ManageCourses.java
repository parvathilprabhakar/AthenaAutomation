package smokeTest;

import org.testng.annotations.Test;
import utility.ExcelUtility;

public class SC003_ManageCourses extends BaseClass {
	ExcelUtility x = new ExcelUtility("Login");

	@Test(enabled = true, priority = 0)
	public void validateSignUpMandatoryErrors() throws Exception {
		u.launchUrl(prop.getPropData().getProperty("URL"));
		objLogin.clickForgotPassword();
		objLogin.verifyForgotPasswordIsDisplayed();
		objLogin.closeForgotPasswordPopup();
		u.rep.logInReport("info", "Mandatory message validation completed!");
	}
	@Test(enabled = true, priority=1)
	public void validateSignUpTutorial() throws Exception {
		u.launchUrl(prop.getPropData().getProperty("URL"));
		objLogin.clickOnSignUp();
		objSignUp.clickShortCourses();
		password = x.readData("Password");
		email = objSignUp.signUpForNewUser(x.readData("FirstName"), x.readData("LastName"), x.readData("Country"), 
				x.readData("MobileNo"), x.readData("Email"), password);
		objTutorial.verifyIfTutorialPageIsDisplayed();
		objTutorial.clickOnSkipTutorial();
		objDashboard.verifyIfDashboardDisplayed();
		
	}

}
