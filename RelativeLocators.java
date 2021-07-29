package example.test;

import static org.openqa.selenium.support.locators.RelativeLocator.with;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

public class RelativeLocators {
	public WebDriver driver;

	@BeforeClass
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://phptravels.org/index.php?rp=/login");
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	@Test (priority=1)
	public void setEmailAddress() {
		WebElement txtEmail = driver.findElement(with(By.tagName("input")).above(By.id("inputPassword")));
		txtEmail.sendKeys("test@gmail.com");
	}

	@Test (priority=2)
	public void setPassword() {
		WebElement txtPassword = driver.findElement(with(By.tagName("input")).below(By.id("inputEmail")));
		txtPassword.sendKeys("Test@123");
	}

	@Test (priority=3)	
	public void clickLogin() {
		WebElement btnForgotPwd = driver
				.findElement(with(By.tagName("input")).toLeftOf(By.linkText("Forgot Password?")));
		btnForgotPwd.click();
	}

	@Test (priority=5)
	public void clickForgotPwd() {
		WebElement btnLogin = driver.findElement(with(By.tagName("a")).toRightOf(By.id("login")));
		btnLogin.click();
	}

	@Test (priority=4)	
	public void nearElement() {
		WebElement emailAddrField = driver.findElement(By.id("inputEmail"));
		WebElement emailAddrLabel = driver.findElement(with(By.tagName("label")).near(emailAddrField));

		Assert.assertEquals(emailAddrLabel.getText(), "Email Address");
	}
}
