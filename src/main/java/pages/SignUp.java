package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import utility.GenericUtility;

public class SignUp {
	GenericUtility u;

	public SignUp(GenericUtility u) {
		this.u = u;
	}

	By byShortCourses = By.xpath("//a[normalize-space(text())='Short Courses']");
	By byFirstName= By.id("reg_first_name");
	By byLastName= By.id("reg_last_name");
	By byEmail = By.id("reg_email");
	By byCountryCode = By.xpath("//input[@id='contact_number']/preceding-sibling::*//div[@class='selected-flag dropdown-toggle']");
	By byCountryCodeDropdownValues ;
	By byMobileNo = By.id("contact_number");
	By byPassword = By.id("password");
	By byTermsAndConditions = By.id("reg_terms");
	By bySignUpButton = By.id("registration_form");

	public String signUpForNewUser(String fName,String lName,String country,String mobileNo, String email, String pWord) {
		email = email.replace("<>", "_"+u.getRandomValue());
		u.enterTextbox(byFirstName, fName);
		u.enterTextbox(byLastName , lName);
		u.enterTextbox(byEmail, email);
		u.click(byCountryCode);
		byCountryCodeDropdownValues = By.xpath("//input[@id='contact_number']/preceding-sibling::*//span[@class='country-name' or text()='"+country+"' or contains(text(),\""+country+" (\")]");
		u.waitTime(2);
		u.click(byCountryCodeDropdownValues);
		u.enterTextbox(byMobileNo, mobileNo);
		u.enterTextbox(byPassword, pWord);
		u.click(byTermsAndConditions);
		u.click(bySignUpButton);
		
		return email;
	}

	public void clickShortCourses() {
		u.aClick(byShortCourses);
	}

}
