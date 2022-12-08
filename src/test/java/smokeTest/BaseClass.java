package smokeTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import pages.Login;
import utility.GenericUtility;
import utility.ReadPropFile;

/*
 * BaseClass is inherited by the TestNG class in src/test/java
 *
 */
public class BaseClass {
	private WebDriver driver;
	public ReadPropFile prop;
	GenericUtility u;
	Login objLogin;
	
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
		
	}

	@AfterClass
	public void closeDriver() {
		u.rep.terminateExtentReport();
		if (driver != null) {
			driver.quit();
		}
	}


}
