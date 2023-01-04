package smokeTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import pages.ChangePassword;
import pages.CommomPage;
import pages.ContactUs;
import pages.Dashboard;
import pages.FAQ;
import pages.MyProfile;
import pages.RegisteredCourses;
import pages.SignIn;
import pages.SignIn_Prod;
import pages.SignUp;
import pages.Tutorial;
import utility.ExtentReport;
import utility.GenericUtility;
import utility.ReadPropFile;

/*
 * BaseClass is inherited by the TestNG class in src/test/java
 *
 */
public class BaseClass {
	private WebDriver driver;
	public ReadPropFile prop;
	public GenericUtility u;
	public ExtentReport rep= new ExtentReport();
	
	SignIn objSignIn;
	SignIn_Prod objSignIn_Prod;
	SignUp objSignUp;
	Dashboard objDashboard;
	Tutorial objTutorial;
	MyProfile objMyProfile;
	ChangePassword objChangePassword;
	ContactUs objContactUs; 
	RegisteredCourses objRegisteredCourses;
	CommomPage objCommomPage;
	FAQ objFAQ;
	
	String email;
	String password = "1234";
	
	@BeforeTest
	public void InitiateReport() {
//		rep = new ExtentReport();
		rep.initiateExtentReport();
	}
	@AfterTest
	public void terminateReport() {
		rep.terminateExtentReport();
	}
	
	@BeforeClass
	@Parameters("browser")
	public void beforeClass(String browser)  {
		rep.startTest(this.getClass().getName());
		try {
			u = new GenericUtility(browser,rep);
			driver=u.getDriver();
		} catch (Exception e) {System.out.println(e);}
		prop = new ReadPropFile();
		
		objSignIn=new SignIn(u);
		objSignIn_Prod=new SignIn_Prod(u);
		objSignUp = new SignUp(u);
		objDashboard=new Dashboard(u);
		objTutorial = new Tutorial(u);
		objMyProfile= new MyProfile(u);
		objRegisteredCourses = new RegisteredCourses(u);
		objCommomPage = new CommomPage(u);
		objChangePassword = new ChangePassword(u);
		objContactUs = new ContactUs(u);
		objFAQ = new FAQ(u);
	}

	@AfterClass
	public void closeDriver() {
		if (driver != null) {
			driver.quit();
		}
	}


}
