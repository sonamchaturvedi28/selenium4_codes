package example.test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

public class FullPageScreenshot {
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
	public void takeFullPageScreenshot() throws IOException {
		File source = ((FirefoxDriver) driver).getFullPageScreenshotAs(OutputType.FILE);
		FileHandler.copy(source, new File("QED_Full_Page_Screenshot.png"));
	}

	@Test
	public void takePageScreenshot() throws IOException {
		File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileHandler.copy(source, new File("QED_Page_Screenshot.png"));
	}
}
