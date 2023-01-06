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
	By byLastAccessedCourseLabel = By.xpath("//span[@class='last-accessed-course']/parent::p");
	By byLastAccessedCourse = By.xpath("//span[@class='last-accessed-course']");

	By bySearchInput = By.xpath("//input[@placeholder='Search your Registered Course']");
	By bySearchIcon = By.xpath(
			"//input[@placeholder='Search your Registered Course']/following-sibling::*//span[contains(@class,'find')]");
	By bySearchClose = By.xpath("//span[normalize-space(text())='close']']");

	By byList_TotalTiles = By.xpath("//div[@class='carousel-cell ng-star-inserted']");
	By byList_TotalTileHeaders = By.xpath("//div[@class='carousel-cell ng-star-inserted']//h3");
	By byList_TotalTilePercentage = By.xpath("//div[@class='course-area text-center']");
	By byList_TotalTileCourseDetailsHoverButton = By.xpath(
			"//div[contains(@class,'course-item-hover')]//button[@class='goto-course' and normalize-space(text())='Go to Course Details']");

	By byList_LessonsLearnedLabels = By.xpath("//p[normalize-space(text())='Lessons Left']");
	By byList_LessonsLearnedValues = By.xpath("//p[normalize-space(text())='Lessons Left']/preceding-sibling::p");
	By byList_LessonsLearnedCourseNames = By
			.xpath("//p[normalize-space(text())='Lessons Left']/ancestor::div[@class='course-details']//h3");

	By byList_TileShareFacebook = By.xpath("//div[@class='carousel-cell ng-star-inserted']//a/img[contains(@src,'facebook')]");
	By byList_TileShareTwitter = By.xpath("//div[@class='carousel-cell ng-star-inserted']//a/img[contains(@src,'twitter')]");
	By byList_TileShareLinkedin= By.xpath("//div[@class='carousel-cell ng-star-inserted']//a/img[contains(@src,'linkedin')]");
	By byList_TileShareWhatsapp= By.xpath("//div[@class='carousel-cell ng-star-inserted']//a/img[contains(@src,'whatsapp')]");
	By byList_TileShareEmail= By.xpath("//div[@class='carousel-cell ng-star-inserted']//a/img[contains(@src,'email')]");
	
	By byLoading_RegisteredCouses = By.xpath("//img[@alt='Registered Courses']");
	By byPageCounts = By.xpath("//ul[@class='pagination']/li");

	public void searchCourseAndValidateResults(String courseName) {
		u.aClick(bySearchInput);
		u.enterTextbox(bySearchInput, courseName);
		u.aClick(bySearchIcon);
		u.waitUntilInvisible(byLoading_RegisteredCouses);

		Boolean bSearchWorksFine = true;
		String actualHeader = "";
		List<WebElement> eTotalTileHeaders = u.elements(byList_TotalTileHeaders);
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

	public void exitSearch() {
		u.click(bySearchClose);
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
		List<WebElement> eTileHeaders = u.elements(byList_TotalTileHeaders);
		for (int i = 0; i < eTileHeaders.size(); i++) {
			expectedLastAccessedCourse = eTileHeaders.get(i).getText();
			if (!expectedLastAccessedCourse.equals(oldLastAccessedCourse)) {
				try {
					u.elements(byList_TotalTilePercentage).get(i).click();
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
		List<WebElement> eTileHeaders = u.elements(byList_TotalTileHeaders);
		for (int i = 0; i < eTileHeaders.size(); i++) {
			actualCourseName = eTileHeaders.get(i).getText();
			try {
				u.elements(byList_TotalTilePercentage).get(i).click();
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
			if (expectedCourseName.trim().toLowerCase().equalsIgnoreCase(actualCourseName.trim().toLowerCase()))
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

	public void validateLessonsLeftValue() {
		// ToDo: Handle using DB: Fetch lessons left query
		List<WebElement> eLessonLearnedValues = u.elements(byList_LessonsLearnedValues);
		List<WebElement> eLessonLearnedCourseNames = u.elements(byList_LessonsLearnedCourseNames);
		for (int i = 0; i < eLessonLearnedValues.size(); i++) {
			String expected = "";// ToDo: Query to fetch expected count
			String actual = eLessonLearnedValues.get(i).getText();
			if (expected.equals(actual))
				u.rep.logInReport("Pass", "Lessons learnt count in cards are displayed as expected for course:<b> "
						+ eLessonLearnedCourseNames.get(i).getText() + "<\b>");
			else
				u.rep.logInReport("Fail",
						"Failed to display Lessons learnt count in cards correctly for couse '"
								+ eLessonLearnedCourseNames.get(i).getText() + "'<br>Expected: " + expected
								+ "<br>Actual: " + actual);
		}
	}

	public void validateIfSharingOptionIsEnabled() {
		List<WebElement> eTileHeaders = u.elements(byList_TotalTileHeaders);
		List<WebElement> eTileShareFacebook = u.elements(byList_TileShareFacebook);
		List<WebElement> eTileShareTwitter= u.elements(byList_TileShareTwitter);
		List<WebElement> eTileShareLinkedIn= u.elements(byList_TileShareLinkedin);
		List<WebElement> eTileShareWhatsapp= u.elements(byList_TileShareWhatsapp);
		List<WebElement> eTileShareEmail= u.elements(byList_TileShareEmail);
		
		for (int i = 0; i < eTileHeaders.size(); i++) {
			Boolean bPaymentPending = true;
			//ToDo: Uncomment and replace with required query
//			if(eTileHeaders.get(i).getText()'s 'is_paid' field is returning "Paid" status)
//					bPaymentPending=false;
			if(bPaymentPending) {
				String courseName = eTileHeaders.get(i).getText();
				u.aHover(eTileHeaders.get(i));
				if(eTileShareFacebook.get(i).isDisplayed() 
						&& eTileShareTwitter.get(i).isDisplayed()
						&& eTileShareLinkedIn.get(i).isDisplayed()
						&& eTileShareWhatsapp.get(i).isDisplayed()
						&& eTileShareEmail.get(i).isDisplayed())
						u.rep.logInReport("Pass", "Share icons are displayed as expected for course card: "+courseName);	
					else if(!eTileShareFacebook.get(i).isDisplayed())
						u.rep.logInReport("Fail","Failed to display Facebook share icon in course card: "+courseName);
					else if(!eTileShareTwitter.get(i).isDisplayed())
						u.rep.logInReport("Fail","Failed to display Twitter share icon in course card: "+courseName);
					else if(!eTileShareLinkedIn.get(i).isDisplayed())
						u.rep.logInReport("Fail","Failed to display LinkedIn share icon in course card: "+courseName);
					else if(!eTileShareWhatsapp.get(i).isDisplayed())
						u.rep.logInReport("Fail","Failed to display Whatsapp share icon in course card: "+courseName);
					else if(!eTileShareEmail.get(i).isDisplayed())
						u.rep.logInReport("Fail","Failed to display Email share icon in course card: "+courseName);
			}
		}

	}

}
