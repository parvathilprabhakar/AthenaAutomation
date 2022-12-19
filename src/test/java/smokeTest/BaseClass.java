package smokeTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import pages.Dashboard;
import pages.Login;
import pages.SignUp;
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
	Login objLogin;
	SignUp objSignUp;
	Dashboard objDashboard;
	String email;
	
	@BeforeClass
	@Parameters("browser")
	public void beforeClass(String browser)  {
		try {
			u = new GenericUtility(browser,this.getClass().getName());
			driver=u.getDriver();
		} catch (Exception e) {}
		driver.manage().window().maximize();
		prop = new ReadPropFile();
		
		objLogin=new Login(u);
		objSignUp = new SignUp(u);
		objDashboard=new Dashboard(u);
		
		
	}

	@AfterClass
	public void closeDriver() {
		if (driver != null) {
			driver.quit();
		}
	}


}
