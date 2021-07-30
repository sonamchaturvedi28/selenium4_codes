package example.test;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

public class WindowHandling {
	public WebDriver driver;

	@BeforeClass
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://www.qed42.com/");
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	@Test
	public void openNewTab() {
		WebDriver newTab = driver.switchTo().newWindow(WindowType.TAB);
		newTab.get("https://www.google.com/");
		System.out.println("New Tab Page Title:" + newTab.getTitle());
	}

	@Test
	public void openNewWindow() {
		WebDriver newWindow = driver.switchTo().newWindow(WindowType.WINDOW);
		newWindow.get("https://www.facebook.com/");
		System.out.println("New Window Page Title:" + newWindow.getTitle());
	}
}
