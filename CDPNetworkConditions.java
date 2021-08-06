package example.test;

import java.time.Duration;
import java.util.Optional;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v91.network.model.ConnectionType;
import org.openqa.selenium.devtools.v91.network.Network;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import static org.openqa.selenium.devtools.v91.network.Network.loadingFailed;

public class CDPNetworkConditions {
	public ChromeDriver driver;
	DevTools devTool;

	@BeforeMethod
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		devTool = driver.getDevTools();
	}

	@AfterMethod
	public void afterMethod() {
		driver.quit();
	}

	@Test
	public void emulateSlowNetwork() {
		devTool.createSession();
		devTool.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		devTool.send(
				Network.emulateNetworkConditions(false, 100, 200000, 100000, Optional.of(ConnectionType.CELLULAR3G)));

		long startTime = System.currentTimeMillis();
		driver.get("https://www.qed42.com");
		long endTime = System.currentTimeMillis();

		System.out.println("Slow Network: Page loaded in " + (endTime - startTime) + " milliseconds");
	}

	@Test
	public void emulateNetworkOffline() {
		devTool.createSession();
		devTool.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		devTool.send(
				Network.emulateNetworkConditions(true, 100, 200000, 100000, Optional.of(ConnectionType.CELLULAR3G)));

		devTool.addListener(loadingFailed(), loadingFailed -> {
			Assert.assertEquals(loadingFailed.getErrorText(), "net::ERR_INTERNET_DISCONNECTED");
		});

		try {
			driver.get("https://www.qed42.com");
		} catch (WebDriverException exc) {
			System.out.println("Network Offline: " + driver.getCurrentUrl() + " did not load");
		}
	}

	@Test
	public void accessURLNormal() {
		long startTime = System.currentTimeMillis();
		driver.get("https://www.qed42.com");
		long endTime = System.currentTimeMillis();

		System.out.println("Normal Way: Page loaded in " + (endTime - startTime) + " milliseconds");
	}
}
