package smokeTest;

import org.testng.annotations.Test;
import utility.ExcelUtility;

public class SC001_SignInSignUp extends BaseClass {
	ExcelUtility x = new ExcelUtility("Login");

	@Test(enabled = true)
	public void checkSignUp() throws Exception {
		u.launchUrl(prop.getPropData().getProperty("URL"));
		objLogin.clickOnSignUp();
		objSignUp.clickShortCourses();
		email = objSignUp.signUpForNewUser(x.readData("FirstName"), x.readData("LastName"), x.readData("Country"), 
				x.readData("MobileNo"), x.readData("Email"), x.readData("Password"));
		objDashboard.verifyIfDashboardDisplayed();
	}

	
}
