package utility;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/*
 * GenericUtility is inherited by the Page Object Model classes in src/main/java/pages
 */
public class GenericUtility {
	protected WebDriver driver;
	WebElement webElement;
	WebElement e;
	String currentDirectory = System.getProperty("user.dir");
	String data;
	public ExtentReport rep;
	String reportScreenshotFileName;

	public GenericUtility(WebDriver driver, String className) {
		this.driver = driver;
		rep = new ExtentReport(this);
		rep.initiateExtentReport();
		rep.startTest(className);
	}

	public GenericUtility(String browser, ExtentReport rep) throws Exception {
		if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", currentDirectory + "\\drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		} else if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", currentDirectory + "\\drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("Edge")) {
			System.setProperty("webdriver.edge.driver", currentDirectory + "\\drivers\\MicrosoftWebDriver.exe");
			driver = new EdgeDriver();
		} else {
			throw new Exception("Browser is not correct");
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		this.rep=rep;
		rep.setGenericUtilityInstance(this);
//		rep = new ExtentReport(this);
//		rep.initiateExtentReport(className);
	}
//	public void setReportInstance(ExtentReport rep) {
//		this.rep=rep;
//	}

	// *********************** Screenshot **********************************
//	public String takeScreenshot() {
//		//waitForLoading();
//		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//		File Dest = new File(System.getProperty("user.dir") + "\\ExtentReport\\" + reportScreenshotFileName + "\\Screenshot"
//				+ System.currentTimeMillis() + ".png");
//		String flpath = Dest.getAbsolutePath();
//		try {
//			FileUtils.copyFile(scrFile, Dest);
//		} catch (IOException e) {
//		}
//		return flpath;
//	}
	
	public String takeScreenshot() {
		String base64Screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
		return base64Screenshot;
	}

	// *********************** Launching URL **********************************
	public void launchUrl(String url) {
		driver.get(url);
		driver.manage().window().maximize();
		waitForLoading();
	}

	// *********************** Navigation Commands ****************************
	public void navigateBack() {
		driver.navigate().back();
	}

	// *********************** Wait Commands **********************************
	public WebElement waitToClick(By by) {
		//waitForLoading();
		try {
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(by)));

		} catch (Exception e) {
			waitTime(2);
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(by)));
		}
		return driver.findElement(by);
	}

	public WebElement waitToVisible(By by) {
		//waitForLoading();
		try {
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(by)));
		} catch (Exception e) {
			waitTime(2);
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(by)));
		}
		return driver.findElement(by);
	}

	public void waitUntilInvisible(By by) {
//			WebDriverWait wait = new WebDriverWait(driver, 20);
//			wait.ignoring(NoSuchElementException.class);
//			wait.ignoring(ElementNotVisibleException.class);
//			wait.ignoring(StaleElementReferenceException.class);
//			wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
		waitTime(2);
		for(int i=0;i<10; i++) {
			try {
				if(driver.findElement(by).isDisplayed()) waitTime(5);
				else break;
			}catch(Exception e) {break;}
		}
	}
	public void waitTime(int timeInSecs) {
		try {
			Thread.sleep(1000 * timeInSecs);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ************************** Web Element Commands *************************
	public WebElement element(By by) {
		//waitForLoading();
		// waitToClick(by);
		e = driver.findElement(by);
		return e;
	}
	public List<WebElement> elements(By by) {
		//waitForLoading();
		waitToVisible(by);
		return driver.findElements(by);
	}

	public WebElement click(By by) {
		waitToClick(by);
		e = driver.findElement(by);
		e.click();
		return e;
	}

	public WebElement enterTextbox(By by, String text) {
		//waitForLoading();
		waitToClick(by);
		e = driver.findElement(by);
		click(by);
		e.clear();
		e.sendKeys(text);
		return e;
	}

	public String getText(By locator) {
		//waitForLoading();
		webElement = driver.findElement(locator);
		data = webElement.getText();
		return data;
	}
	
	public void isDisplayed(By byLocator,String elementName) {
		//waitForLoading();
		waitToVisible(byLocator);
		if(driver.findElement(byLocator).isDisplayed())
			rep.logInReport("Pass", elementName+" is displayed");
		else
			rep.logInReport("Fail", elementName+" is NOT displayed");
	}
	public boolean isDisplayed(By byLocator) {
		//waitForLoading();
		waitToVisible(byLocator);
		if(driver.findElement(byLocator).isDisplayed())
			return true;
		else
			return false;
	}
	// ************************** Javascript Class Commands *************************
	
	public void jsClick(By by) {
		//waitForLoading();
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("arguments[0].click();", element(by));
		
	}
	// ************************** Actions Class Commands *************************
	public WebElement aClick(By by) {
		//waitForLoading();
		waitToClick(by);
		e = driver.findElement(by);
		Actions actions = new Actions(driver);
		actions.moveToElement(e).click(e).build().perform();
		return e;
	}

	// ************************** Drop Down Commands *************************
	public WebElement selectDropDownByIndex(By by, int index) {
		//waitForLoading();
		waitToClick(by);
		e = driver.findElement(by);
		e.isSelected();
		Select s1 = new Select(e);
		s1.selectByIndex(index);
		return e;
	}

	public WebElement selectDropDownByValue(By by, String value) {
		//waitForLoading();
		waitToClick(by);
		e = driver.findElement(by);
		e.isSelected();
		Select s1 = new Select(e);
		s1.selectByValue(value);
		return e;
	}

	public WebElement selectDropDownByVisibleText(By by, String text) {
		//waitForLoading();
		waitToClick(by);
		e = driver.findElement(by);
		e.isSelected();
		Select s1 = new Select(e);
		s1.selectByVisibleText(text);
		return e;
	}

	public WebDriver getDriver() {
		return driver;
	}

	//******************************** Misc *********************************
	public static String getRandomValue() {
		return "" + System.currentTimeMillis();
	}
	public static String getDateTime() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		return LocalDate.now().format(formatter)+"_"+getRandomValue();
	}
	//******************************** Project Specific *********************************

	public void waitForLoading() {
		By byLoading = By.xpath("//img[@class='ath-spinner']");
		try {waitUntilInvisible(byLoading);}catch(Exception e) {}
	}
}
