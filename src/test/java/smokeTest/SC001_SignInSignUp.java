package smokeTest;

import org.testng.annotations.Test;
import utility.ExcelUtility;

public class SC001_SignInSignUp extends BaseClass {
	ExcelUtility x = new ExcelUtility("Login");

	@Test(enabled = true, priority=0)
	public void validateSignUpMandatoryErrors() throws Exception {
		u.launchUrl(prop.getPropData().getProperty("URL"));
		objSignIn.clickOnSignUp();
		objSignUp.clickShortCourses();
		objSignUp.clickOnSignUpButton();
		objSignUp.validateMandatoryErrors();
		u.rep.logInReport("info", "Mandatory message validation completed!");
	}
	
	@Test(enabled = true, priority=1)
	public void validateSignUp() throws Exception {
		u.launchUrl(prop.getPropData().getProperty("URL"));
		objSignIn.clickOnSignUp();
		objSignUp.clickShortCourses();
		password = x.readData("Password");
		email = objSignUp.signUpForNewUser(x.readData("FirstName"), x.readData("LastName"), x.readData("Country"), 
				x.readData("MobileNo"), x.readData("Email"), password);
		objDashboard.verifyIfDashboardDisplayed();
		
	}
	@Test(enabled = true, priority=2)
	public void validateSignIn() throws Exception {
		u.launchUrl(prop.getPropData().getProperty("URL"));
		objSignIn.loginToApplication(email,password);
		objDashboard.verifyIfDashboardDisplayed();
	}

	
}
