package pages;

import java.util.List;
import java.util.Random;

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

	By byRegisteredCourseTitle = By.xpath("//div[@class='row reg-courses']//h2");
	By byLastAccessedCourseLabel = By.xpath("//span[@class='last-accessed-course']/..");
	By byLastAccessedCourse = By.xpath("//span[@class='last-accessed-course']");

	By bySearchInput = By.xpath("//input[@placeholder='Search your Registered Course']");
	By bySearchIcon = By.xpath(
			"//input[@placeholder='Search your Registered Course']/following-sibling::*//span[contains(@class,'find')]");
	By byTotalTiles = By.xpath("//div[@class='carousel-cell ng-star-inserted']");
	By byTotalTileHeaders = By.xpath("//div[@class='carousel-cell ng-star-inserted']//h3");
	By byTotalTilePercentage = By.xpath("//div[@class='course-area text-center']");
	By byTotalTileCourseDetailsHoverButton = By.xpath(
			"//div[contains(@class,'course-item-hover')]//button[@class='goto-course' and normalize-space(text())='Go to Course Details']");

	By byLoading_RegisteredCouses = By.xpath("//img[@alt='Registered Courses']");
	By byPageCounts = By.xpath("//ul[@class='pagination']/li");

	public void searchCourseAndValidateResults(String courseName) {
		u.aClick(bySearchInput);
		u.enterTextbox(bySearchInput, courseName);
		u.aClick(bySearchIcon);
		u.waitUntilInvisible(byLoading_RegisteredCouses);

		Boolean bSearchWorksFine = true;
		String actualHeader = "";
		List<WebElement> eTotalTileHeaders = u.elements(byTotalTileHeaders);
		for (WebElement eHeader : eTotalTileHeaders) {
			actualHeader = eHeader.getText();
			if (!actualHeader.toLowerCase().contains(courseName.toLowerCase())) {
				bSearchWorksFine = false;
			}
		}
		if (bSearchWorksFine)
			u.rep.logInReport("Pass", "Search works as expected for keyword: " + courseName);
		else
			u.rep.logInReport("Fail", "Failed to display search result based on the keyword '" + courseName + "'"
					+ "<br>Course listed incorrectly: " + actualHeader);

	}

	public void validateLastAccessedCourseIsUpdated() {
		String expectedLabel = "Last accessed course: ";
		if (u.getText(byLastAccessedCourseLabel).equals(expectedLabel))
			u.rep.logInReport("Pass",
					"Last accessed label displayed correctly as " + u.getText(byLastAccessedCourseLabel));
		else
			u.rep.logInReport("Fail", "Failed to display Last accessed label correctly" + "<br>Expected: "
					+ expectedLabel + "<br>Actual: " + u.getText(byLastAccessedCourseLabel));

		String oldLastAccessedCourse = u.getText(byLastAccessedCourse);
		String expectedLastAccessedCourse = "";
//		Random rn = new Random();
//		int randomTileNumber = rn.nextInt(12 - 1 + 1) + 1;
		List<WebElement> eTileHeaders = u.elements(byTotalTileHeaders);
		for (int i = 0; i < eTileHeaders.size(); i++) {
			expectedLastAccessedCourse = eTileHeaders.get(i).getText();
			if (!expectedLastAccessedCourse.equals(oldLastAccessedCourse)) {
				try {
					u.elements(byTotalTilePercentage).get(i).click();
				} catch (Exception e) {
					String dynamicXpath_BtnGoToCourseDetails = "(//div[@class='carousel-cell ng-star-inserted'])[" + i
							+ "]//div[contains(@class,'course-item-hover')]//button[@class='goto-course' and normalize-space(text())='Go to Course Details']";
					u.aClick(By.xpath(dynamicXpath_BtnGoToCourseDetails));
				}
				break;
			}
		}
		u.waitForLoading();
		CourseDetails objCourseDetails = new CourseDetails(u);
		objCourseDetails.verifyMyProfilePageIsDisplayed();
		objCourseDetails.navigateBackToDashboard();
		u.waitForLoading();
		String newLastAccessedCourseDetails = u.getText(byLastAccessedCourse);
		if (!oldLastAccessedCourse.equals(newLastAccessedCourseDetails))
			u.rep.logInReport("Pass",
					"Last accessed course name is displayed as expected: " + newLastAccessedCourseDetails);
		else
			u.rep.logInReport("Fail", "Failed to display course count correctly " + "<br>Expected: "
					+ expectedLastAccessedCourse + "<br>Actual: " + newLastAccessedCourseDetails);
	}

	public void verifyCourseCountListed() {
		String actualCourseCount = u.getText(byRegisteredCourseTitle).replace("Registered Courses ", "")
				.replace("-", "").trim();
		if (Integer.parseInt(actualCourseCount) == getCourseCountFromDB())
			u.rep.logInReport("Pass",
					"The label 'Registered Courses  - ' is displayed along with the number of enrolled courses");
		else
			u.rep.logInReport("Fail", "Failed to display course count correctly " + "<br>Expected: "
					+ getCourseCountFromDB() + "<br>Actual: " + actualCourseCount);
	}

	public void validateCourseNamesInCards() {
		String actualCourseName = "";
		List<WebElement> eTileHeaders = u.elements(byTotalTileHeaders);
		for (int i = 0; i < eTileHeaders.size(); i++) {
			actualCourseName = eTileHeaders.get(i).getText();
			try {
				u.elements(byTotalTilePercentage).get(i).click();
			} catch (Exception e) {
				String dynamicXpath_BtnGoToCourseDetails = "(//div[@class='carousel-cell ng-star-inserted'])[" + i
						+ "]//div[contains(@class,'course-item-hover')]//button[@class='goto-course' and normalize-space(text())='Go to Course Details']";
				u.aClick(By.xpath(dynamicXpath_BtnGoToCourseDetails));
			}
		
		u.waitForLoading();
		CourseDetails objCourseDetails = new CourseDetails(u);
		objCourseDetails.verifyMyProfilePageIsDisplayed();
		String expectedCourseName = objCourseDetails.getCourseName();
		objCourseDetails.navigateBackToDashboard();
		u.waitForLoading();
		if (!expectedCourseName.trim().equalsIgnoreCase(actualCourseName.trim()))
			u.rep.logInReport("Pass",
					"Course name in cards are displayed as expected for course: " + actualCourseName);
		else
			u.rep.logInReport("Fail", "Failed to display Course name in cards correctly " + "<br>Expected: "
					+ expectedCourseName + "<br>Actual: " + actualCourseName);
		}
	}

	public int getCourseCountFromDB() {
		// ToDo: Handle using DB
		return 29;
	}

	public int getPageCount() {
		List<WebElement> ePageCount = u.elements(byPageCounts);
		return ePageCount.size();
	}

}
