package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import utility.GenericUtility;

public class SignIn {
	GenericUtility u;

	public SignIn(GenericUtility u) {
		this.u = u;
	}

	By byShortCourses = By.xpath("//a[normalize-space(text())='Short Courses']");
	By byEmail = By.id("reg_email");
	By byPassword = By.id("password");
	By byEmailError = By.id("mat-error-1");
	By login = By.id("registration_form");
	By bySignUpLink = By.xpath("//a[normalize-space(text())='Sign Up']");
 
	public void loginToApplication(String usrName, String pWord) {
		u.rep.logInReport("Info", "Signing into the application");
		u.enterTextbox(byEmail, usrName);
		u.enterTextbox(byPassword, pWord);
		u.click(login);
	}
	
	public void clickOnSignUp() {
		u.click(bySignUpLink);
	}

	public void clickShortCourses() {
		u.click(byShortCourses);
	}

}