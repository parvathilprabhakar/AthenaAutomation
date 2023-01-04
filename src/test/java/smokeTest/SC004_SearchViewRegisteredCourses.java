package smokeTest;

import org.testng.annotations.Test;
import utility.ExcelUtility;

public class SC004_SearchViewRegisteredCourses extends BaseClass {
	ExcelUtility x = new ExcelUtility("SC004");

	@Test(enabled = true, priority = 0)
	public void validateRegisteredCourses_AddViewSearch() throws Exception {
		u.launchUrl(prop.getPropData().getProperty("URL"));
		email = x.readData("Email");
		password = x.readData("Password");
		objSignIn.loginToApplication(email,password);
		objDashboard.verifyIfDashboardDisplayed();
		objRegisteredCourses.searchCourseAndValidateResults(x.readData("Course name to be searched"));
		objRegisteredCourses.searchCourseAndValidateResults("QA");
		objRegisteredCourses.searchCourseAndValidateResults("basic");
		
		
		
		
		
		
		
		
		
	}
	
}
