package example.test;

import org.testng.annotations.Test;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import java.time.Duration;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v91.log.Log;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CDPViewConsoleLogs {
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
	public void viewConsoleLogs() {
		DevTools devTools = driver.getDevTools();
		devTools.createSession();
		devTools.send(Log.enable());

		devTools.addListener(Log.entryAdded(), logEntry -> {
			System.out.println("------------------------------------------------------------");
			System.out.println("Request ID = " + logEntry.getNetworkRequestId());
			System.out.println("URL = " + logEntry.getUrl());
			System.out.println("Source = " + logEntry.getSource());
			System.out.println("Level = " + logEntry.getLevel());
			System.out.println("Text = " + logEntry.getText());
			System.out.println("Timestamp = " + logEntry.getTimestamp());
			System.out.println("------------------------------------------------------------");
		});
		driver.get("https://www.qed42.com/404");
	}
}
