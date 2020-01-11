package com.w2a.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.w2a.utilities.ExcelReader;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	
	public static WebDriver driver;
	
	//public static Logger log = Logger.getLogger(BaseTest.class);
	
	public static Properties or = new Properties();
	
	public static Properties config = new Properties();
	
	public static FileInputStream fis;
	
	public static ExcelReader excel = new ExcelReader("./src/test/resources/excel/logintest.xlsx");
	
	public static WebDriverWait Wait;
	
	@BeforeSuite
	public void setUp() {
		PropertyConfigurator.configure("./src/test/resources/properties/log4j.properties");
		
		try {
			fis = new FileInputStream("./src/test/resources/properties/Config.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			config.load(fis);
			//log.info("Config Properties File");
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		try {
			fis = new FileInputStream("./src/test/resources/properties/OR.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			or.load(fis);
			//log.info("or Properties File");
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		if(config.getProperty("browser").equals("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			//log.info("chrome driver launch");
		}
		
		driver.get(config.getProperty("testsiteurl"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")), TimeUnit.SECONDS);
		Wait = new WebDriverWait(driver, Integer.parseInt(config.getProperty("explicit.wait")));
		
	}
	
	 public void type(String locatorKey, String value) {
			// driver.findElement(By.xpath(OR.getProperty(locatorKey))).sendKeys(value);
			if (locatorKey.endsWith("_xpath")) {
				System.out.println(locatorKey);
				driver.findElement(By.xpath(or.getProperty(locatorKey))).sendKeys(value);
			} else if (locatorKey.endsWith("_id")) {
				driver.findElement(By.id(or.getProperty(locatorKey))).sendKeys(value);
			}
			//log.info("type successfully");
			//throw new SkipException("condition not fulfilled");
		}
	
	 public void click(String locatorKey) {
		 if (locatorKey.endsWith("_xpath")) {
				System.out.println(locatorKey);
				driver.findElement(By.xpath(or.getProperty(locatorKey))).click();
			} else if (locatorKey.endsWith("_id")) {
				driver.findElement(By.id(or.getProperty(locatorKey))).click();
			}
			//log.info("clicked successfully");
	 }
	
	@AfterSuite
	public void tearDown() {
		driver.quit();
		//log.info("browser launched :- ");
	}
}
