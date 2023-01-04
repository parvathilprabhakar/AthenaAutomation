package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import utility.GenericUtility;

public class RegisteredCourses {
	GenericUtility u;

	public RegisteredCourses(GenericUtility u) {
		this.u = u;
	}

	By bySearchInput = By.xpath("//input[@placeholder='Search your Registered Course']");
	By bySearchIcon = By.xpath(
			"//input[@placeholder='Search your Registered Course']/following-sibling::*//span[contains(@class,'find')]");
	By byTotalTiles = By.xpath("//div[@class='carousel-cell ng-star-inserted']");
	By byTotalTileHeaders = By.xpath("//div[@class='carousel-cell ng-star-inserted']//h3");
	
	By byLoading_RegisteredCouses = By.xpath("//img[@alt='Registered Courses']");

	public void searchCourseAndValidateResults(String courseName) {
		u.aClick(bySearchInput);
		u.enterTextbox(bySearchInput, courseName);
		u.aClick(bySearchIcon);
		u.waitUntilInvisible(byLoading_RegisteredCouses);
		

		Boolean bSearchWorksFine = true;
		String actualHeader="";
		List<WebElement> eTotalTileHeaders = u.elements(byTotalTileHeaders);
		for (WebElement eHeader : eTotalTileHeaders) {
			actualHeader = eHeader.getText();
			if (!actualHeader.toLowerCase().contains(courseName.toLowerCase())) {
				bSearchWorksFine = false;
			}
		}
		if (bSearchWorksFine)
			u.rep.logInReport("Pass", "Search works as expected for keyword: "+courseName);
		else
			u.rep.logInReport("Fail", "Failed to display search result based on the keyword '"+courseName+"'"
					+"<br>Course listed incorrectly: "+actualHeader);

	}

}
