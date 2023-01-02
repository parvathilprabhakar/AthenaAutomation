package smokeTest;

import org.testng.annotations.Test;
import utility.ExcelUtility;

public class SC003_ValidateDashboardAndDropdowns extends BaseClass {
	ExcelUtility x = new ExcelUtility("SC003");

	@Test(enabled = true, priority = 0)
	public void validateRegisteredCourses_AddViewSearch() throws Exception {
		u.launchUrl(prop.getPropData().getProperty("URL"));
		email = x.readData("Email");
		password = x.readData("Password");
		objSignIn.loginToApplication(email,password);
		objDashboard.verifyIfDashboardDisplayed();
		objDashboard.verifyIfProfilePicIsDisplayed();
		objDashboard.verifyIfIdIsDisplayed();
		objDashboard.verifyIfFirstLastNameIsDisplayed(x.readData("FirstName"),x.readData("LastName"));
		objCommomPage.validateProfileIconDropdowns();
		objCommomPage.clickOptionFromProfileDropdown("My Profile");
		objMyProfile.verifyMyProfilePageIsDisplayed();
		objCommomPage.clickOptionFromProfileDropdown("Change Password");
		objChangePassword.verifyChangePasswordPopupIsDisplayed();
		objChangePassword.closePopup();
		objCommomPage.clickOptionFromProfileDropdown("Contact Us");
		objContactUs.verifyContactUsPageIsDisplayed();
		objCommomPage.clickOptionFromProfileDropdown("FAQ");
		objFAQ.verifyFAQPageIsDisplayed();
		objCommomPage.clickOptionFromProfileDropdown("Logout");
		objSignIn_Prod.verifyProdSigninPageIsDisplayed();
		
		
		
		
		
		
		
		
	}
	
}
