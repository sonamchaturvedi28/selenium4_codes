package example.test;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

public class SwitchWindow {
	public WebDriver driver;
	String originalWindow;

	@BeforeClass
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://www.qed42.com/");
		originalWindow = driver.getWindowHandle();
		System.out.println("Original Window Page Title:" + driver.getTitle());
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	@Test
	public void switchToOriginalWindow() {
		driver.switchTo().newWindow(WindowType.TAB);
		driver.navigate().to("https://www.google.com/");
		System.out.println("New Tab Page Title: " + driver.getTitle());

		driver.switchTo().newWindow(WindowType.WINDOW);
		driver.navigate().to("https://www.facebook.com/");
		System.out.println("New Window Page Title: " + driver.getTitle());

		// Loop through until we find a original window handle
		for (String windowHandle : driver.getWindowHandles()) {
			if (originalWindow.contentEquals(windowHandle)) {
				driver.switchTo().window(originalWindow);
				break;
			}
		}
		System.out.println("Original Window Page Title after switch: " + driver.getTitle());
	}

}
