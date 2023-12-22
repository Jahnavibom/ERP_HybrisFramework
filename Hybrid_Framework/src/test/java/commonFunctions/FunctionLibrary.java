package commonFunctions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class FunctionLibrary {
public static WebDriver driver;
public static Properties conpro;
// method for launching browser
public static WebDriver startBrowser() throws Throwable
{
	conpro = new Properties();
	conpro.load(new FileInputStream("./PropertyFiles/Environment.properties"));
	if(conpro.getProperty("Browser").equalsIgnoreCase("chrome"))
	{
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		
	}
	else if (conpro.getProperty("Browser").equalsIgnoreCase("firefox"))
	{
		driver = new FirefoxDriver();
	}
	else
	{
		Reporter.log("Browser not matching",true);
	}
	return driver;
}
// method to launch URL
public static void openUrl(WebDriver driver)
{
	driver.get(conpro.getProperty("Url"));
}
// method for wait for element 
public static void waitForElement(WebDriver driver, String Locator_Type, String Locator_Value, String Test_Data)
{
	WebDriverWait myWait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(Test_Data) ));
	if(Locator_Type.equalsIgnoreCase("id"))
	{
		myWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(Locator_Value)));
	}
	if(Locator_Type.equalsIgnoreCase("name"))
	{
		myWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(Locator_Value)));
	}
	
	if(Locator_Type.equalsIgnoreCase("xpath"))
	{
		myWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locator_Value)));
	}
}
// method for text boxes
public static void typeAction(WebDriver driver, String Locator_Type, String Locator_Value, String Test_Data)
{
	if(Locator_Type.equalsIgnoreCase("id"))
	{
		driver.findElement(By.id(Locator_Value)).clear();
		driver.findElement(By.id(Locator_Value)).sendKeys(Test_Data);
	}
	if(Locator_Type.equalsIgnoreCase("name"))
	{
		driver.findElement(By.name(Locator_Value)).clear();
		driver.findElement(By.name(Locator_Value)).sendKeys(Test_Data);
	}
	if(Locator_Type.equalsIgnoreCase("xpath"))
	{
		driver.findElement(By.xpath(Locator_Value)).clear();
		driver.findElement(By.xpath(Locator_Value)).sendKeys(Test_Data);
	}
}
// methods for check boxes, radio buttons, buttons, links and images
public static void clickAction(WebDriver driver, String Locator_Type, String Locator_Value)
{
	if(Locator_Type.equalsIgnoreCase("name"))
	{
		driver.findElement(By.name(Locator_Value)).click();
	}
	if(Locator_Type.equalsIgnoreCase("id"))
	{
		driver.findElement(By.id(Locator_Value)).sendKeys(Keys.ENTER);;
	}
	if(Locator_Type.equalsIgnoreCase("xpath"))
	{
		driver.findElement(By.xpath(Locator_Value)).click();
	}
}

// method to validate title
public static void validateTitle( WebDriver driver , String Expected_Title)
{
	String Actual_Title = driver.getTitle();
	try {
	Assert.assertEquals(Actual_Title, Expected_Title, "Title is not matching");
	}catch(Throwable t)
	{
		System.out.println(t.getMessage());
	}
}
// method to closing driver

public static void closeBrowser( WebDriver driver)
{
	driver.quit();
}
// method for mouse click 
public static void mouseclick(WebDriver driver) throws Throwable
{
	Actions ac = new Actions(driver);
	ac.moveToElement(driver.findElement(By.xpath("//a[text()='Stock Items ']"))).perform();
	Thread.sleep(2000);
	ac.moveToElement(driver.findElement(By.xpath("(//a[.='Stock Categories'])[2]")));
	ac.pause(2000).click().perform();
}
// method for category table
public static void categoryTable(WebDriver driver, String Exp_Data) throws Throwable
{
	if(!driver.findElement(By.xpath(conpro.getProperty("search_textbox"))).isDisplayed())
		//if search text box not displayed click search search panel button
		driver.findElement(By.xpath(conpro.getProperty("search_panel"))).click();
	driver.findElement(By.xpath(conpro.getProperty("search_textbox"))).clear();
	driver.findElement(By.xpath(conpro.getProperty("search_textbox"))).sendKeys(Exp_Data);
	driver.findElement(By.xpath(conpro.getProperty("search_button"))).click();
	Thread.sleep(2000);
	String Act_data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[4]/div/span/span")).getText();
	Reporter.log(Exp_Data+"   "+ Act_data, true);
	try {
		Assert.assertEquals(Act_data, Exp_Data, "Category name is not found in table");
		}catch(Throwable t)
	{
			System.out.println(t.getMessage());
	}
}
	
//method for drop down
	public static void dropDownAction(WebDriver driver, String Locator_Type, String Locator_Value, String Test_Data)
	{
		if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			int value = Integer.parseInt(Test_Data);
			WebElement element = driver.findElement(By.xpath(Locator_Value));
			Select select = new Select(element);
			select.selectByIndex(value);
		}
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			int value = Integer.parseInt(Test_Data);
			WebElement element = driver.findElement(By.id(Locator_Value));
			Select select = new Select(element);
			select.selectByIndex(value);
		}
		if(Locator_Type.equalsIgnoreCase("name"))
		{
			int value = Integer.parseInt(Test_Data);
			WebElement element = driver.findElement(By.name(Locator_Value));
			Select select = new Select(element);
			select.selectByIndex(value);
		}
     }
	
	//Method to capture stock number into note pad
	public static void captureStock( WebDriver driver,String Locator_Type, String Locator_Value) throws Throwable
	{
		String stockNumber ="";
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			stockNumber = driver.findElement(By.id(Locator_Value)).getAttribute("value");
		}
		if(Locator_Type.equalsIgnoreCase("name"))
		{
			stockNumber = driver.findElement(By.name(Locator_Value)).getAttribute("value");
		}
		if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			stockNumber = driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
		}
		//create note pad and write Stock number into it
		FileWriter fw = new FileWriter("./CaptureData.stockNum.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(stockNumber);
		bw.flush();
		bw.close();
		
	}
	
	// Method for Stock Table
	public static void stockTable( WebDriver driver) throws Throwable
	{
		FileReader fr = new FileReader("./CaptureData.stockNum.txt");
		BufferedReader br = new BufferedReader(fr);
		String Exp_Data = br.readLine();
		if(!driver.findElement(By.xpath(conpro.getProperty("search_textbox"))).isDisplayed())
			//if search text box not displayed click search search panel button
			driver.findElement(By.xpath(conpro.getProperty("search_panel"))).click();
		driver.findElement(By.xpath(conpro.getProperty("search_textbox"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("search_textbox"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(conpro.getProperty("search_button"))).click();
		Thread.sleep(2000);
		String Act_data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
		Reporter.log(Act_data+"   "+ Exp_Data,true);
		Assert.assertEquals(Exp_Data, Act_data, "Stock number is not found in table");
		
		
	}
	// Method to capture supplier number in a notepad
	public static void captureSupplier( WebDriver driver, String Locator_Type, String Locator_Value) throws Throwable
	{
		String SupplierNum ="";
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			SupplierNum = driver.findElement(By.id(Locator_Value)).getAttribute("value");
		}
		if(Locator_Type.equalsIgnoreCase("name"))
		{
			SupplierNum = driver.findElement(By.name(Locator_Value)).getAttribute("value");
		}
		if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			SupplierNum = driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
		}
		// create note pad and write Supplier number into it
		FileWriter fw = new FileWriter("./CaptureData/SupplierNum.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(SupplierNum);
		bw.flush();
		bw.close();
		
	}
	
	// Method for Supplier table 
	 public static void supplierTable(WebDriver driver) throws Throwable
	 {
		 FileReader fr = new FileReader("./CaptureData/SupplierNum.txt");
		 BufferedReader br = new BufferedReader(fr);
		 String Exp_Data = br.readLine();
		 if(!driver.findElement(By.xpath(conpro.getProperty("search_textbox"))).isDisplayed())
				//if search text box not displayed click search search panel button
				driver.findElement(By.xpath(conpro.getProperty("search_panel"))).click();
			driver.findElement(By.xpath(conpro.getProperty("search_textbox"))).clear();
			driver.findElement(By.xpath(conpro.getProperty("search_textbox"))).sendKeys(Exp_Data);
			driver.findElement(By.xpath(conpro.getProperty("search_button"))).click();
			Thread.sleep(2000);
			String Act_data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[6]/div/span/span")).getText();
			Reporter.log(Exp_Data+"   "+ Act_data, true);
			try {
				Assert.assertEquals(Exp_Data, Act_data, "Supplier number not found in table");
			}catch (Throwable t)
			{
				System.out.println(t.getMessage());
			}
		
	 }
	 //method for date Generation
	 public static String generateDate()
	 {
		Date date = new Date(0);
		DateFormat df = new SimpleDateFormat("YYYY_MM_dd");
		return df.format(date);
				 
	 }
	 
	 //Method for capturing customer Number
	 public static void captureCustomer(WebDriver driver, String Locator_Type, String Locator_Value) throws Throwable
	 {
		 String customerNum="";
		 if(Locator_Type.equalsIgnoreCase("id"))
		 {
			 customerNum= driver.findElement(By.id(Locator_Value)).getAttribute("value");
		 }
		 if(Locator_Type.equalsIgnoreCase("name"))
		 {
			 customerNum= driver.findElement(By.name(Locator_Value)).getAttribute("value");
		 }
		 if(Locator_Type.equalsIgnoreCase("xpath"))
		 {
			 customerNum= driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
		 }
		 // create note pad and write customer number into it
		 FileWriter fw = new FileWriter("./CaptureData/CustomerNum.txt");
		 BufferedWriter bw = new BufferedWriter(fw);
		 bw.write(customerNum);
		 bw.flush();
		 bw.close();
		 
	 }
	 
	 // Method for customerTable
	 
	 public static void customerTable(WebDriver driver) throws Throwable
	 {
		 FileReader fr = new FileReader("./CaptureData/CustomerNum.txt");
		 BufferedReader br = new BufferedReader(fr);
		 String Exp_Data = br.readLine();
		 if(!driver.findElement(By.xpath(conpro.getProperty("search_textbox"))).isDisplayed())
			 
		//if search text box not displayed click search search panel button
			driver.findElement(By.xpath(conpro.getProperty("search_panel"))).click();
			driver.findElement(By.xpath(conpro.getProperty("search_textbox"))).clear();
			driver.findElement(By.xpath(conpro.getProperty("search_textbox"))).sendKeys(Exp_Data);
			driver.findElement(By.xpath(conpro.getProperty("search_button"))).click();
			Thread.sleep(2000);
			String Act_data = driver.findElement(By.xpath("//table[@class = 'table ewTable']/tbody/tr[1]/td[5]")).getText();
			Reporter.log(Exp_Data+"   "+ Act_data, true);
			Assert.assertEquals(Exp_Data, Act_data, "Customer number not found in table");
			
	 }
		 
}


