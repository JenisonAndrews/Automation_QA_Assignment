package Task1;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class PlaceOrder {

	public static void main(String args[])
	{
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\jenis\\Selenium\\chromedriver.exe");		

		WebDriver driver = new ChromeDriver();
		WebDriverWait wait= new WebDriverWait(driver,50);

		driver.get("https://www.flipkart.com/");
		driver.manage().window().maximize();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Actions actions = new Actions(driver);
		actions.sendKeys(Keys.ESCAPE);
		String mainWindowHandle = driver.getWindowHandle();	


		//Adding First ITEM to the cart		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='q']"))); 
		WebElement txtSearch =driver.findElement(By.xpath("//input[@name='q']")); 
		WebElement btnSearch=driver.findElement(By.xpath("//button[@class='L0Z3Pu']")); 		
		txtSearch.sendKeys("Hp Laptop"); 
		actions.moveToElement(btnSearch).click().build().perform();	
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-id='COMG6MVEA2PWVH3B']"))); 
		WebElement blProduct1=driver.findElement(By.xpath("//div[@data-id='COMG6MVEA2PWVH3B']"));				
		blProduct1.click();
		addtocart(driver,wait,actions,mainWindowHandle);

		//Adding Second ITEM to the cart
		txtSearch.clear();
		txtSearch.sendKeys("Mobile"); 
		actions.moveToElement(btnSearch).click().build().perform();
		WebElement txtProduct2 =driver.findElement(By.xpath("//div[@data-id='MOBGF4894MEWZJGV']"));
		txtProduct2.click();
		addtocart(driver,wait,actions,mainWindowHandle);
		
		//Remove Item From cart
		removeItemFromCart(driver,wait,actions,mainWindowHandle);
		
		//Verify last item in Cart

		String LastItemIncart = driver.findElement(By.xpath("//a[@class='_2Kn22P gBNbID']")).getText();		
		System.out.print("Last Item in Cart is " +LastItemIncart);
		
		//Navigating to the home page
		driver.navigate().back();



	}

	public static void addtocart(WebDriver driver,WebDriverWait wait,Actions actions,String mainWindowHandle)
	{
		Set<String> allWindowHandles = driver.getWindowHandles();
		Iterator<String> iterator = allWindowHandles.iterator();

		// Here we will check if child window has other child windows and will fetch the heading of the child window


		while (iterator.hasNext()) {
			String ChildWindow = iterator.next();
			if (!mainWindowHandle.equalsIgnoreCase(ChildWindow)) {
				driver.switchTo().window(ChildWindow);

				WebElement btnAddtoCart = driver.findElement(By.xpath("//button[@class='_2KpZ6l _2U9uOA _3v1-ww']"));
				actions.moveToElement(btnAddtoCart).click().build().perform();

				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='_2KpZ6l _2ObVJD _3AWRsL']")));
				driver.close();
			}
		}

		driver.switchTo().window(mainWindowHandle);
	}

	public static void removeItemFromCart(WebDriver driver,WebDriverWait wait,Actions actions,String mainWindowHandle)
	{

		WebElement cart= driver.findElement(By.xpath("//a[@class='_3SkBxJ']"));
		actions.moveToElement(cart).click().build().perform();

		driver.findElement(By.xpath("//button[@class='_2KpZ6l _2ObVJD _3AWRsL']//span[text()='Place Order']")).isDisplayed();

		driver.findElement(By.xpath("(//div[@class='zab8Yh _10k93p'])[1]")).isDisplayed();
		driver.findElement(By.xpath("(//div[@class='zab8Yh _10k93p'])[2]")).isDisplayed();

		WebElement linkRemoveSecond = driver.findElement(By.xpath("(//div[@class='_3dsJAO'])[4]"));
		actions.moveToElement(linkRemoveSecond).click().build().perform();

		WebElement btnRemove = driver.findElement(By.xpath("//div[@class='_3dsJAO _24d-qY FhkMJZ']"));
		actions.moveToElement(btnRemove).click().build().perform();

	}




}
