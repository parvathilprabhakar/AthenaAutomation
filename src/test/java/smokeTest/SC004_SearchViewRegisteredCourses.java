package smokeTest;

import org.testng.annotations.Test;

import utility.DBConnector;
import utility.ExcelUtility;

public class SC004_SearchViewRegisteredCourses extends BaseClass {
	ExcelUtility x = new ExcelUtility("SC004");

	//@Test(enabled = true, priority = 0)
	public void validateRegisteredCourses_AddViewSearch() throws Exception {
		u.launchUrl(prop.getPropData().getProperty("URL"));
		email = x.readData("Email");
		password = x.readData("Password");
		objSignIn.loginToApplication(email,password);
		u.objUserDetailsRepo.setAthenaID(x.readData("AthenaID"));
		objDashboard.verifyIfDashboardDisplayed();
		objRegisteredCourses.searchCourseAndValidateResults(x.readData("Course name to be searched"));
		objRegisteredCourses.searchCourseAndValidateResults("QA");
		objRegisteredCourses.searchCourseAndValidateResults("basic");
		objRegisteredCourses.exitSearch();
		objRegisteredCourses.verifyCourseCountListed(); //ToDo: Update code to check with DB once query is provided
		objRegisteredCourses.validateLastAccessedCourseIsUpdated();
		objRegisteredCourses.validateCourseNamesInCards();
		objRegisteredCourses.validateLessonsLeftValue();//ToDo: Update code to check with DB once query is provided
		objRegisteredCourses.validateIfSharingOptionIsEnabled();//ToDo: Update code to check with DB once query is provided
		
		
	}
	@Test(enabled = true, priority = 0)
	public void temp() throws Exception {
		DBConnector db = new DBConnector();
		db.Select_IsClaimed_FromCourseEnrolment_UID_CID("221175512", "165");
	}
	}

