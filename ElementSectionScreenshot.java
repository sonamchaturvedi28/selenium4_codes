package example.test;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class ElementSectionScreenshot {
	public WebDriver driver;

	@BeforeClass
	public void setUp() {
		WebDriverManager.firefoxdriver().setup();
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https:www.qed42.com");
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	@Test
	public void takeElementScreenshot() throws IOException {
		WebElement elementLogo = driver.findElement(By.className("site-logo"));

		File source = elementLogo.getScreenshotAs(OutputType.FILE);
		File dest = new File(System.getProperty("user.dir") + "/screenshots/elementLogo.png");

		try {
			FileHandler.copy(source, dest);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	@Test
	public void takeSectionScreenshot() throws IOException {
		WebElement btnCookie = driver.findElement(By.cssSelector(".agree-button"));
		btnCookie.click();

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,450)");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".qed42-services")));

		WebElement elementLogo = driver.findElement(By.cssSelector(".qed42-services"));

		File source = elementLogo.getScreenshotAs(OutputType.FILE);
		File dest = new File(System.getProperty("user.dir") + "/screenshots/service-wrapper.png");

		try {
			FileHandler.copy(source, dest);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

}
