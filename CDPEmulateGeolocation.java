package example.test;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import io.github.bonigarcia.wdm.WebDriverManager;

public class CDPEmulateGeolocation {
	public ChromeDriver driver;

	@BeforeClass
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	@Test
	public void emulateGeolocation() {
		Map coordinates = new HashMap() {
			{
				put("latitude", 40.7128);
				put("longitude", -74.0060);
				put("accuracy", 1);
			}
		};

		driver.executeCdpCommand("Emulation.setGeolocationOverride", coordinates);
		driver.get("https://where-am-i.org/");

		System.out.println("-----GeoLocation----");
		System.out.println("Location: " + driver.findElement(By.id("address")).getText());
		System.out.println("Latitude: " + driver.findElement(By.id("latitude")).getText());
		System.out.println("Longitude: " + driver.findElement(By.id("longitude")).getText());
		System.out.println("--------------------");
	}

}
