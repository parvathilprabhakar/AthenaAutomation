package smokeTest;

import org.testng.annotations.Test;
import utility.ExcelUtility;

public class SC001_SignInSignUp extends BaseClass {
	ExcelUtility x = new ExcelUtility("Login");

	@Test(enabled = true)
	public void checkLogin() {
		u.launchUrl(prop.getPropData().getProperty("URL"));
		
	}

	
}
