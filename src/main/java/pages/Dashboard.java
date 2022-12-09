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

	public void verifyIfProfilePicIsDisplayed(String usrName, String pWord) {
		if (u.isDisplayed(byDashboardProfilePic))
			u.rep.logInReport("Pass", "Profile pic displayed in Dashboard");
		else
			u.rep.logInReport("Fail", "Failed to display profile pic in Dashboard");
	}

	public String verifyIfIdIsDisplayed() {
		String athenaID = "";
		if (u.isDisplayed(byAthenaId)) {
			athenaID = u.getText(byAthenaId);
			u.rep.logInReport("Pass", "Athena Id is displayed.<br>" + athenaID);
		}else
			u.rep.logInReport(athenaID, athenaID);
return athenaID;
	}
}