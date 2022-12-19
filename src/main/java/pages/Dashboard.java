package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import utility.GenericUtility;

public class Dashboard {
	GenericUtility u;

	public Dashboard(GenericUtility u) {
		this.u = u;
	}

	By byDashboardProfilePic = By.xpath("//img[@class='mb-2']");
	By byAthenaId = By.xpath("//p[contains(text(),'Athena ID:')]");

	public void verifyIfDashboardDisplayed() {
		try{if (u.isDisplayed(byDashboardProfilePic))
			u.rep.logInReport("Pass", "Dashboard is displayed");
		else
			u.rep.logInReport("Fail", "Failed to display Dashboard");
		}catch(Exception e) {
			u.rep.logInReport("Fail", "Failed to display Dashboard");//Need to handle with listeners
		}
	}
	
	public void verifyIfProfilePicIsDisplayed() {
		try{if (u.isDisplayed(byDashboardProfilePic))
			u.rep.logInReport("Pass", "Profile pic displayed in Dashboard");
		else
			u.rep.logInReport("Fail", "Failed to display profile pic in Dashboard");
	}catch(Exception e) {
		u.rep.logInReport("Fail", "Failed to display Dashboard");//Need to handle with listeners
	}
	}

	public String verifyIfIdIsDisplayed() {
		String athenaID = "";
		try{if (u.isDisplayed(byAthenaId)) {
			athenaID = u.getText(byAthenaId);
			u.rep.logInReport("Pass", "Athena Id is displayed.<br>" + athenaID);
		}else
			u.rep.logInReport(athenaID, athenaID);
	}catch(Exception e) {
		u.rep.logInReport("Fail", "Failed to display Dashboard");//Need to handle with listeners
	}
return athenaID;
	}
}