package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import utility.GenericUtility;

public class SignUp {
	GenericUtility u;
	String strCountry = "";

	public SignUp(GenericUtility u) {
		this.u = u;
	}

	By byShortCourses = By.xpath("//a[normalize-space(text())='Short Courses']");
	By byFirstName= By.id("reg_first_name");
	By byLastName= By.id("reg_last_name");
	By byEmail = By.id("reg_email");
	By byCountryCode = By.xpath("//input[@id='contact_number']/preceding-sibling::*//div[@class='selected-flag dropdown-toggle']");
	By byCountryCodeDropdownValues = By.xpath("//input[@id='contact_number']/preceding-sibling::*//span[@class='country-name' and text()='"+strCountry+"']");
	By byMobileNo = By.id("contact_number");
	By byPassword = By.id("password");
	By byTermsAndConditions = By.id("reg_terms");
	By bySignUpButton = By.id("registration_form");

	public void signUpForNewUser(String fName,String lName,String country,String mobileNo, String email, String pWord) {
		u.enterTextbox(byFirstName, fName);
		u.enterTextbox(byLastName , lName);
		u.enterTextbox(byEmail, email);
		u.click(byCountryCode);
		strCountry=country;
		u.click(byCountryCodeDropdownValues);
		u.enterTextbox(byMobileNo, mobileNo);
		u.enterTextbox(byPassword, pWord);
		u.click(byTermsAndConditions);
		u.click(bySignUpButton);
	}

	public void clickShortCourses() {
		u.click(byShortCourses);
	}

}
