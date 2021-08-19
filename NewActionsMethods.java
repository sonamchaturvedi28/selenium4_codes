package example.test;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.testng.Assert;

public class NewActionsMethods {

	public WebDriver driver;

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
	public void clickAction() {
		driver.get("http://demo.guru99.com/test/newtours/");
		Actions action = new Actions(driver);
		WebElement signOnLink = driver.findElement(By.linkText("SIGN-ON"));

		action.click(signOnLink).build().perform();
		Assert.assertEquals(driver.getTitle(), "Sign-on: Mercury Tours");
	}

	@Test
	public void dblClickAction() {
		driver.get("https://api.jquery.com/dblclick/");
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe")));

		Actions action = new Actions(driver);
		WebElement doubleClickBox = driver
				.findElement(By.xpath("//span[text()='Double click the block']//parent::body/div"));

		System.out.println("Color Before: " + doubleClickBox.getCssValue("background-color"));
		action.doubleClick(doubleClickBox).build().perform();
		System.out.println("Color After: " + doubleClickBox.getCssValue("background-color"));
	}

	@Test
	public void contextClickAction() {
		driver.get("https://swisnl.github.io/jQuery-contextMenu/demo.html");
		WebElement rightBtn = driver.findElement(By.className("btn"));

		Actions action = new Actions(driver);
		action.contextClick(rightBtn).perform();

		List<WebElement> elements = driver.findElements(By.cssSelector("li span"));
		System.out.println("WebElements After Right Click:");
		for (WebElement element : elements) {
			System.out.println("\t" + element.getText());
		}
	}
	
	@Test
	public void clickAndHoldAction() throws InterruptedException {
		driver.get("https://jqueryui.com/draggable/");
		WebElement dragFrame = driver.findElement(By.xpath("//*[@id=\"content\"]/iframe"));
		
		driver.switchTo().frame(0);
		WebElement dragBox = driver.findElement(By.id("draggable"));

		Actions action = new Actions(driver);
		action.clickAndHold(dragBox).moveByOffset(75, 75).build().perform();
		
		driver.switchTo().parentFrame();
		File source = dragFrame.getScreenshotAs(OutputType.FILE);
		File dest = new File(System.getProperty("user.dir") + "/screenshots/dragBox.png");

		try {
			FileHandler.copy(source, dest);
		} catch (IOException exception) {
			exception.printStackTrace();
		}	
	}
	
	@Test
	public void releaseAction() throws InterruptedException {
		driver.get("https://jqueryui.com/droppable/");
		driver.switchTo().frame(0);
		
		WebElement source = driver.findElement(By.id("draggable"));
		WebElement destination = driver.findElement(By.id("droppable"));

		Actions action = new Actions(driver);
		//Alternate way: action.clickAndHold(source).moveToElement(destination).release().build().perform();
		action.clickAndHold(source).release(destination).build().perform();

		File src = destination.getScreenshotAs(OutputType.FILE);
		File dest = new File(System.getProperty("user.dir") + "/screenshots/release.png");

		try {
			FileHandler.copy(src, dest);
		} catch (IOException exception) {
			exception.printStackTrace();
		}	
	}
}
