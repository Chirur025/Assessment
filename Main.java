package resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;


public class Main {
	static WebDriver driver;
	
		@Test
		public void yatra() throws InterruptedException, IOException
		{
		System.setProperty("webdriver.chrome.driver","C:\\Users\\Chiru R\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(getData("URL")); // Yatra.com
		driver.manage().window().maximize(); // maximize the ChromeBrowser.
		Thread.sleep(5000); // page should wait for 5 seconds for loading the yatra.com link

		DesiredCapabilities cap=new DesiredCapabilities(); //We are accepting the SSL Certification.
		cap.setAcceptInsecureCerts(true); //true = Allow 
		String DepartPlacevalue=getData("DepartPlace");  // Banglore is retrived from data.properties file
		String ToPlacevalue=getData("GoingPlace");  // New Delhi is retrived from data.properties file
		String FlightDate=getData("Date");  // 24/06/2021 is the Depart date
		selectFromAndTo(DepartPlacevalue,ToPlacevalue,FlightDate); // we are sending this info to selectFromAndTo method.
		CloseBrowser();
		}
	
	
	public static void selectFromAndTo(String FromPlace, String ToPlace,String Date) 
			throws InterruptedException, IOException{
		Locators locator=new Locators();
		driver.findElement(By.xpath(locator.DepartPlace)).click(); // clicks on depart place
		Thread.sleep(3000);
		driver.findElement(By.xpath(locator.DepartPlace)).sendKeys(FromPlace); // Banglore
		Thread.sleep(3000);
		driver.findElement(By.xpath(locator.FirstValue)).click(); // clicks on 1st dropdown
		Thread.sleep(3000);
		driver.findElement(By.xpath(locator.GoingPlace)).sendKeys(ToPlace); // New Delhi
		Thread.sleep(3000);
		driver.findElement(By.xpath(locator.FirstValue)).click(); // clicks on 1st dropdown
		
	
	driver.findElement(By.xpath(locator.DatePicker)).click();	// clicks on depart date
	Thread.sleep(3000);
	driver.findElement(By.xpath(CreateXpath("//td[@data-date='{0}']", Date))).click(); // clicks on given date
	driver.findElement(By.xpath(locator.Search)).click();  // clicks on Search
	
	}
	public static String getData(String key) throws IOException
	{
		Properties prop =new Properties(); // this is the path of data.properties file and will be retrived.
		FileInputStream fis =new FileInputStream("C:\\Users\\Chiru R\\eclipse-workspaceNew\\New folder\\testYatra\\src\\main\\java\\resources\\data.properties");
		prop.load(fis);
		return prop.getProperty(key);
		
	}
	
	public static String CreateXpath(String xpath, String value){  // for date picking 
		xpath=xpath.replace("{0}", value);
		return xpath;
		
	}
	
	public static void CloseBrowser(){
		driver.quit();   // after execution the Chrome browser will quit.
	}

}

class Locators{
	final String DepartPlace="//input[@name='flight_origin' and @id='BE_flight_origin_city']";  //Banglore
	final String GoingPlace="//input[@name='flight_destination' and @id='BE_flight_arrival_city']";  // New Delhi
 	final String FirstValue="(//p[@class='ac_cityname'])[1]"; // Selects the 1st dropdown in from and to options.
	final String Search="//input[contains(@value,'Search Flights')]"; // Search Flights
	final String DatePicker="(//input[contains(@name,'flight_origin_date')])[1]"; //24/06/2021
	
}
