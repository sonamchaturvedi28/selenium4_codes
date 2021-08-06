package example.test;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import java.time.Duration;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v91.security.Security;
import io.github.bonigarcia.wdm.WebDriverManager;

public class CDPCertificateError {
	public ChromeDriver driver;
	DevTools devTool;

	@BeforeClass
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		devTool = driver.getDevTools();
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	@Test
	public void loadUntrustedSite() {
		devTool.createSession();
		devTool.send(Security.enable());
		devTool.send(Security.setIgnoreCertificateErrors(true));

		driver.get("https://expired.badssl.com/");
	}

}
