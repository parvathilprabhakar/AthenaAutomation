package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import utility.GenericUtility;

public class CourseDetails {
	GenericUtility u;

	public CourseDetails(GenericUtility u) {
		this.u = u;
	}

	By byCourseName = By.xpath("//div[@class= 'page-content']//div[@ng-reflect-router-link='/student-dashboard']/following-sibling::span");
	By byTab_WhyThisCourse = By.xpath("//h1[text()='Why this Course']");
	By byGoToDashboardBtn = By.xpath("//span[normalize-space(text())='Go To Dashboard']");

	public void verifyMyProfilePageIsDisplayed() {
		u.waitToVisible(byTab_WhyThisCourse);
		try{if (u.isDisplayed(byTab_WhyThisCourse))
			u.rep.logInReport("Pass", "Course details page is displayed successfully");
		else
			u.rep.logInReport("Fail", "Failed to display Course details page");
		}catch(Exception e) {
			u.rep.logInReport("Fail", "Failed to display Course details page");
		}
	}
	public String getCourseName() {
		return u.getText(byCourseName).trim();
	}
	public void navigateBackToDashboard() {
		u.click(byGoToDashboardBtn);
		u.waitForLoading();
	}
}
